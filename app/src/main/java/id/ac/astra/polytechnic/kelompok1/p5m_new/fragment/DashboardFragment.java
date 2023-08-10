package id.ac.astra.polytechnic.kelompok1.p5m_new.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import id.ac.astra.polytechnic.kelompok1.p5m_new.LoginActivity;
import id.ac.astra.polytechnic.kelompok1.p5m_new.MainActivity;
import id.ac.astra.polytechnic.kelompok1.p5m_new.R;
import id.ac.astra.polytechnic.kelompok1.p5m_new.helper.UniformaHelper;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.P5m;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.LoginMahasiswaResponse;
import id.ac.astra.polytechnic.kelompok1.p5m_new.viewmodel.MahasiswaListViewModel;
import id.ac.astra.polytechnic.kelompok1.p5m_new.viewmodel.P5mListViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {
    SharedPreferences preferences;

    UniformaHelper mUniformaHelper;

    MahasiswaListViewModel mMahasiswaListViewModel;

    P5mListViewModel mP5mListViewModel;
    ConstraintLayout mConstraintCRUD;

    TextView mTextHelloUser;
    TextView mTextTanggalSekarang;
    CardView mCardPengguna;
    CardView mCardPelanggaran;
    ImageButton mBtnLogout;
    String kelas;

    private Top3Adapter mAdapter = new Top3Adapter(Collections.emptyList());

    private RecyclerView mRecyclerViewTop;

    public static DashboardFragment newInstance() {
        DashboardFragment fragment = new DashboardFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMahasiswaListViewModel = new ViewModelProvider(this).get(MahasiswaListViewModel.class);
        mP5mListViewModel = new ViewModelProvider(this).get(P5mListViewModel.class);
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dashboard_instruktur, container, false);
        preferences = getActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE);
        String name = (preferences.getString("kry_nama", ""));
        String role = (preferences.getString("role", ""));
         kelas = (preferences.getString("kelas", ""));
        mConstraintCRUD = v.findViewById(R.id.constraintCRUD);
        if(role.equals("Koordinator Tata Tertib")) {
            mConstraintCRUD.setVisibility(View.VISIBLE);
        } else {
            mConstraintCRUD.setVisibility(View.GONE);
        }
        mTextHelloUser = v.findViewById(R.id.hallo_user);
        mTextHelloUser.setText("Hello, " + name);
        mTextTanggalSekarang = v.findViewById(R.id.txtRole);
        mTextTanggalSekarang.setText("Login Sebagai " + role);
        mCardPelanggaran = v.findViewById(R.id.cardPelanggaran);
        mBtnLogout = v.findViewById(R.id.image_notif);
        mRecyclerViewTop = v.findViewById(R.id.rvTop3);
        mRecyclerViewTop.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();

            }
        });
        SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        mP5mListViewModel.top3NimAndTotalJamMinus(kelas.substring(kelas.length() - 2)).observe(getViewLifecycleOwner(), new Observer<List<Object[]>>() {
            @Override
            public void onChanged(List<Object[]> objects) {
                if(objects.size() == 0) {
                    pDialog.dismissWithAnimation(); // Dismiss the loading dialog
                    Toast.makeText(getActivity(), "Data Kosong", Toast.LENGTH_SHORT).show();
                    // Tampilkan pesan kesalahan kepada pengguna



                    // Show error message to the user
                    new SweetAlertDialog(requireContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error")
                            .setContentText("An error occurred while loading data. Please try again later.")
                            .setConfirmText("Try Again")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    loadData(); // Retry loading data
                                    sweetAlertDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                }
                mAdapter = new Top3Adapter(objects);
                mRecyclerViewTop.setAdapter(mAdapter);
                pDialog.dismissWithAnimation();
            }
        });


        mCardPelanggaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PelanggaranFragment pelanggaranFragment = new PelanggaranFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, pelanggaranFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                Toast.makeText(getActivity(), "Fitur Crud Pelanggaran belum tersedia", Toast.LENGTH_SHORT).show();
            }
        });
        mCardPengguna = v.findViewById(R.id.cardPengguna);
        mCardPengguna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PenggunaFragment penggunaFragment = new PenggunaFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, penggunaFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                //Toast.makeText(getActivity(), "Fitur Crud Pengguna belum tersedia", Toast.LENGTH_SHORT).show();

            }
        });

        // Inflate the layout for this fragment
        return v;
    }

    private void loadData() {
        SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        mP5mListViewModel.top3NimAndTotalJamMinus(kelas.substring(kelas.length() - 2)).observe(getViewLifecycleOwner(), new Observer<List<Object[]>>() {
            @Override
            public void onChanged(List<Object[]> objects) {
                if (objects.size() == 0){
                    pDialog.dismissWithAnimation(); // Dismiss the loading dialog

                    // Show error message to the user
                    new SweetAlertDialog(requireContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error")
                            .setContentText("An error occurred while loading data. Please try again later.")
                            .setConfirmText("Try Again")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    loadData(); // Retry loading data
                                    sweetAlertDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                }
                mAdapter = new Top3Adapter(objects);
                mRecyclerViewTop.setAdapter(mAdapter);
                pDialog.dismissWithAnimation();
            }

        });
    }

    private class Top3Holder extends RecyclerView.ViewHolder {

        private TextView mNamamhs;

        private TextView mJammin;

        private P5m mP5m;

        public Top3Holder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.top3min_row_item, parent, false));

            mNamamhs = itemView.findViewById(R.id.nama_mhs);
            mJammin = itemView.findViewById(R.id.jammin_presentase);

        }

        public void bind(Object[] p5m) {
            String nim = (String) p5m[0];
            Double jammin = (Double) p5m[1]; // Mengambil nilai Double dari hasil query

            String jamminString = Double.toString(jammin); // Konversi Double menjadi String jika diperlukan

            mMahasiswaListViewModel.getLoginMahasiswa(nim).observe(getViewLifecycleOwner(), new Observer<LoginMahasiswaResponse>() {
                @Override
                public void onChanged(LoginMahasiswaResponse loginMahasiswaResponse) {
                    String nama = loginMahasiswaResponse.getmMahasiswa().getNama();
                    String[] kataKata = nama.split(" "); // Memisahkan nama menjadi array kata-kata
                    String duaKataPertama = ""; // Mengambil dua kata pertama
                    if (nama.length() > 19) {
                        if (kataKata.length >= 2) {
                            duaKataPertama = kataKata[0] + " " + kataKata[1];
                        }
                    }else {
                        duaKataPertama = nama;
                    }

                    mNamamhs.setText(duaKataPertama);
                }
            });

            mJammin.setText(jamminString);
        }
    }

    private class Top3Adapter extends RecyclerView.Adapter<Top3Holder> {


        private List<Object[]> mTopList;

        public Top3Adapter(List<Object[]> topList) {
            mTopList = topList;
        }

        @Override
        public Top3Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new Top3Holder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull Top3Holder holder, int position) {
            Object[] toplist = mTopList.get(position);
            holder.bind(toplist);
        }

        @Override
        public int getItemCount() {
            return mTopList.size();
        }
    }
}