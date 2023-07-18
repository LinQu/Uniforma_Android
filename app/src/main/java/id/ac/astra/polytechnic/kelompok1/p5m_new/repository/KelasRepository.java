package id.ac.astra.polytechnic.kelompok1.p5m_new.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import id.ac.astra.polytechnic.kelompok1.p5m_new.api.ApiUtils;
import id.ac.astra.polytechnic.kelompok1.p5m_new.dao.KaryawanDao;
import id.ac.astra.polytechnic.kelompok1.p5m_new.dao.KelasDao;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Kelas;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Pengguna;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.ListKelasResponse;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.ListPenggunaResponse;
import id.ac.astra.polytechnic.kelompok1.p5m_new.service.KelasService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelasRepository {
    private static final String TAG = "KelasRepository";
    private static KelasRepository INSTANCE;
    private KelasService mKelasService;
    private static KelasDao mKelasDao;
    private Context context;

    public KelasRepository(Context context){
        mKelasService = ApiUtils.getKelasService();
        this.context = context;
    }

    public static void initialize(Context context){
        if (INSTANCE == null){
            mKelasDao = new KelasDao();
            INSTANCE = new KelasRepository(context);
        }
    }

    public static KelasRepository get(){
        return INSTANCE;
    }

    public LiveData<List<Kelas>> getAllKelas(){
        Call<ListKelasResponse> call = mKelasService.getKelas();
        call.enqueue(new Callback<ListKelasResponse>() {
            @Override
            public void onResponse(Call<ListKelasResponse> call, Response<ListKelasResponse> response) {
                ListKelasResponse listKelasResponse = response.body();
                Log.d(TAG,"OnResponse Kelas : "+listKelasResponse.getmKelas());
                if (listKelasResponse.getStatus() == 200){
                    mKelasDao.setListMutableLiveData(listKelasResponse.getmKelas());
                }
            }

            @Override
            public void onFailure(Call<ListKelasResponse> call, Throwable t) {
                mKelasDao.setListMutableLiveData(null);
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
        return mKelasDao.getListMutableLiveData();
    }
}
