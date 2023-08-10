package id.ac.astra.polytechnic.kelompok1.p5m_new.fragment;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
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

import cn.pedant.SweetAlert.SweetAlertDialog;
import id.ac.astra.polytechnic.kelompok1.p5m_new.R;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Mahasiswa;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.LoginMahasiswaResponse;
import id.ac.astra.polytechnic.kelompok1.p5m_new.viewmodel.MahasiswaListViewModel;

public class MahasiswaListFragment extends Fragment {

    private static final String TAG = "MahasiswaListFragment";


    private MahasiswaListViewModel mMahasiswaListViewModel;
    private RecyclerView mMahasiswaRecyclerView;

    private List<Mahasiswa> mMahasiswaFrag;

    SharedPreferences preferences;
    TextView mTextHelloUser;
    TextView mTextTanggalSekarang;

    private String kelas;

    private MahasiswaAdapter mAdapter = new MahasiswaAdapter(Collections.emptyList());



//    private void updateUI(){
//        List<Mahasiswa> mahasiswas = mMahasiswaListViewModel.getMahasiswa();
//        mAdapter = new MahasiswaAdapter(mahasiswas);
//        mMahasiswaRecyclerView.setAdapter(mAdapter);
//    }

    private void updateUIWithMahasiswa(List<Mahasiswa> mahasiswaList){
        mAdapter = new MahasiswaAdapter(mahasiswaList);
        mMahasiswaFrag = mahasiswaList;
        mMahasiswaRecyclerView.setAdapter(mAdapter);
        /*      filter("");*/
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mMahasiswaListViewModel = new ViewModelProvider(this).get(MahasiswaListViewModel.class);

        Log.d(TAG, "Total mahasiswa: " + mMahasiswaListViewModel.getMahasiswa().size());
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_daftar_mahasiswa, container, false);
        mMahasiswaRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewList);
        mMahasiswaRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        preferences = getActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE);
        String name = (preferences.getString("kry_nama", ""));
        String role = (preferences.getString("role", ""));
        kelas = (preferences.getString("kelas", ""));
        mTextHelloUser = view.findViewById(R.id.hallo_user);
        mTextHelloUser.setText("Hello, " + name);
        mTextTanggalSekarang = view.findViewById(R.id.txtRole);
        mTextTanggalSekarang.setText("Login Sebagai " + role);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        mMahasiswaListViewModel.getMahasiswaByKelas(kelas).observe(getViewLifecycleOwner(), new Observer<List<Mahasiswa>>() {
            @Override
            public void onChanged(List<Mahasiswa> mahasiswasi) {
                updateUIWithMahasiswa(mahasiswasi);
                pDialog.dismissWithAnimation();

            }
        });
    }

    private class MahasiswaHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mMahasiswaTextView;
        private Mahasiswa mMahasiswa;

        private MaterialCardView mCardView;


        public MahasiswaHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.mahasiswa_item, parent, false));
            itemView.setOnClickListener(this);

            mMahasiswaTextView = (TextView) itemView.findViewById(R.id.namaMahasiswa);

        }

        public void bind(Mahasiswa mahasiswa){
            mMahasiswa = mahasiswa;
            mMahasiswaTextView.setText(mMahasiswa.getNama());
        }

        @Override
        public void onClick(View view){
            ProfileMahasiswaFragment fragmentSource = new ProfileMahasiswaFragment();
            Bundle dataBundle = new Bundle();
            dataBundle.putString("nim", mMahasiswa.getNim());
            dataBundle.putString("nama", mMahasiswa.getNama());
            mMahasiswaListViewModel.getLoginMahasiswa(mMahasiswa.getNim()).observe(getViewLifecycleOwner(), new Observer<LoginMahasiswaResponse>() {
                @Override
                public void onChanged(LoginMahasiswaResponse loginMahasiswaResponse) {
                    dataBundle.putString("url_photo", loginMahasiswaResponse.getmMahasiswa().getDul_pas_foto());
                    fragmentSource.setArguments(dataBundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragmentSource).addToBackStack(TAG).commit();
                    Toast.makeText(getActivity(),
                                    mMahasiswa.getNama() + "clicked!", Toast.LENGTH_SHORT)
                            .show();
                }
            });


        }
    }

    private class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaHolder>{
        private List<Mahasiswa> mMahasiswas;

        public MahasiswaAdapter(List<Mahasiswa> mahasiswas){
            mMahasiswas = mahasiswas;
        }

        @Override
        public  MahasiswaHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new MahasiswaHolder(layoutInflater, parent);
        }

        @Override
        public void  onBindViewHolder(MahasiswaHolder holder, int position){
            Mahasiswa mahasiswa = mMahasiswas.get(position);
            holder.bind(mahasiswa);

        }

        @Override
        public int getItemCount(){
            return mMahasiswas.size();
        }
    }

}
