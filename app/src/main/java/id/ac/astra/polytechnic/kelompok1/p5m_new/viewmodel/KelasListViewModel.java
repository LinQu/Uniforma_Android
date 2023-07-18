package id.ac.astra.polytechnic.kelompok1.p5m_new.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Kelas;
import id.ac.astra.polytechnic.kelompok1.p5m_new.repository.KelasRepository;

public class KelasListViewModel extends ViewModel {
    private static final String TAG = "KelasListViewModel";
    private KelasRepository mKelasRepository;
    public KelasListViewModel(){
        mKelasRepository = KelasRepository.get();
    }

    public LiveData<List<Kelas>> getAllKelas(){
        return mKelasRepository.getAllKelas();
    }
}
