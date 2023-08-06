package id.ac.astra.polytechnic.kelompok1.p5m_new.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.ac.astra.polytechnic.kelompok1.p5m_new.R;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.DetailP5m;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Mahasiswa;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.P5m;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Pelanggaran;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.ListDetailP5mResponse;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.P5mResponse;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.PelanggaranMahasiswa;
import id.ac.astra.polytechnic.kelompok1.p5m_new.viewmodel.MahasiswaListViewModel;
import id.ac.astra.polytechnic.kelompok1.p5m_new.viewmodel.P5mListViewModel;
import id.ac.astra.polytechnic.kelompok1.p5m_new.viewmodel.PelanggaranListViewModel;

public class MahasiswaFormFragmentCoba extends Fragment {

    public static MahasiswaFormFragmentCoba newInstance() {
        return new MahasiswaFormFragmentCoba();
    }
    SharedPreferences preferences;
    private static final String TAG = "MahasiswaFormFragmentCoba";

    private MahasiswaListViewModel mMahasiswaListViewModel;
    private PelanggaranListViewModel mPelanggaranListViewModel;
    private P5mListViewModel mP5mListViewModel;

    private RecyclerView mMahasiswaRecyclerView;
    private MahasiswaAdapter mAdapter = new MahasiswaAdapter(Collections.emptyList());
    private View mLayoutEmpty;
    private PelanggaranAdapter mPelanggarAdapter=new PelanggaranAdapter(Collections.emptyList());
    private String kelas;
    private TextInputLayout mMahasiswSearchLayout;
    private Button mBtnSsave;
    private List<Mahasiswa> mMahasiswaFrag;
    private List<P5m> mP5mList;
    private List<DetailP5m> mDetailP5mList;
    List<Integer> selectedCheckBoxIds = new ArrayList<>();
    private List<PelanggaranMahasiswa> mPelanggaranMahasiswaList = new ArrayList<>();;


//    private void updateUI(List<Pelanggaran> pelanggaranList) {
//        mPelanggarAdapter = new PelanggaranAdapter(pelanggaranList);
//    }
//    private void updateUIWithMahasiswa(List<Mahasiswa> mahasiswaList){
//        mAdapter = new MahasiswaAdapter(mahasiswaList);
//        mMahasiswaFrag = mahasiswaList;
//        mMahasiswaRecyclerView.setAdapter(mAdapter);
//        filter("");
//    }
    @SuppressLint("LongLogTag")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mMahasiswaListViewModel = new ViewModelProvider(this).get(MahasiswaListViewModel.class);
        mPelanggaranListViewModel = new ViewModelProvider(this).get(PelanggaranListViewModel.class);
        mP5mListViewModel = new ViewModelProvider(this).get(P5mListViewModel.class);
        Log.d(TAG, "Total mahasiswa: " + mMahasiswaListViewModel.getMahasiswa().size());
        Log.d(TAG, "Total Pelanggaran: " + mMahasiswaListViewModel.getMahasiswa().size());
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_form, container, false);
        mMahasiswaRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mMahasiswaRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        preferences = getActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE);
        mMahasiswSearchLayout = (TextInputLayout) view.findViewById(R.id.til_mahasiswa_search);
        mMahasiswSearchLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                filter(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
        mLayoutEmpty = view.findViewById(R.id.layout_empty_data);
        mBtnSsave = (Button) view.findViewById(R.id.btnSaveP5m);
        mBtnSsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new MahasiswaFormFragmentCoba()).commit();
                hitungP5m(selectedCheckBoxIds);
            }
        });
        kelas = (preferences.getString("kelas", ""));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMahasiswaListViewModel.getMahasiswaByKelas(kelas).observe(getViewLifecycleOwner(), new Observer<List<Mahasiswa>>() {
            @Override
            public void onChanged(List<Mahasiswa> mahasiswasi) {
                //updateUIWithMahasiswa(mahasiswasi);
                mPelanggaranListViewModel.getAllPelanggaran().observe(getViewLifecycleOwner(), new Observer<List<Pelanggaran>>() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onChanged(List<Pelanggaran> pelanggaranList) {
                        for (Mahasiswa mahasiswa : mahasiswasi) {
                            List<Pelanggaran> pelanggaranMahasiswa = new ArrayList<>();
                            for (Pelanggaran pelanggaran : pelanggaranList) {
                                Pelanggaran pelanggaranMahasiswaItem = new Pelanggaran(pelanggaran.getId(), pelanggaran.getNama(),pelanggaran.getJamMinus(), pelanggaran.isSelected());
                                pelanggaranMahasiswaItem.setNim(mahasiswa.getNim());
                                pelanggaranMahasiswa.add(pelanggaranMahasiswaItem);
                            }
                            mPelanggaranMahasiswaList.add(new PelanggaranMahasiswa(pelanggaranMahasiswa, mahasiswa));
                        }

                        mAdapter = new MahasiswaAdapter(mPelanggaranMahasiswaList);
                        mMahasiswaRecyclerView.setAdapter(mAdapter);

                        // Log untuk memastikan data telah diatur dengan benar
                        Log.d(TAG, "onChanged Pelanggaran: " + mPelanggaranMahasiswaList.get(1).getmPelanggaran());
                        Log.d(TAG, "onChanged Pelanggaran: " + mPelanggaranMahasiswaList.get(2).getmPelanggaran());

                        mMahasiswaFrag = mahasiswasi;
                        filter("");
                    }
                });
            }
        });


    }

    private class MahasiswaHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mMahasiswaTextView;
        private TextView mPelanggaranTextView;
        private CheckBox mCheckBox;
        private Mahasiswa mMahasiswa;
        private PelanggaranMahasiswa mPelanggaranMahasiswa;
        private PelanggaranAdapter itemAdapter;
        private RecyclerView mPelanggaranRecycleView;
        private MaterialCardView mCardView;


        public MahasiswaHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.fragment_form_item, parent, false));
            itemView.setOnClickListener(this);
            itemView.setTag(this);
            mMahasiswaTextView = (TextView) itemView.findViewById(R.id.namaMahasiswa);
            mCardView = (MaterialCardView) itemView.findViewById(R.id.expanded1);
