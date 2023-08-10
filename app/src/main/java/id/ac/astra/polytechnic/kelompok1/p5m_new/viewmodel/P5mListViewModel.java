package id.ac.astra.polytechnic.kelompok1.p5m_new.viewmodel;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Map;

import id.ac.astra.polytechnic.kelompok1.p5m_new.model.DetailP5m;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.ListDetailP5mResponse;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.P5mResponse;
import id.ac.astra.polytechnic.kelompok1.p5m_new.repository.P5mRepository;
import id.ac.astra.polytechnic.kelompok1.p5m_new.repository.PelanggaranRepository;

public class P5mListViewModel extends ViewModel {
    private static final String TAG = "P5mListViewModel";
    private P5mRepository mP5mRepository;
    @SuppressLint("LongLogTag")
    public P5mListViewModel(){
        Log.d(TAG, "PelList constructor called");
        mP5mRepository = P5mRepository.get();
    }

    public LiveData<List<DetailP5m>> postP5m(Map<String, List<Integer>> p5mParam, String kelasParam){
        return mP5mRepository.postP5m(p5mParam,kelasParam);
    }

    public LiveData<List<Object[]>> findPelanggaranOccurrencesByNim(String nim,int year,int month){
       return mP5mRepository.findPelanggaranOccurrencesByNim(nim,year,month);
    }

    public LiveData<List<Object[]>> top3NimAndTotalJamMinus(String kelas){
        return mP5mRepository.top3NimAndTotalJamMinus(kelas);
    }
}
