package id.ac.astra.polytechnic.kelompok1.p5m_new.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import id.ac.astra.polytechnic.kelompok1.p5m_new.R;

public class DashboardMahasiswaFragment extends Fragment {
    SharedPreferences preferences;
    TextView txtNama;
    TextView txtRole;

    public static DashboardMahasiswaFragment newInstance() {
        DashboardMahasiswaFragment fragment = new DashboardMahasiswaFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);
        preferences = getActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE);
        String name = (preferences.getString("nama", ""));
        String role = (preferences.getString("role", ""));
        txtNama = v.findViewById(R.id.tvNamaMahasiswa);
        txtRole = v.findViewById(R.id.tvNamaRoleMahasiswa);
        txtNama.setText("Hello, " + name);
        txtRole.setText("Login Sebagai " + role);
        return v;
    }

}