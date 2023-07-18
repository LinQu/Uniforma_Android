package id.ac.astra.polytechnic.kelompok1.p5m_new.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.ac.astra.polytechnic.kelompok1.p5m_new.helper.CrudType;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Pengguna;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.ListPenggunaResponse;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.PenggunaResponse;
import id.ac.astra.polytechnic.kelompok1.p5m_new.repository.PenggunaRepository;

public class PenggunaListViewModel extends ViewModel {
    private static final String TAG = "PenggunaListViewModel";
    private PenggunaRepository mPenggunaRepository;
    public PenggunaListViewModel(){
        Log.d(TAG, "PenggunaListViewModel constructor called");
        mPenggunaRepository = PenggunaRepository.getInstance();
    }

    public LiveData<PenggunaResponse> getPenggunaByNama(String nama){
        return mPenggunaRepository.getPenggunaByName(nama);
    }

    public LiveData<PenggunaResponse> addToPosition(int position, Pengguna pengguna) {
        return mPenggunaRepository.updatePengguna(CrudType.ADD, position, pengguna);
    }

    public LiveData<PenggunaResponse> updatePengguna(Pengguna pengguna) {
        return mPenggunaRepository.updatePengguna(CrudType.EDIT, -1, pengguna);
    }

    public LiveData<List<Pengguna>> getAllPengguna(){
        return mPenggunaRepository.getAllPengguna();
    }

    public LiveData<PenggunaResponse> savePengguna(Pengguna pengguna){
        return mPenggunaRepository.savePengguna(pengguna);
    }

    public LiveData<PenggunaResponse> deletePengguna(int id){
        return mPenggunaRepository.deletePengguna(id);
    }
}
