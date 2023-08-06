package id.ac.astra.polytechnic.kelompok1.p5m_new.fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import id.ac.astra.polytechnic.kelompok1.p5m_new.R;
import id.ac.astra.polytechnic.kelompok1.p5m_new.helper.CrudType;
import id.ac.astra.polytechnic.kelompok1.p5m_new.helper.NetworkStateLiveData;
import id.ac.astra.polytechnic.kelompok1.p5m_new.helper.ValidationHelper;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Pelanggaran;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Pengguna;
import id.ac.astra.polytechnic.kelompok1.p5m_new.viewmodel.PelanggaranListViewModel;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class PelanggaranFragment extends Fragment {
    private static final String TAG = "PelanggaranFragment";
    private NetworkStateLiveData networkStateLiveData;
    SharedPreferences preferences;
    private MaterialAlertDialogBuilder mMaterialAlertDialogBuilder;
    private FloatingActionButton mFloatingAddPelanggaranButton;
    private RecyclerView mPelanggaranRecycleView;
    private View mLayoutEmpty;
    private AlertDialog mAlertDialog;
    private TextInputLayout mPelanggaranNamaLayout;
    private TextInputLayout mPelanggaranJamLayout;
    private View customAlertDialogView;
    private TextInputLayout mPelanggaranSearchLayout;
    //Data
    private PelanggaranListViewModel mPelanggaranListViewModel;
    private List<Pelanggaran> mPelanggaranList;
    private PelanggaranAdapter mPelanggaranAdapter = new PelanggaranAdapter(Collections.emptyList());
    private Pelanggaran mGlobalPelanggaran;

    public void updateUI(List<Pelanggaran> pelanggaranList){
        Log.d(TAG, "updateUI: called " + pelanggaranList.size());
        mPelanggaranAdapter = new PelanggaranAdapter(pelanggaranList);
        mPelanggaranRecycleView.setAdapter(mPelanggaranAdapter);
        mPelanggaranList = pelanggaranList;
        filter("");

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        networkStateLiveData = new NetworkStateLiveData(getActivity());
        networkStateLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isConnected) {
                if (isConnected) {
                    // Koneksi terhubung
                } else {
                    // Tidak ada koneksi
                    Toast.makeText(getActivity(), "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mPelanggaranListViewModel = new ViewModelProvider(this).get(PelanggaranListViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.fragment_list_pelanggaran, container, false);
       mPelanggaranRecycleView = v.findViewById(R.id.rv_pelanggaran);
       mPelanggaranRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
       mFloatingAddPelanggaranButton = v.findViewById(R.id.fab_add_pelanggaran);
       mLayoutEmpty = v.findViewById(R.id.layout_empty_data);
       mFloatingAddPelanggaranButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    launchCustomAlertDialog(CrudType.ADD, null, savePelanggaran);
                }
            });

       mPelanggaranSearchLayout = v.findViewById(R.id.til_pelanggaran_search);
       mPelanggaranSearchLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
                filter(charSequence.toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mSimpleCallback);
        itemTouchHelper.attachToRecyclerView(mPelanggaranRecycleView);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            mPelanggaranListViewModel.getAllPelanggaran().observe(getViewLifecycleOwner(), new Observer<List<Pelanggaran>>() {
                @Override
                public void onChanged(List<Pelanggaran> pelanggarans) {
                  updateUI(pelanggarans);
                }
            });
    }

    ItemTouchHelper.SimpleCallback mSimpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            Pelanggaran pelanggaran = mPelanggaranList.get(position);

            switch (direction){
                case ItemTouchHelper.LEFT:
                    mPelanggaranListViewModel.deletePelanggaran(pelanggaran.getId()).observe(getViewLifecycleOwner(),pelanggaranResponse -> {
                        if (pelanggaranResponse.getStatus() == 200){
                            mPelanggaranAdapter.notifyItemRemoved(position);
                            View ve = getActivity().findViewById(R.id.end);

                            Snackbar.make(ve, pelanggaranResponse.getmPelanggaran().getNama() + " was deleted.", Snackbar.LENGTH_LONG)
                                    .setAction("Undo", v -> {
                                        Pelanggaran updatedPelanggaran = pelanggaranResponse.getmPelanggaran();
                                        updatedPelanggaran.setStatus(1);
                                        mPelanggaranListViewModel.addToPosition(position, updatedPelanggaran);
                                    }).show();
                        } else {
                            FancyToast.makeText(getActivity(), pelanggaranResponse.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
                        }
                    });
                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getActivity(), R.color.red))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    private class PelanggaranHolder extends RecyclerView.ViewHolder{
        private List<Pelanggaran> mPelanggaranList;
        private TextView mPelanggaranNama;
        private TextView mPelanggaranJam;

        private ImageButton mBtnEdit;
        private ImageButton mBtnDelete;
        private Pelanggaran mPelanggaran;




        public PelanggaranHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.pelanggaran_row_item, parent, false));
            //itemView.setOnClickListener(this);
            mPelanggaranNama = itemView.findViewById(R.id.tv_pelanggaran_name);
            mPelanggaranJam = itemView.findViewById(R.id.tv_show_jam);
            mBtnEdit = itemView.findViewById(R.id.btn_edit_pelanggaran);
            mBtnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mGlobalPelanggaran = mPelanggaran;
                    launchCustomAlertDialog(CrudType.EDIT,mPelanggaran,editPelanggaran);

                }
            });
        }

        public void bind(Pelanggaran pelanggaran){
            mPelanggaran = pelanggaran;
            mPelanggaranNama.setText(mPelanggaran.getNama());
            mPelanggaranJam.setText(mPelanggaran.getJamMinus());
        }
    }

    private class PelanggaranAdapter extends RecyclerView.Adapter<PelanggaranHolder>{
        private List<Pelanggaran> mPelanggaranList;
        public PelanggaranAdapter(List<Pelanggaran> pelanggaranList){
            mPelanggaranList = pelanggaranList;
        }

        @Override
        public PelanggaranHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new PelanggaranHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(PelanggaranHolder holder, int position){
            Pelanggaran pelanggaran = mPelanggaranList.get(position);
            holder.bind(pelanggaran);
        }

        @Override
        public int getItemCount(){
            return mPelanggaranList.size();
        }

        public void filteList(List<Pelanggaran> filteredList){
            if (filteredList.size() == 0) {
                mLayoutEmpty.setVisibility(View.VISIBLE);
                mPelanggaranRecycleView.setVisibility(View.GONE);
            } else {
                mLayoutEmpty.setVisibility(View.GONE);
                mPelanggaranRecycleView.setVisibility(View.VISIBLE);
            }
            mPelanggaranList = filteredList;
            notifyDataSetChanged();
        }

    }

    public void filter(String text) {
        List<Pelanggaran> filtered = new ArrayList<>();

        for (Pelanggaran s : mPelanggaranList) {
            if (s.getNama().toLowerCase().contains(text.toLowerCase())) {
                filtered.add(s);
            }
        }

        mPelanggaranAdapter.filteList(filtered);
    }

    private void launchCustomAlertDialog(CrudType crudType, Pelanggaran pelanggaran, View.OnClickListener onClickListener){
        LayoutInflater inflater = getLayoutInflater();
        customAlertDialogView = inflater.inflate(R.layout.dialog_pelanggaran_form, null);
        mMaterialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());

        mPelanggaranNamaLayout = customAlertDialogView.findViewById(R.id.til_pelanggaran_name);
        mPelanggaranJamLayout = customAlertDialogView.findViewById(R.id.til_jam_desc);

        if (crudType == CrudType.EDIT && pelanggaran != null){
            mPelanggaranNamaLayout.getEditText().setText(pelanggaran.getNama());
            mPelanggaranJamLayout.getEditText().setText(pelanggaran.getJamMinus());
        }

        String title = crudType == CrudType.ADD ? "Create" : "Update";
        if (customAlertDialogView != null) {
            mMaterialAlertDialogBuilder
                    .setTitle(title + " Pelanggaran").setView(customAlertDialogView)
                    .setMessage("Enter your Pelanggaran details")
                    .setPositiveButton(title, null)
                    .setNegativeButton("Cancel", (dialog, which) -> {
                        dialog.dismiss();
                    });

        }
        mAlertDialog = mMaterialAlertDialogBuilder.create();
        mAlertDialog.show();
        Button positiveButton = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(onClickListener);
    }

    private View.OnClickListener editPelanggaran = v -> {
        if(validate()){
            String nama = mPelanggaranNamaLayout.getEditText().getText().toString();
            String jam = mPelanggaranJamLayout.getEditText().getText().toString();
            mGlobalPelanggaran.setNama(nama);
            mGlobalPelanggaran.setJamMinus(jam);
            ProgressDialog progressDialog = ProgressDialog.show(requireContext(), "Pelanggaran", "Updating Pelanggaran ...");
            mPelanggaranListViewModel.updatePelanggaran(mGlobalPelanggaran).observe(getViewLifecycleOwner(),pelanggaranResponse -> {
                Log.d(TAG,"On Fragment Pelanggaran " + pelanggaranResponse);
                if (pelanggaranResponse.getStatus() == 200){
                    FancyToast.makeText(getActivity(),pelanggaranResponse.getMessage(),FancyToast.LENGTH_LONG, FancyToast.SUCCESS,false).show();
                    mAlertDialog.dismiss();
                }else {
                    FancyToast.makeText(getActivity(), pelanggaranResponse.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
                }
                progressDialog.dismiss();
            });
        }else {
            FancyToast.makeText(getActivity(),"Lengkapi semua data!", FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();

        }
    };

    private View.OnClickListener savePelanggaran = v -> {
        if(validate()){
            String nama = mPelanggaranNamaLayout.getEditText().getText().toString();
            String jam = mPelanggaranJamLayout.getEditText().getText().toString();

//            mGlobalPengguna.setNama(nama);
//            mGlobalPengguna.setKelas(kelas);
//            mGlobalPengguna.setRole(role);
            Pelanggaran pelanggaran = new Pelanggaran(null,nama,jam,false);
            ProgressDialog progressDialog = ProgressDialog.show(requireContext(), "Pelanggaran", "Saving Pelanggaran ...");
            mPelanggaranListViewModel.savePelanggaran(pelanggaran).observe(getViewLifecycleOwner(),pelanggaranResponse -> {
                if (pelanggaranResponse.getStatus() == 200){
                    FancyToast.makeText(getActivity(), pelanggaranResponse.getMessage(), FancyToast.LENGTH_LONG, FancyToast.SUCCESS,false).show();
                    mAlertDialog.dismiss();
                } else {
                    FancyToast.makeText(getActivity(), pelanggaranResponse.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
                }
                progressDialog.dismiss();
            });
        }else {
            FancyToast.makeText(getActivity(),"Lengkapi semua data!", FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
        }
    };

public boolean validate() {
    boolean nameValidation = ValidationHelper.requiredTextInputValidation(mPelanggaranNamaLayout);
    boolean jamValidation = ValidationHelper.requiredTextInputValidation(mPelanggaranJamLayout);


    return nameValidation && jamValidation ;
}
}
