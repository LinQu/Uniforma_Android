package id.ac.astra.polytechnic.kelompok1.p5m_new.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import id.ac.astra.polytechnic.kelompok1.p5m_new.LoginActivity;
import id.ac.astra.polytechnic.kelompok1.p5m_new.MainActivity;
import id.ac.astra.polytechnic.kelompok1.p5m_new.R;
import id.ac.astra.polytechnic.kelompok1.p5m_new.helper.CrudType;
import id.ac.astra.polytechnic.kelompok1.p5m_new.helper.NetworkStateLiveData;
import id.ac.astra.polytechnic.kelompok1.p5m_new.helper.ValidationHelper;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Kelas;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Mahasiswa;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Pengguna;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Role;
import id.ac.astra.polytechnic.kelompok1.p5m_new.viewmodel.KelasListViewModel;
import id.ac.astra.polytechnic.kelompok1.p5m_new.viewmodel.PenggunaListViewModel;
import id.ac.astra.polytechnic.kelompok1.p5m_new.viewmodel.RoleListViewModel;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class PenggunaFragment extends Fragment {
    private static final String TAG = "PenggunaFragment";
    SharedPreferences preferences;
    private NetworkStateLiveData networkStateLiveData;
    private MaterialAlertDialogBuilder mMaterialAlertDialogBuilder;
    private PenggunaListViewModel mPengunaListViewModel;
    private FloatingActionButton mFloatingAddPenggunaButton;
    private PenggunaAdapter mPenggunaAdapter = new PenggunaAdapter(Collections.emptyList());
    private RecyclerView mPenggunaRecycleView;
    private TextInputLayout mPenggunaSearchLayout;
    private View mLayoutEmpty;
    private AlertDialog mAlertDialog;
    private TextInputLayout mPenggunaNameLayout;
    private TextInputLayout mPenggunaRoleLayout;
    private TextInputLayout mPenggunaClassLayout;
    private AutoCompleteTextView mAutoTextPenggunaRole;
    private AutoCompleteTextView mAutoTextPenggunaClass;
    // Data
    private List<Pengguna> mPenggunaFrag;
    private Pengguna mGlobalPengguna;
    private View customAlertDialogView;
    private ArrayAdapter<Role> mRoleAdapter;
    private ArrayAdapter<String> mKelasAdater;
    private RoleListViewModel roleListViewModel;
    private KelasListViewModel kelasListViewModel;

    private void updateUI(List<Pengguna> penggunas){
        mPenggunaAdapter = new PenggunaAdapter(penggunas);
        mPenggunaRecycleView.setAdapter(mPenggunaAdapter);
        mPenggunaFrag = penggunas;
        filter("");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
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
        roleListViewModel = new ViewModelProvider(this).get(RoleListViewModel.class);
        mPengunaListViewModel = new ViewModelProvider(this).get(PenggunaListViewModel.class);
        kelasListViewModel = new ViewModelProvider(this).get(KelasListViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_list_pengguna,container,false);

        mPenggunaRecycleView = v.findViewById(R.id.rv_pengguna);
        mPenggunaRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        mPenggunaRecycleView.setAdapter(mPenggunaAdapter);
        mFloatingAddPenggunaButton = v.findViewById(R.id.fab_add_pengguna);
        mFloatingAddPenggunaButton.setOnClickListener(view -> {
            launchCustomAlertDialog(CrudType.ADD, null, savePengguna);
        });
        mPenggunaSearchLayout = v.findViewById(R.id.til_pengguna_search);
        mPenggunaSearchLayout.getEditText().addTextChangedListener(new TextWatcher() {
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
        mLayoutEmpty = v.findViewById(R.id.layout_empty_data);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mSimpleCallback);
        itemTouchHelper.attachToRecyclerView(mPenggunaRecycleView);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPengunaListViewModel.getAllPengguna().observe(getViewLifecycleOwner(), new Observer<List<Pengguna>>() {
            @Override
            public void onChanged(List<Pengguna> penggunas) {
                updateUI(penggunas);
            }
        });
    }

    private View.OnClickListener savePengguna = v -> {
        if(validate()){
            String nama = mPenggunaNameLayout.getEditText().getText().toString();
            String kelas = mPenggunaClassLayout.getEditText().getText().toString();
            String role = mPenggunaRoleLayout.getEditText().getText().toString();

//            mGlobalPengguna.setNama(nama);
//            mGlobalPengguna.setKelas(kelas);
//            mGlobalPengguna.setRole(role);
            Pengguna pengguna = new Pengguna(null,nama,role,kelas,1);
            ProgressDialog progressDialog = ProgressDialog.show(requireContext(), "Pengguna", "Saving Pengguna ...");
            mPengunaListViewModel.savePengguna(pengguna).observe(getViewLifecycleOwner(),penggunaResponse -> {
                if (penggunaResponse.getStatus() == 200){
                    FancyToast.makeText(getActivity(), penggunaResponse.getMessage(), FancyToast.LENGTH_LONG, FancyToast.SUCCESS,false).show();
                    mAlertDialog.dismiss();
                } else {
                    FancyToast.makeText(getActivity(), penggunaResponse.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
                }
                progressDialog.dismiss();
            });
        }else {
            FancyToast.makeText(getActivity(),"Lengkapi semua data!", FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
        }
    };

    private View.OnClickListener editPengguna = v -> {
        if(validate()){
            String nama = mPenggunaNameLayout.getEditText().getText().toString();
            String kelas = mPenggunaClassLayout.getEditText().getText().toString();
            String role = mPenggunaRoleLayout.getEditText().getText().toString();

            mGlobalPengguna.setNama(nama);
            mGlobalPengguna.setKelas(kelas);
            mGlobalPengguna.setRole(role);
            mGlobalPengguna.setStatus(1);

            ProgressDialog progressDialog = ProgressDialog.show(requireContext(), "Pengguna", "Saving Pengguna ...");
            mPengunaListViewModel.updatePengguna(mGlobalPengguna).observe(getViewLifecycleOwner(),penggunaResponse -> {
                if (penggunaResponse.getStatus() == 200){
                    FancyToast.makeText(getActivity(), penggunaResponse.getMessage(), FancyToast.LENGTH_LONG, FancyToast.SUCCESS,false).show();
                    mAlertDialog.dismiss();
                } else {
                    FancyToast.makeText(getActivity(), penggunaResponse.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
                }
                progressDialog.dismiss();
            });
        }else {
            FancyToast.makeText(getActivity(),"Lengkapi semua data!", FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
        }
        Toast.makeText(getActivity(),"Test dialog",Toast.LENGTH_SHORT);
    };

    ItemTouchHelper.SimpleCallback mSimpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            Pengguna pengguna = mPenggunaFrag.get(position);

            switch (direction){
                case ItemTouchHelper.LEFT:
                    mPengunaListViewModel.deletePengguna(pengguna.getId()).observe(getViewLifecycleOwner(),penggunaResponse -> {
                        if (penggunaResponse.getStatus() == 200){
                            mPenggunaAdapter.notifyItemRemoved(position);
                            View ve = getActivity().findViewById(R.id.end);

                            Snackbar.make(ve, penggunaResponse.getmPengguna().getNama() + " was deleted.", Snackbar.LENGTH_LONG)
                                    .setAction("Undo", v -> {
                                        Pengguna updatedPengguna = penggunaResponse.getmPengguna();
                                        updatedPengguna.setStatus(1);
                                        mPengunaListViewModel.addToPosition(position, updatedPengguna);
                                    }).show();
                        } else {
                            FancyToast.makeText(getActivity(), penggunaResponse.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
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
        private class PenggunaHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mPenggunaNama;
        private TextView mPenggunaRole;
        private TextView mPenggunaKelas;
        private ImageButton mBtnEdit;
        private ImageButton mBtnDelete;
        private Pengguna mPengguna;



        public PenggunaHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.pengguna_row_item,parent,false));
            itemView.setOnClickListener(this);
            mPenggunaNama = itemView.findViewById(R.id.tv_pengguna_name);
            mPenggunaRole = itemView.findViewById(R.id.tv_pengguna_role);
            mPenggunaKelas = itemView.findViewById(R.id.tv_pengguna_kelas);
            mBtnEdit = itemView.findViewById(R.id.btn_edit_pengguna);
            mBtnEdit.setOnClickListener(view -> {
                mGlobalPengguna = mPengguna;
                launchCustomAlertDialog(CrudType.EDIT, mPengguna, editPengguna);
            });

        }

        public void bind(Pengguna pengguna){
            mPengguna = pengguna;
            mPenggunaNama.setText(mPengguna.getNama());
            mPenggunaKelas.setText(mPengguna.getKelas());
            mPenggunaRole.setText(mPengguna.getRole());
        }

        @Override
        public void onClick(View view) {

        }
    }

    private class PenggunaAdapter extends RecyclerView.Adapter<PenggunaHolder>{
        private List<Pengguna> mPengguna;

        public PenggunaAdapter(List<Pengguna> penggunas){
            mPengguna = penggunas;
        }

        @NonNull
        @Override
        public PenggunaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new PenggunaHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull PenggunaHolder holder, int position) {
            Pengguna pengguna = mPengguna.get(position);
            holder.bind(pengguna);
        }

        @Override
        public int getItemCount() {
            return mPengguna.size();
        }

        public void filterList(List<Pengguna> filtered) {
            if (filtered.size() == 0) {
                mLayoutEmpty.setVisibility(View.VISIBLE);
                mPenggunaRecycleView.setVisibility(View.GONE);
            } else {
                mLayoutEmpty.setVisibility(View.GONE);
                mPenggunaRecycleView.setVisibility(View.VISIBLE);
            }
            this.mPengguna = filtered;
            notifyDataSetChanged();
        }
    }

    public void filter(String text) {
        List<Pengguna> filtered = new ArrayList<>();

        for (Pengguna s : mPenggunaFrag) {
            if (s.getNama().toLowerCase().contains(text.toLowerCase())
                    || s.getRole().toLowerCase().contains(text.toLowerCase())) {
                filtered.add(s);
            }
        }

        mPenggunaAdapter.filterList(filtered);
    }

    private void launchCustomAlertDialog(CrudType crudType, Pengguna pengguna, View.OnClickListener onClickListener) {
        LayoutInflater inflater = getLayoutInflater();
        customAlertDialogView = inflater.inflate(R.layout.dialog_schedule_form, null);
        mMaterialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());

        // Membuat adapter untuk AutoCompleteTextView mAutoTextPenggunaRole
        List<Role> mRole = roleListViewModel.getRoleList();
        mRoleAdapter = new ArrayAdapter<Role>(getActivity(), R.layout.list_item_role, mRole);

        // Membuat adapter untuk AutoCompleteTextView mAutoTextPenggunaClass
        mKelasAdater = new ArrayAdapter<String>(getActivity(), R.layout.list_item_class, new ArrayList<>());
        mAutoTextPenggunaClass = customAlertDialogView.findViewById(R.id.tie_pengguna_class);
        mAutoTextPenggunaClass.setAdapter(mKelasAdater);

        // Mengamati perubahan pada kelasListViewModel.getAllKelas()
        kelasListViewModel.getAllKelas().observe(getViewLifecycleOwner(), new Observer<List<Kelas>>() {
            @Override
            public void onChanged(List<Kelas> kelas) {
                // Mengupdate data dalam adapter mKelasAdater
                List<String> namaKelasList = new ArrayList<>();
                for (Kelas k : kelas) {
                    namaKelasList.add(k.getId()); // Menambahkan hanya nama kelas ke daftar
                }
                mKelasAdater.clear();
                mKelasAdater.addAll(namaKelasList);
                mKelasAdater.notifyDataSetChanged();
            }
        });

        // Mengisi field EditText sesuai data pengguna yang diberikan
        mPenggunaNameLayout = customAlertDialogView.findViewById(R.id.til_schedule_name);
        mPenggunaRoleLayout = customAlertDialogView.findViewById(R.id.til_schedule_desc);
        mPenggunaClassLayout = customAlertDialogView.findViewById(R.id.til_pengguna_class);
        mAutoTextPenggunaRole = customAlertDialogView.findViewById(R.id.tie_pengguna_role);
        mAutoTextPenggunaRole.setAdapter(mRoleAdapter);

        if (crudType == CrudType.EDIT && pengguna != null) {
            mPenggunaNameLayout.getEditText().setText(pengguna.getNama());
            mPenggunaRoleLayout.getEditText().setText(pengguna.getRole());
            mPenggunaClassLayout.getEditText().setText(pengguna.getKelas());
        }

        String title = crudType == CrudType.ADD ? "Create" : "Update";
        if (customAlertDialogView != null) {
            mMaterialAlertDialogBuilder.setView(customAlertDialogView)
                    .setTitle(title + " Pengguna")
                    .setMessage("Enter your Pengguna details")
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

    public boolean validate() {
        boolean nameValidation = ValidationHelper.requiredTextInputValidation(mPenggunaNameLayout);
        boolean kelasValidation = ValidationHelper.requiredTextInputValidation(mPenggunaClassLayout);
        boolean roleValidation = ValidationHelper.requiredTextInputValidation(mPenggunaClassLayout);

        return nameValidation && kelasValidation && roleValidation;
    }
}
