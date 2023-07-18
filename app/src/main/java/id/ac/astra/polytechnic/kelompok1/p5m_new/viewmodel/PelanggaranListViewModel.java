package id.ac.astra.polytechnic.kelompok1.p5m_new.viewmodel;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Pelanggaran;
import id.ac.astra.polytechnic.kelompok1.p5m_new.repository.PelanggaranRepository;
import id.ac.astra.polytechnic.kelompok1.p5m_new.repository.PenggunaRepository;

public class PelanggaranListViewModel extends ViewModel {
    private static final String TAG = "PelanggaranListViewModel";
    private List<Pelanggaran> mPelanggaran;
    private PelanggaranRepository mPelanggaranRepository;

    @SuppressLint("LongLogTag")
    public PelanggaranListViewModel(){
        Log.d(TAG, "PelList constructor called");
        mPelanggaranRepository = PelanggaranRepository.get();
    }

    public LiveData<List<Pelanggaran>> getAllPelanggaran(){
        return mPelanggaranRepository.getPelangarans();
    }
}
