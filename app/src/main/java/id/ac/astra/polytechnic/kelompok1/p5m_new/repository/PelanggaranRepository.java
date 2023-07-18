package id.ac.astra.polytechnic.kelompok1.p5m_new.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import id.ac.astra.polytechnic.kelompok1.p5m_new.api.ApiUtils;
import id.ac.astra.polytechnic.kelompok1.p5m_new.dao.PelanggaranDao;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Pelanggaran;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.LIstPelanggaranResponse;
import id.ac.astra.polytechnic.kelompok1.p5m_new.service.PelanggaranService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PelanggaranRepository {
    private static PelanggaranRepository INSTANCE;
    private static PelanggaranDao mPelanggaranDao;
    private PelanggaranService p5mService;

    private PelanggaranRepository(Context context) {
        p5mService = ApiUtils.getP5mService();
    }

    public static void initialize(Context context) {
        if (INSTANCE == null) {
            mPelanggaranDao = new PelanggaranDao();
            INSTANCE = new PelanggaranRepository(context);

        }
    }

    public static PelanggaranRepository get() {
        return INSTANCE;
    }

    public LiveData<List<Pelanggaran>> getPelangarans() {
        Call<LIstPelanggaranResponse> call = p5mService.getPelanggarans();
        call.enqueue(new Callback<LIstPelanggaranResponse>() {
            @Override
            public void onResponse(Call<LIstPelanggaranResponse> call, Response<LIstPelanggaranResponse> response) {
                LIstPelanggaranResponse lIstPelanggaranResponse = response.body();
                Log.d("PelanggaranRepository","onResponse pelanggaran List View Model " + lIstPelanggaranResponse.getmPelanggaran());
                if (lIstPelanggaranResponse.getStatus() == 200) {
                }
                    mPelanggaranDao.setListPelanggaranMutableLiveData(lIstPelanggaranResponse.getmPelanggaran());
                }

            @Override
            public void onFailure(Call<LIstPelanggaranResponse> call, Throwable t) {
                mPelanggaranDao.setListPelanggaranMutableLiveData(null);
                Log.e("PelanggaranRepository", "onFailure: " + t.getMessage());
            }
        });
        return mPelanggaranDao.getlistPelanggaranMutableLiveData();
    }
}
