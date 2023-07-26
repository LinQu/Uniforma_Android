package id.ac.astra.polytechnic.kelompok1.p5m_new.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import id.ac.astra.polytechnic.kelompok1.p5m_new.R;
import id.ac.astra.polytechnic.kelompok1.p5m_new.helper.BulanNavigator;
import id.ac.astra.polytechnic.kelompok1.p5m_new.viewmodel.AbsenListViewModel;

public class HistoryAbsenFragment extends Fragment {

    SharedPreferences preferences;
    private BulanNavigator bulanNavigator;
    private AbsenListViewModel mAbsenListViewModel;
    private View mLayoutEmpty;
    private RecyclerView mAbsenRecyclerView;
    private ProgressBar mProgressBar;
    private TextView mTextViewProgress;
    private TextView mMonthYearTV;
    private Button mPrevButton;
    private Button mNextButton;
    private String mPersentase;
    private String mBulan;
    private int currentYear;
    private int currentMonth;

    String NIM;

    private AbsenAdapter mAdapter = new AbsenAdapter(Collections.emptyList());

    public static HistoryAbsenFragment newInstance() {
        HistoryAbsenFragment fragment = new HistoryAbsenFragment();
        return fragment;
    }

    public void updateUI(List<Object[]> listHistoryAbsen){
        mAdapter = new AbsenAdapter(listHistoryAbsen);
        mAbsenRecyclerView.setAdapter(mAdapter);
        updatePersentase();
        filter("");
    }

