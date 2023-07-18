package id.ac.astra.polytechnic.kelompok1.p5m_new.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Karyawan;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.LoginResponse;
import id.ac.astra.polytechnic.kelompok1.p5m_new.repository.KaryawanRepository;

public class KaryawanListViewModel extends ViewModel {
    private static final String TAG = "KaryawanListViewModel";
    private KaryawanRepository mKaryawanRepository;

    public KaryawanListViewModel(){
        Log.d(TAG, "UserListViewModel constructor called");
        mKaryawanRepository = KaryawanRepository.get();
    }

    public LiveData<LoginResponse> getLoginKaryawan(String username){
       return mKaryawanRepository.getLogin(username);
    }
}
