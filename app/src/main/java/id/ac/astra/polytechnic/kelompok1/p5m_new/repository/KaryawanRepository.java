package id.ac.astra.polytechnic.kelompok1.p5m_new.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import id.ac.astra.polytechnic.kelompok1.p5m_new.api.ApiUtils;
import id.ac.astra.polytechnic.kelompok1.p5m_new.dao.KaryawanDao;
import id.ac.astra.polytechnic.kelompok1.p5m_new.helper.NetworkStateLiveData;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Karyawan;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.LoginResponse;
import id.ac.astra.polytechnic.kelompok1.p5m_new.service.KaryawanService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KaryawanRepository {
    private static final String TAG = "KaryawanRepository";
    private static KaryawanRepository INSTANCE;
    private KaryawanService mKaryawanService;
    private static KaryawanDao mKaryawanDao;
    private NetworkStateLiveData networkStateLiveData;
    private Context context;

    public KaryawanRepository(Context context){
        mKaryawanService = ApiUtils.getKaryawanService();

        this.context = context;
    }

    public static void initialize(Context context){
        if (INSTANCE == null){
            mKaryawanDao = new KaryawanDao();
            INSTANCE = new KaryawanRepository(context);
        }
    }

    public static KaryawanRepository get(){
        return INSTANCE;
    }

    public LiveData<LoginResponse> getLogin(String username){
        MutableLiveData<LoginResponse> loginResponseMutableLiveData = new MutableLiveData<>();

        Call<LoginResponse> call = mKaryawanService.loginKaryawan(username);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                Log.d(TAG, "onResponse: " + response.body());
                loginResponseMutableLiveData.setValue(loginResponse);
                if (loginResponse.getmKaryawan() != null){
                    Log.d(TAG, "onResponse: " + loginResponse.getmKaryawan().getNama());
                    mKaryawanDao.setUserLogin(loginResponse.getmKaryawan());
                }else{
                    mKaryawanDao.setUserLogin(loginResponse.getmKaryawan());
                    Log.d(TAG, "onResponse: " + loginResponse.getmKaryawan());
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loginResponseMutableLiveData.setValue(null);
                Log.e(TAG, "Login Gagal : " + t.getMessage()  );
                //networkStateLiveData.onPostValue(false);
            }
        });
        return loginResponseMutableLiveData;
    }


}
