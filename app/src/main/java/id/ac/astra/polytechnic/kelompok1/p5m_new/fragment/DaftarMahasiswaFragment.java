package id.ac.astra.polytechnic.kelompok1.p5m_new.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import id.ac.astra.polytechnic.kelompok1.p5m_new.R;

public class DaftarMahasiswaFragment extends Fragment {
    public static DaftarMahasiswaFragment newInstance() {
        DaftarMahasiswaFragment fragment = new DaftarMahasiswaFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daftar_mahasiswa, container, false);
    }
}
