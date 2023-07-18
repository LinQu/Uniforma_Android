package id.ac.astra.polytechnic.kelompok1.p5m_new.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import id.ac.astra.polytechnic.kelompok1.p5m_new.api.ApiUtils;
import id.ac.astra.polytechnic.kelompok1.p5m_new.dao.KaryawanDao;
import id.ac.astra.polytechnic.kelompok1.p5m_new.dao.MahasiswaDao;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Mahasiswa;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.ListMahasiswaResponse;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.LoginMahasiswaResponse;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.LoginResponse;
import id.ac.astra.polytechnic.kelompok1.p5m_new.service.MahasiswaService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MahasiswaRepository {
    private static final  String TAG = "MahasiswaRepository";
    private static MahasiswaRepository INSTANCE;
    private MahasiswaService mMahasiswaService;
    private static MahasiswaDao mMahasiswaDao;
    private Context context;

    public MahasiswaRepository(Context context){
        mMahasiswaService = ApiUtils.getMahasiswaService();
        this.context = context;
    }

    public static void initialize(Context context){
        if (INSTANCE == null){
            mMahasiswaDao = new MahasiswaDao();
            INSTANCE = new MahasiswaRepository(context);
        }
    }

    public static MahasiswaRepository get(){
        return INSTANCE;
    }

    public LiveData<List<Mahasiswa>> getMahasiswaByKelas(String kelas){
        Call<ListMahasiswaResponse> call = mMahasiswaService.getMahasiswaByKelas(kelas);
        call.enqueue(new Callback<ListMahasiswaResponse>() {
            @Override
            public void onResponse(Call<ListMahasiswaResponse> call, Response<ListMahasiswaResponse> response) {
                ListMahasiswaResponse listMahasiswaResponse = response.body();
                Log.d(TAG,"On response : "+ response.body());
                if(listMahasiswaResponse.getStatus()==200){
                    mMahasiswaDao.setmListMahasiswa(listMahasiswaResponse.getmMahasiswa());
                }
            }

            @Override
            public void onFailure(Call<ListMahasiswaResponse> call, Throwable t) {
                mMahasiswaDao.setmListMahasiswa(null);
                Log.e(TAG, "AMbil List MHS Gagal : " + t.getMessage()  );
            }
        });
        return mMahasiswaDao.getmListMahasiswa();
    }

    public LiveData<LoginMahasiswaResponse> getLoginMhs(String nim){
        MutableLiveData<LoginMahasiswaResponse> loginResponseMutableLiveData = new MutableLiveData<>();

        Call<LoginMahasiswaResponse> call = mMahasiswaService.loginMahasiswa(nim);
        call.enqueue(new Callback<LoginMahasiswaResponse>() {
            @Override
            public void onResponse(Call<LoginMahasiswaResponse> call, Response<LoginMahasiswaResponse> response) {
                LoginMahasiswaResponse loginResponse = response.body();
                loginResponseMutableLiveData.setValue(loginResponse);
                if (loginResponse.getmMahasiswa() != null){
                    mMahasiswaDao.setUserLogin(loginResponse.getmMahasiswa());

                }
            }

            @Override
            public void onFailure(Call<LoginMahasiswaResponse> call, Throwable t) {
                loginResponseMutableLiveData.setValue(null);
                Log.e(TAG, "Login Gagal : " + t.getMessage()  );
            }
        });
        return loginResponseMutableLiveData;
    }
}
