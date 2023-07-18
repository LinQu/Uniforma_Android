package id.ac.astra.polytechnic.kelompok1.p5m_new.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.Collections;
import java.util.List;

import id.ac.astra.polytechnic.kelompok1.p5m_new.R;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Mahasiswa;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Pelanggaran;
import id.ac.astra.polytechnic.kelompok1.p5m_new.viewmodel.MahasiswaListViewModel;
import id.ac.astra.polytechnic.kelompok1.p5m_new.viewmodel.PelanggaranListViewModel;

public class MahasiswaFormFragment extends Fragment {

    public static MahasiswaFormFragment newInstance() {
        return new MahasiswaFormFragment();
    }
    SharedPreferences preferences;
    private static final String TAG = "MahasiswaListFragment";

    private MahasiswaListViewModel mMahasiswaListViewModel;
    private PelanggaranListViewModel mPelanggaranListViewModel;
    private RecyclerView mMahasiswaRecyclerView;
    private MahasiswaAdapter mAdapter;
    private PelanggaranAdapter mPelanggarAdapter=new PelanggaranAdapter(Collections.emptyList());
    private String kelas;



    private void updateUI(List<Pelanggaran> pelanggaranList) {

        mPelanggarAdapter = new PelanggaranAdapter(pelanggaranList);
    }
    private void updateUIWithMahasiswa(List<Mahasiswa> mahasiswaList){


        mAdapter = new MahasiswaAdapter(mahasiswaList);
        mMahasiswaRecyclerView.setAdapter(mAdapter);

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mMahasiswaListViewModel = new ViewModelProvider(this).get(MahasiswaListViewModel.class);
        mPelanggaranListViewModel = new ViewModelProvider(this).get(PelanggaranListViewModel.class);
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
        kelas = (preferences.getString("kelas", ""));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPelanggaranListViewModel.getAllPelanggaran().observe(getViewLifecycleOwner(), new Observer<List<Pelanggaran>>() {
            @Override
            public void onChanged(List<Pelanggaran> pelanggaranList) {
                updateUI(pelanggaranList);
            }
        });
        mMahasiswaListViewModel.getMahasiswaByKelas(kelas).observe(getViewLifecycleOwner(), new Observer<List<Mahasiswa>>() {
            @Override
            public void onChanged(List<Mahasiswa> mahasiswasi) {
                updateUIWithMahasiswa(mahasiswasi);
            }
        });
    }

    private class MahasiswaHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mMahasiswaTextView;
        private TextView mPelanggaranTextView;
        private CheckBox mCheckBox;
        private Mahasiswa mMahasiswa;
        private PelanggaranAdapter itemAdapter;
        private RecyclerView mPelanggaranRecycleView;
        private MaterialCardView mCardView;


        public MahasiswaHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.fragment_form_item, parent, false));
            itemView.setOnClickListener(this);

            mMahasiswaTextView = (TextView) itemView.findViewById(R.id.namaMahasiswa);
            mCardView = (MaterialCardView) itemView.findViewById(R.id.expanded1);
//            mPelanggaranTextView = (TextView) itemView.findViewById(R.id.pelanggaran);
            //mCheckBox = (CheckBox) itemView.findViewById(R.id.cb_item_pelanggaran);
            mPelanggaranRecycleView = (RecyclerView) itemView.findViewById(R.id.rv_item_pelanggaran);
            mPelanggaranRecycleView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            mPelanggaranRecycleView.setAdapter(mPelanggarAdapter);

        }

        public void bind(Mahasiswa mahasiswa){
            mMahasiswa = mahasiswa;
            mMahasiswaTextView.setText(mMahasiswa.getNama());
        }

        @Override
        public void onClick(View view){
            Toast.makeText(getActivity(),
                            mMahasiswa.getNama() + "clicked!", Toast.LENGTH_SHORT)
                    .show();
            TransitionManager.beginDelayedTransition(mCardView,new AutoTransition());

            if (mCardView.getVisibility() == View.GONE){
                mCardView.setVisibility(View.VISIBLE);
            } else {
                mCardView.setVisibility(View.GONE);
            }
        }
    }

    private class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaHolder>{
        private List<Mahasiswa> mMahasiswas;

        public MahasiswaAdapter(List<Mahasiswa> mahasiswas){
            mMahasiswas = mahasiswas;
        }

        @Override
        public MahasiswaHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new MahasiswaHolder(layoutInflater, parent);
        }

        @Override
        public void  onBindViewHolder(MahasiswaHolder holder, int position){
            Mahasiswa mahasiswa = mMahasiswas.get(position);
            holder.bind(mahasiswa);
            holder.mCardView.setVisibility(View.GONE);

        }

        @Override
        public int getItemCount(){
            return mMahasiswas.size();
        }
    }

    private class PelanggaranHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private CheckBox cbPelanggaran;
        public PelanggaranHolder(@NonNull View itemView) {
            super(itemView);
            cbPelanggaran = itemView.findViewById(R.id.cb_item_pelanggaran);
        }

        public void bind(Pelanggaran item) {
            cbPelanggaran.setText(item.getNama());
        }

        @Override
        public void onClick(View view) {

        }
    }

    private class PelanggaranAdapter extends RecyclerView.Adapter<PelanggaranHolder>{
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
    }


}
