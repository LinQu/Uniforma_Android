package id.ac.astra.polytechnic.kelompok1.p5m_new.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import id.ac.astra.polytechnic.kelompok1.p5m_new.R;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.KehadiranPerBulanDTO;
import id.ac.astra.polytechnic.kelompok1.p5m_new.viewmodel.AbsenListViewModel;

public class ProfileMahasiswaFragment extends Fragment {
    private static final String TAG = "ProfileMahasiswaFragment";
    private AbsenListViewModel mAbsenListViewModel;
    SharedPreferences preferences;
    ImageView ivProfile;
    TextView tvNama,tvNIM;
    String nim;
    String nama;
    String urlPhoto;
    LineChart lineChartAbsen;
    List<KehadiranPerBulanDTO> mListKehadiranPerBulanDTO;

    public ProfileMahasiswaFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mAbsenListViewModel = new ViewModelProvider(this).get(AbsenListViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_mahasiswa, container, false);
        preferences = getActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE);
        nim = preferences.getString("nim", "");
        nama = preferences.getString("nama", "");
        urlPhoto = preferences.getString("urlPhoto", "");
        lineChartAbsen = v.findViewById(R.id.lineChartAbsen);
        ivProfile = v.findViewById(R.id.imgProfileMhs);
        tvNama = v.findViewById(R.id.tv_dashboard_name);
        tvNIM = v.findViewById(R.id.tv_profile_nim);
        tvNIM.setText(nim);
        tvNama.setText(nama);
        Glide.with(this)
                .load("https://sia.polytechnic.astra.ac.id/Files/"+urlPhoto)
                .transform(new CenterCrop(),new RoundedCorners(190))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivProfile);

        mAbsenListViewModel.getPersentaseKehadiran(nim).observe(getViewLifecycleOwner(), new Observer<List<KehadiranPerBulanDTO>>() {
            @Override
            public void onChanged(List<KehadiranPerBulanDTO> kehadiranPerBulanDTOS) {
                mListKehadiranPerBulanDTO = kehadiranPerBulanDTOS;
                Log.d(TAG, "onChanged: "+mListKehadiranPerBulanDTO);
                setupLineChart(mListKehadiranPerBulanDTO);
            }
        });
        return v;
    }

    private void setupLineChart(List<KehadiranPerBulanDTO> data){
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < data.size(); i++){
            entries.add(new Entry(i,(float) data.get(i).getPersentaseKehadiran()));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Persentase Kehadiran");
        dataSet.setColor(Color.BLUE);
        dataSet.setCircleColor(Color.BLUE);
        dataSet.setLineWidth(2f);
        dataSet.setCircleRadius(5f);
        dataSet.setDrawValues(true);

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);

        LineData lineData = new LineData(dataSets);
        lineChartAbsen.setData(lineData);
        lineChartAbsen.setDragXEnabled(true);
        lineChartAbsen.setVisibleXRangeMaximum(5);

        XAxis xAxis = lineChartAbsen.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(getLabels(data)));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setLabelCount(data.size());

        YAxis leftAxis = lineChartAbsen.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(100f);

        YAxis rightAxis = lineChartAbsen.getAxisRight();
        rightAxis.setEnabled(false);

        Description description = new Description();
        description.setText("Data Kehadiran Per Bulan");
        lineChartAbsen.setDescription(description);

        Legend legend = lineChartAbsen.getLegend();
        legend.setEnabled(false);

        lineChartAbsen.invalidate();

    }

    private List<String> getLabels(List<KehadiranPerBulanDTO> dataItems) {
        List<String> labels = new ArrayList<>();
        for (KehadiranPerBulanDTO dataItem : dataItems) {
            labels.add(dataItem.getBulan());
        }
        return labels;
    }
}
