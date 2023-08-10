package id.ac.astra.polytechnic.kelompok1.p5m_new.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import id.ac.astra.polytechnic.kelompok1.p5m_new.R;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.KehadiranPerBulanDTO;
import id.ac.astra.polytechnic.kelompok1.p5m_new.viewmodel.AbsenListViewModel;
import id.ac.astra.polytechnic.kelompok1.p5m_new.viewmodel.P5mListViewModel;

public class ProfileMahasiswaFragment extends Fragment {
    private static final String TAG = "ProfileMahasiswaFragment";
    private AbsenListViewModel mAbsenListViewModel;
    private P5mListViewModel mP5mListViewModel;
    SharedPreferences preferences;
    ImageView ivProfile;
    TextView tvNama,tvNIM,tvNamaBulan;
    String nim;
    String nama;
    String urlPhoto;
    LineChart lineChartAbsen;
    BarChart lineChartPelanggaran;
    Button mPrevButton,mNextButton;
    int currentYear,currentMonth;
    List<KehadiranPerBulanDTO> mListKehadiranPerBulanDTO;

    public ProfileMahasiswaFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mAbsenListViewModel = new ViewModelProvider(this).get(AbsenListViewModel.class);
        mP5mListViewModel = new ViewModelProvider(this).get(P5mListViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_mahasiswa, container, false);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Adding 1 because Calendar.MONTH is 0-based
        currentMonth = month;
        currentYear = year;
        preferences = getActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE);
        nim = preferences.getString("nim", "");
        nama = preferences.getString("nama", "");
        urlPhoto = preferences.getString("urlPhoto", "");
        Bundle dataBundle = getArguments();
        if (dataBundle != null) {
           nim = dataBundle.getString("nim", "");
           nama = dataBundle.getString("nama", "");
           urlPhoto = dataBundle.getString("url_photo", "");
            // Gunakan data yang diterima di sini
        }

        lineChartAbsen = v.findViewById(R.id.lineChartAbsen);
        lineChartPelanggaran = v.findViewById(R.id.linechartPelanggaran);
        ivProfile = v.findViewById(R.id.imgProfileMhs);
        tvNama = v.findViewById(R.id.tv_dashboard_name);
        tvNIM = v.findViewById(R.id.tv_profile_nim);
        tvNIM.setText(nim);
        tvNama.setText(nama);
        tvNamaBulan = v.findViewById(R.id.tvNamaBulan);
        tvNamaBulan.setText(getMonthNameIndonesia(currentMonth));
        mPrevButton = v.findViewById(R.id.prevButton);
        SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPrevNextMont(currentMonth,false);
                mP5mListViewModel.findPelanggaranOccurrencesByNim(nim,currentYear,currentMonth).observe(getViewLifecycleOwner(), new Observer<List<Object[]>>() {
                    @Override
                    public void onChanged(List<Object[]> objects) {
                        setupLineChartPelanggaran(objects);

                    }
                });
                tvNamaBulan.setText(getMonthNameIndonesia(currentMonth) + "-" + currentYear);
            }
        });
        mNextButton = v.findViewById(R.id.nextButton);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPrevNextMont(currentMonth,true);
                mP5mListViewModel.findPelanggaranOccurrencesByNim(nim,currentYear,currentMonth).observe(getViewLifecycleOwner(), new Observer<List<Object[]>>() {
                    @Override
                    public void onChanged(List<Object[]> objects) {
                        setupLineChartPelanggaran(objects);

                    }
                });
                tvNamaBulan.setText(getMonthNameIndonesia(currentMonth) + "-" + currentYear);

            }
        });
        Glide.with(this)
                .load("https://sia.polytechnic.astra.ac.id/Files/"+urlPhoto)
                .transform(new CenterCrop(),new RoundedCorners(190))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivProfile);

        mAbsenListViewModel.getPersentaseKehadiran(nim).observe(getViewLifecycleOwner(), new Observer<List<KehadiranPerBulanDTO>>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onChanged(List<KehadiranPerBulanDTO> kehadiranPerBulanDTOS) {
                mListKehadiranPerBulanDTO = kehadiranPerBulanDTOS;
                Log.d(TAG, "onChanged: "+mListKehadiranPerBulanDTO);
                setupLineChart(mListKehadiranPerBulanDTO);

            }
        });
        mP5mListViewModel.findPelanggaranOccurrencesByNim(nim,currentYear,currentMonth).observe(getViewLifecycleOwner(), new Observer<List<Object[]>>() {
            @Override
            public void onChanged(List<Object[]> objects) {
                setupLineChartPelanggaran(objects);
                pDialog.dismissWithAnimation();
            }
        });
        return v;
    }

    private void setupLineChartPelanggaran(List<Object[]> data){
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            // Convert Double to float using the floatValue() method
            float value = ((Double) data.get(i)[3]).floatValue();
            entries.add(new BarEntry(i, value));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Persentase Pelanggaran");
        dataSet.setColor(Color.BLUE);

        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.5f); // Set the width of the bars


        lineChartPelanggaran.setData(barData);
        lineChartPelanggaran.setDragXEnabled(true);
        lineChartPelanggaran.setVisibleXRangeMaximum(5);

        XAxis xAxis = lineChartPelanggaran.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(getLabelPelanggaran(data)));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setLabelCount(data.size() + 1);

        YAxis leftAxis = lineChartPelanggaran.getAxisLeft();
        leftAxis.setAxisMinimum(0f);

        YAxis rightAxis = lineChartPelanggaran.getAxisRight();
        rightAxis.setEnabled(false);

        Description description = new Description();
        description.setText("Data Kehadiran Per Bulan");
        lineChartPelanggaran.setDescription(description);

        Legend legend = lineChartPelanggaran.getLegend();
        legend.setEnabled(false);

        lineChartPelanggaran.invalidate();
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

    private String[] getLabelPelanggaran(List<Object[]> dataItems){
        String[] labels = new String[dataItems.size()];
        for (int i = 0; i < dataItems.size(); i++) {
            Object[] rowData = dataItems.get(i);
            String namaPelanggaran = (String) rowData[1]; // The violation name is at index 1
            labels[i] = namaPelanggaran;
        }
        return labels;
    }

    private List<String> getLabels(List<KehadiranPerBulanDTO> dataItems) {
        List<String> labels = new ArrayList<>();
        for (KehadiranPerBulanDTO dataItem : dataItems) {
            labels.add(dataItem.getBulan());
        }
        return labels;
    }

    public static Calendar getNextOrPrevMonthYear(int currentMonth, boolean next, boolean year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, currentMonth - 1); // Adjusting to 0-based index

        if (!next) {
            currentMonth--;
        }else{
            currentMonth++;
        }

        if (year) {
            calendar.add(Calendar.YEAR, currentMonth);
        } else {
            calendar.add(Calendar.MONTH, currentMonth);

            // Check if month overflows to next/previous year
            int newMonth = calendar.get(Calendar.MONTH);
            int newYear = calendar.get(Calendar.YEAR);

            if (newMonth < 0) {
                newMonth = Calendar.DECEMBER;
                newYear--;
                calendar.set(Calendar.YEAR, newYear);
            } else if (newMonth > Calendar.DECEMBER) {
                newMonth = Calendar.JANUARY;
                newYear++;
                calendar.set(Calendar.YEAR, newYear);
            }
            calendar.set(Calendar.MONTH, newMonth);
        }

        return calendar;
    }

    public void getPrevNextMont(int curent,boolean next){
        if(next){
            if(curent==12){
                currentYear++;
                currentMonth = 1;
            }else{
                currentMonth++;
            }
        }else{
            if(curent==1){
                currentYear--;
                currentMonth = 12;
            }else{
                currentMonth--;
            }
        }
    }

    public static String getMonthNameIndonesia(int monthNumber) {
        String[] indonesianMonths = new DateFormatSymbols().getMonths();
        return indonesianMonths[monthNumber - 1];
    }
}
