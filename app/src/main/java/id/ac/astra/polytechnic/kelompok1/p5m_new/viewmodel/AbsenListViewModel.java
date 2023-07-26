package id.ac.astra.polytechnic.kelompok1.p5m_new.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Objects;

import id.ac.astra.polytechnic.kelompok1.p5m_new.model.KehadiranPerBulanDTO;
import id.ac.astra.polytechnic.kelompok1.p5m_new.repository.AbsenRepository;

public class AbsenListViewModel extends ViewModel {
    private static final String TAG = "AbsenListViewModel";
    private AbsenRepository mAbsenRepository;

    public AbsenListViewModel(){
        mAbsenRepository = AbsenRepository.getInstance();
    }

    public LiveData<List<Object[]>> getHistoryAbsenByNim(String nim){
       return mAbsenRepository.getHistoryAbsenByNim(nim);
    }

    public LiveData<List<Object[]>> getAbsenByNimAndMonth(String nim, int month, int year){
        return mAbsenRepository.getAbsenByNimAndMonth(nim, month, year);
    }

    public LiveData<List<KehadiranPerBulanDTO>> getPersentaseKehadiran(String NIM){
        return mAbsenRepository.getPersentaseKehadiran(NIM);
    }

    public LiveData<KehadiranPerBulanDTO> getPersentaseKehadiranByBulan(String NIM, int tahun, int bulan){
        return mAbsenRepository.getPersentaseKeahadiraByMonth(NIM, tahun, bulan);
    }

    public LiveData<List<Object[]>> getHistoryListMonthByNim(String nim){
        return mAbsenRepository.getHistoryListMonthByNim(nim);
    }
}
