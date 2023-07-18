package id.ac.astra.polytechnic.kelompok1.p5m_new.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import id.ac.astra.polytechnic.kelompok1.p5m_new.LoginActivity;
import id.ac.astra.polytechnic.kelompok1.p5m_new.MainActivity;
import id.ac.astra.polytechnic.kelompok1.p5m_new.R;
import id.ac.astra.polytechnic.kelompok1.p5m_new.helper.UniformaHelper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {
    SharedPreferences preferences;

    UniformaHelper mUniformaHelper;

    TextView mTextHelloUser;
    TextView mTextTanggalSekarang;
    CardView mCardPengguna;
    CardView mCardPelanggaran;
    ImageButton mBtnLogout;

    public static DashboardFragment newInstance() {
        DashboardFragment fragment = new DashboardFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dashboard_instruktur, container, false);
        preferences = getActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE);
        String name = (preferences.getString("kry_nama", ""));
        String role = (preferences.getString("role", ""));

        mTextHelloUser = v.findViewById(R.id.hallo_user);
        mTextHelloUser.setText("Hello, " + name);
        mTextTanggalSekarang = v.findViewById(R.id.txtRole);
        mTextTanggalSekarang.setText("Login Sebagai " + role);
        mCardPelanggaran = v.findViewById(R.id.cardPelanggaran);
        mBtnLogout = v.findViewById(R.id.image_notif);
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
        mCardPelanggaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PelanggaranFragment pelanggaranFragment = new PelanggaranFragment();
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_container, pelanggaranFragment, "findThisFragment")
//                        .addToBackStack(null)
//                        .commit();
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
}