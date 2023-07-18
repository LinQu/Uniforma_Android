package id.ac.astra.polytechnic.kelompok1.p5m_new.dao;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Karyawan;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Mahasiswa;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Pengguna;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.LoginMahasiswaResponse;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.LoginResponse;

public class MahasiswaDao {
    private static final String TAG = "MahasiswaDao";
    private MutableLiveData<LoginMahasiswaResponse> mLoginResponse = new MutableLiveData<>();
    private MutableLiveData<Mahasiswa> mMahsisaLogin = new MutableLiveData<>();
    private MutableLiveData<List<Mahasiswa>> mListMahasiswa = new MutableLiveData<>();
    public LiveData<LoginMahasiswaResponse> getLogin() {
        return mLoginResponse;
    }

    public void setLoginResponse(LoginMahasiswaResponse loginResponse) {
        mLoginResponse.setValue(loginResponse);
    }

    public MutableLiveData<Mahasiswa> getUserLogin() {
        return mMahsisaLogin;
    }

    public void setUserLogin(Mahasiswa mahasiswaLogins) {
        mMahsisaLogin.setValue(mahasiswaLogins);
        Log.d(TAG, "setUserLogin: " + mMahsisaLogin.getValue());
    }

    public LiveData<List<Mahasiswa>> getmListMahasiswa(){
        return mListMahasiswa;
    }

    public void setmListMahasiswa(List<Mahasiswa> mahasiswa){
        mListMahasiswa.setValue(mahasiswa);
    }
}