//            mPelanggaranTextView = (TextView) itemView.findViewById(R.id.pelanggaran);
            //mCheckBox = (CheckBox) itemView.findViewById(R.id.cb_item_pelanggaran);
            mPelanggaranRecycleView = itemView.findViewById(R.id.rv_item_pelanggaran);

        }

        private void showMahasiswaNameToast() {
            String mahasiswaName = mMahasiswa.getNama();
            Toast.makeText(itemView.getContext(), "Nama Mahasiswa: " + mahasiswaName, Toast.LENGTH_SHORT).show();
        }

        public void bind(PelanggaranMahasiswa mahasiswa){
            mPelanggaranMahasiswa = mahasiswa;
            mMahasiswaTextView.setText(mPelanggaranMahasiswa.getmMahasiswa().getNama());
        }

        @Override
        public void onClick(View view){
//            Toast.makeText(getActivity(),
//                            mMahasiswa.getNama() + "clicked!", Toast.LENGTH_SHORT)
//                    .show();
            TransitionManager.beginDelayedTransition(mCardView,new AutoTransition());

            if (mCardView.getVisibility() == View.GONE){
                mCardView.setVisibility(View.VISIBLE);

            } else {
                mCardView.setVisibility(View.GONE);
            }
        }
    }

    private class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaHolder>{
        private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        private List<Mahasiswa> mMahasiswas;
        private List<PelanggaranMahasiswa> mPelanggaranMahasiswa;

        public MahasiswaAdapter(List<PelanggaranMahasiswa> mahasiswas){
            mPelanggaranMahasiswa = mahasiswas;
        }

        @Override
        public MahasiswaHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new MahasiswaHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(MahasiswaHolder holder, int position){
            PelanggaranMahasiswa mahasiswa = mPelanggaranMahasiswa.get(position);
            holder.bind(mahasiswa);
            holder.mCardView.setVisibility(View.GONE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(holder.mPelanggaranRecycleView.getContext(), LinearLayoutManager.VERTICAL, false);
            layoutManager.setInitialPrefetchItemCount(mahasiswa.getmPelanggaran().size());
            PelanggaranAdapter itemAdapter = new PelanggaranAdapter(mahasiswa.getmPelanggaran());
            holder.mPelanggaranRecycleView.setLayoutManager(layoutManager);
            holder.mPelanggaranRecycleView.setAdapter(itemAdapter);
            //holder.mPelanggaranRecycleView.setRecycledViewPool(viewPool);

        }

        @Override
        public int getItemCount(){
            return mPelanggaranMahasiswa.size();
        }
        public void filterList(List<PelanggaranMahasiswa> filtered) {
            if (filtered.size() == 0) {
                mLayoutEmpty.setVisibility(View.VISIBLE);
                mMahasiswaRecyclerView.setVisibility(View.GONE);
            } else {
                mLayoutEmpty.setVisibility(View.GONE);
                mMahasiswaRecyclerView.setVisibility(View.VISIBLE);
            }
            this.mPelanggaranMahasiswa = filtered;
            notifyDataSetChanged();
        }
    }



    private class PelanggaranAdapter extends RecyclerView.Adapter<PelanggaranAdapter.PelanggaranHolder>{
        private List<Pelanggaran> pelanggaranList;
        public PelanggaranAdapter(List<Pelanggaran> mPelanggaranList){
            pelanggaranList = mPelanggaranList;
        }

        @NonNull
        @Override
        public PelanggaranHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_view_pelanggaran, parent, false);
            return new PelanggaranHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PelanggaranHolder holder, int position) {
            Pelanggaran pelanggaran = pelanggaranList.get(position);
            holder.bind(pelanggaran);

        }

        @Override
        public int getItemCount() {
             return pelanggaranList.size();
        }

        private class PelanggaranHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            private CheckBox cbPelanggaran;
            String nim;
            public PelanggaranHolder(@NonNull View itemView) {
                super(itemView);
                cbPelanggaran = itemView.findViewById(R.id.cb_item_pelanggaran);
                cbPelanggaran.setOnClickListener(this);
            }

            public void bind(Pelanggaran item) {
                nim = item.getNim();
                cbPelanggaran.setText(item.getNama());
                cbPelanggaran.setId(Integer.parseInt( item.getId()+nim.substring(nim.length() - 3)));
                cbPelanggaran.setChecked(item.isSelected());
            }

            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(),
                                cbPelanggaran.getId() +" " + cbPelanggaran.getText() + " clicked! ", Toast.LENGTH_SHORT)
                        .show();
                int pos = getAdapterPosition();

                if (pos != RecyclerView.NO_POSITION){

                    Pelanggaran clickedDataItem = pelanggaranList.get(pos);
                    clickedDataItem.setSelected(cbPelanggaran.isChecked());
                    if (cbPelanggaran.isChecked()) {
                        int checkBoxId = cbPelanggaran.getId();
                        selectedCheckBoxIds.add(checkBoxId);
                    }else{
                        // Cari indeks ID yang cocok
                        int indexToRemove = -1;
                        for (int i = 0; i < selectedCheckBoxIds.size(); i++) {
                            if (selectedCheckBoxIds.get(i) == cbPelanggaran.getId()) {
                                indexToRemove = i;
                                break;
                            }
                        }
                        // Hapus ID dari ArrayList jika ditemukan
                        if (indexToRemove != -1) {
                            selectedCheckBoxIds.remove(indexToRemove);
                        }
                    }

                    Log.d("TAG", "Ada Data Pelanggaran: " + selectedCheckBoxIds.size());
//                    Mahasiswa mahasiswa = mMahasiswaFrag.get(pos);

                    // Tampilkan data mahasiswa yang terkait (misalnya nama, nim, dll.)
//                    Toast.makeText(itemView.getContext(),
//                            "Data Mahasiswa: " + mahasiswa.getNama() + ", NIM: " + mahasiswa.getNim(),
//                            Toast.LENGTH_SHORT).show();
                    Log.d("TAG", "onClick: " + clickedDataItem + " " + clickedDataItem.isSelected());
                    //update ke listPelanggaran
                    if (cbPelanggaran.isChecked()){
                        pelanggaranList.set(pos,clickedDataItem);
                    }
                    if (cbPelanggaran.isChecked()){
                        Toast.makeText(view.getContext(), "Kamu memilih " + clickedDataItem.getNama(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(view.getContext(), "Kamu membatalkan pilihan " + clickedDataItem.getNama(), Toast.LENGTH_SHORT).show();
                    }


                }
            }
        }
    }

    public void filter(String text) {
        List<PelanggaranMahasiswa> filtered = new ArrayList<>();

        for (PelanggaranMahasiswa s : mPelanggaranMahasiswaList) {
            if (s.getmMahasiswa().getNama().toLowerCase().contains(text.toLowerCase())
                    || s.getmMahasiswa().getNim().toLowerCase().contains(text.toLowerCase())) {
                filtered.add(s);
            }
        }

        mAdapter.filterList(filtered);
    }

    @SuppressLint("LongLogTag")
    public void hitungP5m(List<Integer> selectedCheckBoxIds){
        Map<String, List<Integer>> resultMap = new HashMap<>();

        for (int id : selectedCheckBoxIds) {
            String nim = findMahasiswaByNimSubstring(mMahasiswaFrag,String.valueOf(id).substring(String.valueOf(id).length() - 3)).getNim();
            int ids = Integer.parseInt(String.valueOf(id).substring(0, String.valueOf(id).length() - 3));
            if (resultMap.containsKey(nim)) {
                List<Integer> idList = resultMap.get(nim);
                idList.add(ids);
            } else {
                List<Integer> idList = new ArrayList<>();
                idList.add(ids);
                resultMap.put(nim, idList);
            }
        }

    mP5mListViewModel.postP5m(resultMap,kelas).observe(getViewLifecycleOwner(), new Observer<ListDetailP5mResponse>() {
        @Override
        public void onChanged(ListDetailP5mResponse p5mResponse) {
            if (p5mResponse.getStatus() == 200 ) {
                // Reload current fragment
                Log.d(TAG, p5mResponse.getMessage());
            }
        }
    });


        Log.d("TAG", "hitungP5m: " + resultMap.size());

    }

    public Mahasiswa findMahasiswaByNimSubstring(List<Mahasiswa> mahasiswaList, String nimSubstring) {
        for (Mahasiswa mahasiswa : mahasiswaList) {
            String nim = mahasiswa.getNim();
            if (nim.endsWith(nimSubstring)) {
                // Jika potongan string NIM cocok, kembalikan objek Mahasiswa yang ditemukan
                return mahasiswa;
            }
        }
        // Jika tidak ada yang cocok, kembalikan null atau lakukan tindakan lain sesuai kebutuhan
        return null;
    }

}
