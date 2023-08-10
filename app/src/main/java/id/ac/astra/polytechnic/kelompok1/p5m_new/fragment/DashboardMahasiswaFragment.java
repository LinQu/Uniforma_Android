package id.ac.astra.polytechnic.kelompok1.p5m_new.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import id.ac.astra.polytechnic.kelompok1.p5m_new.LoginActivity;
import id.ac.astra.polytechnic.kelompok1.p5m_new.R;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.KehadiranPerBulanDTO;
import id.ac.astra.polytechnic.kelompok1.p5m_new.viewmodel.AbsenListViewModel;

public class DashboardMahasiswaFragment extends Fragment {
    SharedPreferences preferences;
    AbsenListViewModel mAbsenListViewModel;

    TextView txtNama;
    TextView txtRole;
    TextView txtHadir,txtTelat,txtTidak;
    ImageButton btnLogout;
    private PresentaseAdapter mAdapter = new PresentaseAdapter(Collections.emptyList());

    private RecyclerView mRecyclerViewBulan;

    public static DashboardMahasiswaFragment newInstance() {
        DashboardMahasiswaFragment fragment = new DashboardMahasiswaFragment();
        return fragment;
    }

    public void updateUI(List<KehadiranPerBulanDTO> listKehadiran){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAbsenListViewModel = new ViewModelProvider(this).get(AbsenListViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);
        SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        preferences = getActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Adding 1 because Calendar.MONTH is 0-based
        String nim = (preferences.getString("nim", ""));
        String name = (preferences.getString("nama", ""));
        String role = (preferences.getString("role", ""));
        txtHadir = v.findViewById(R.id.hari);
        txtTelat = v.findViewById(R.id.hariTelat);
        txtTidak = v.findViewById(R.id.tidakhadir);
        mAbsenListViewModel.calculateAbsen(nim,year,month).observe(getViewLifecycleOwner(), new Observer<Object[]>() {
            @Override
            public void onChanged(Object[] objects) {
                txtHadir.setText(objects[0].toString().substring(0,objects[0].toString().length()-2) + " Hari");
                txtTelat.setText(objects[1].toString().substring(0,objects[1].toString().length()-2) + " Hari");
                txtTidak.setText(objects[2].toString().substring(0,objects[2].toString().length()-2) + " Hari");
            }
        });
        txtNama = v.findViewById(R.id.tvNamaMahasiswa);
        txtRole = v.findViewById(R.id.tvNamaRoleMahasiswa);
        txtNama.setText("Hello, " + name);
        txtRole.setText("Login Sebagai " + role);
        btnLogout = v.findViewById(R.id.btnLogoutMahasiswa);
        mRecyclerViewBulan = v.findViewById(R.id.rvKehadiran);
        mRecyclerViewBulan.setLayoutManager(new LinearLayoutManager(getActivity()));
        btnLogout.setOnClickListener(new View.OnClickListener() {
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
        mAbsenListViewModel.getPersentaseKehadiran(nim).observe(getViewLifecycleOwner(), new Observer<List<KehadiranPerBulanDTO>>() {
            @Override
            public void onChanged(List<KehadiranPerBulanDTO> kehadiranPerBulanDTOS) {
                int dataSize = kehadiranPerBulanDTOS.size();
                int startIndex = dataSize >= 3 ? dataSize - 3 : 0; // Memastikan tidak ada IndexOutOfBoundsException
                List<KehadiranPerBulanDTO> listPersen = new ArrayList<>();
                for (int i = startIndex; i < dataSize; i++) {
                    listPersen.add(kehadiranPerBulanDTOS.get(i));
                }
                mAdapter = new PresentaseAdapter(listPersen);
                mRecyclerViewBulan.setAdapter(mAdapter);
                pDialog.dismissWithAnimation();
            }
        });

        return v;
    }

    private class PresentaseHolder extends RecyclerView.ViewHolder {
        private List<KehadiranPerBulanDTO> mKehadiranPerBulanDTOS;

        private TextView mBulan;

        private TextView mPresentase;

        private KehadiranPerBulanDTO mKehadiranPerBulanDTO;


        public PresentaseHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.persentase_row_item,parent,false));

            mBulan = itemView.findViewById(R.id.bulan1);
            mPresentase = itemView.findViewById(R.id.presentase1);

        }

        public void bind(KehadiranPerBulanDTO kehadiranPerBulanDTO){

            mKehadiranPerBulanDTO = kehadiranPerBulanDTO;
            mBulan.setText(mKehadiranPerBulanDTO.getBulan());
            double persentaseKehadiran = mKehadiranPerBulanDTO.getPersentaseKehadiran();
            DecimalFormat decimalFormat = new DecimalFormat("#");
            // Format angka persentase menjadi angka bulat
            String formattedPersentase = decimalFormat.format(persentaseKehadiran);

            mPresentase.setText(formattedPersentase+"%");
        }
    }

    private class PresentaseAdapter extends RecyclerView.Adapter<PresentaseHolder>{

        private List<KehadiranPerBulanDTO> mKehadiranPerBulanDTOS;

        public PresentaseAdapter(List<KehadiranPerBulanDTO> kehadiranPerBulanDTOList){
            mKehadiranPerBulanDTOS = kehadiranPerBulanDTOList;
        }



        @Override
        public PresentaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new PresentaseHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull PresentaseHolder holder, int position) {
            KehadiranPerBulanDTO kehadiranPerBulanDTO = mKehadiranPerBulanDTOS.get(position);
            holder.bind(kehadiranPerBulanDTO);
        }

        @Override
        public int getItemCount() {
            return mKehadiranPerBulanDTOS.size();
        }
    }

}