    public void updatePersentase(){
        mAbsenListViewModel.getPersentaseKehadiranByBulan(NIM, currentYear, currentMonth).observe(getViewLifecycleOwner(), persentaseKehadiranDTO -> {
            if (persentaseKehadiranDTO != null){
                Log.d("persentase", String.valueOf(persentaseKehadiranDTO.getPersentaseKehadiran()));
                mPersentase = String.format("%.2f", persentaseKehadiranDTO.getPersentaseKehadiran()) + "%";
                mProgressBar.setProgress((int) persentaseKehadiranDTO.getPersentaseKehadiran());
                mTextViewProgress.setText(mPersentase);
                mBulan = persentaseKehadiranDTO.getBulan();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mAbsenListViewModel = new ViewModelProvider(this).get(AbsenListViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history,container,false);
        preferences = getActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE);
         NIM = preferences.getString("nim", "");
        mAbsenRecyclerView = v.findViewById(R.id.recycler_history);
        mAbsenRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAbsenRecyclerView.setHasFixedSize(true);
        mProgressBar = v.findViewById(R.id.progress_bar);
        mTextViewProgress = v.findViewById(R.id.text_view_progress);
        mLayoutEmpty = v.findViewById(R.id.layout_empty_data);
        mMonthYearTV = v.findViewById(R.id.monthYearTV);
        mMonthYearTV.setText("Juni 2023");
        currentMonth = Calendar.getInstance().get(Calendar.MONTH)+1;
        currentYear = Calendar.getInstance().get(Calendar.YEAR);
        mPrevButton = v.findViewById(R.id.prevButton);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] data = bulanNavigator.getPreviousData();
                currentYear = data[0];
                currentMonth = data[1];
                updateMonthYearText();
                mAbsenListViewModel.getAbsenByNimAndMonth(NIM,currentMonth,currentYear).observe(getViewLifecycleOwner(), listHistoryAbsen -> {
                    updateUI(listHistoryAbsen);
                });
            }
        });
        mNextButton = v.findViewById(R.id.nextButton);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] data = bulanNavigator.getNextData();
                currentYear = data[0];
                currentMonth = data[1];
                updateMonthYearText();
                mAbsenListViewModel.getAbsenByNimAndMonth(NIM,currentMonth,currentYear).observe(getViewLifecycleOwner(), listHistoryAbsen -> {
                    updateUI(listHistoryAbsen);
                });
            }
        });
        return v;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAbsenListViewModel.getAbsenByNimAndMonth(NIM,currentMonth,currentYear).observe(getViewLifecycleOwner(), listHistoryAbsen -> {
                updateUI(listHistoryAbsen);
        });



        mAbsenListViewModel.getHistoryListMonthByNim(NIM).observe(getViewLifecycleOwner(), new Observer<List<Object[]>>(){
            @Override
            public void onChanged(List<Object[]> data) {
                if (data != null && data.size() > 0) {

                    Object[] yearMonth = data.get(0);
                    double tempYear = (double) yearMonth[0];
                    double tempMonth = (double) yearMonth[1];
                    //Log.d("yearMonth", String.valueOf(yearMonth[0]) + " " + String.valueOf(yearMonth[1]));
                    if (yearMonth.length >= 2) {

                        currentYear = (int) tempYear;
                        currentMonth = (int) tempMonth;
                    }
                    //convert from List<Object[]> to List<int>
                    List<int[]> dataInt = new ArrayList<>();
                    for (Object[] o : data) {
                        int[] i = new int[2];
                        tempYear = (double) o[0];
                        tempMonth = (double) o[1];
                        i[0] = (int) tempYear;
                        i[1] = (int) tempMonth;
                        Log.d("yearMonth", String.valueOf(i[0]) + " " + String.valueOf(i[1]));
                        dataInt.add(i);
                    }
                    bulanNavigator = new BulanNavigator(dataInt);
                    updateMonthYearText();
                }
            }
        });
    }

    private void updateMonthYearText() {
        // Mengatur teks pada textViewMonthYear dengan nilai bulan dan tahun yang baru
        String monthYearText = getMonthName(currentMonth) + " " + currentYear;
        mMonthYearTV.setText(monthYearText);

        // Di sini Anda juga dapat memanggil fungsi untuk menampilkan data sesuai bulan dan tahun yang baru.
    }

    // Fungsi untuk mendapatkan nama bulan berdasarkan angka bulan (1-12)
    private String getMonthName(int month) {
        String[] monthNames = {"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
        return monthNames[month - 1];
    }

    private class AbsenHolder extends RecyclerView.ViewHolder{
        private TextView mTanggal;
        private TextView mJamMasuk;
        private TextView mJamKeluar;
        private TextView mHari;
        public AbsenHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.history_row_item,parent,false));
            //itemView.setOnClickListener(this);
            mTanggal = itemView.findViewById(R.id.tvTanggalHistory);
            mJamMasuk = itemView.findViewById(R.id.tvMasuk);
            mJamKeluar = itemView.findViewById(R.id.tvKeluar);
            mHari = itemView.findViewById(R.id.tvHari);
        }

        public void bind(Object[] listAbsen){
            mTanggal.setText(listAbsen[1].toString());
            mJamMasuk.setText(listAbsen[2].toString());
            if (listAbsen[3] != null){
                mJamKeluar.setText(listAbsen[3].toString());
            }else {
                mJamKeluar.setText("-");
            }
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    LocalDate date = LocalDate.parse(listAbsen[1].toString(), DateTimeFormatter.ISO_DATE);
                    mHari.setText(getDayOfWeek(date));
                }
            //mHari.setText(listAbsen[3].toString());
        }
    }

    private class AbsenAdapter extends RecyclerView.Adapter<AbsenHolder>{
        private List<Object[]> mAbsenList;
        public AbsenAdapter(List<Object[]> absenList){
            mAbsenList = absenList;
        }

        @NonNull
        @Override
        public AbsenHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new AbsenHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull AbsenHolder holder, int position) {
            Object[] listAbsen = mAbsenList.get(position);
            holder.bind(listAbsen);
        }

        @Override
        public int getItemCount() {
            return mAbsenList.size();
        }

        public void filterList(List<Object[]> filteredList){
            if (filteredList.size() == 0){
                mLayoutEmpty.setVisibility(View.VISIBLE);
                mAbsenRecyclerView.setVisibility(View.GONE);
            }else{
                mLayoutEmpty.setVisibility(View.GONE);
                mAbsenRecyclerView.setVisibility(View.VISIBLE);

            }
            this.mAbsenList = filteredList;
            notifyDataSetChanged();
        }
    }

    public void filter(String text){
        List<Object[]> filteredList = new ArrayList<>();
        for (Object[] item : mAdapter.mAbsenList){
            if (item[0].toString().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        mAdapter.filterList(filteredList);
    }

    public static String getDayOfWeek(LocalDate date) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        Locale locale = new Locale("id", "ID"); // Untuk menggunakan bahasa Indonesia

            return dayOfWeek.getDisplayName(TextStyle.FULL, locale);
        }
        return "";
    }
}
