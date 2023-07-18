package id.ac.astra.polytechnic.kelompok1.p5m_new.dao;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Karyawan;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.KaryawanResponse;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.LoginResponse;

public class KaryawanDao {
    private static final String TAG = "KaryawanDao";

    private MutableLiveData<LoginResponse> mLoginResponse = new MutableLiveData<>();
    private MutableLiveData<KaryawanResponse> mKaryawanResponse = new MutableLiveData<>();
    private MutableLiveData<Karyawan> mKaryawanLogin = new MutableLiveData<>();
    public LiveData<LoginResponse> getLogin() {
        return mLoginResponse;
    }


    public void setLoginResponse(LoginResponse loginResponse) {
        mLoginResponse.setValue(loginResponse);
    }

    public MutableLiveData<Karyawan> getUserLogin() {
        return mKaryawanLogin;
    }

    public void setUserLogin(Karyawan karyawanLogin) {
        mKaryawanLogin.setValue(karyawanLogin);
        Log.d(TAG, "setUserLogin: " + mKaryawanLogin.getValue());
    }
}
