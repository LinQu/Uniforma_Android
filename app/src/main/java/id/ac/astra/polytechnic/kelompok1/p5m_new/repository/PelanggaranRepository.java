package id.ac.astra.polytechnic.kelompok1.p5m_new.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import id.ac.astra.polytechnic.kelompok1.p5m_new.api.ApiUtils;
import id.ac.astra.polytechnic.kelompok1.p5m_new.dao.PelanggaranDao;
import id.ac.astra.polytechnic.kelompok1.p5m_new.helper.CrudType;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Pelanggaran;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.LIstPelanggaranResponse;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.PelanggaranResponse;
import id.ac.astra.polytechnic.kelompok1.p5m_new.service.PelanggaranService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PelanggaranRepository {

    private static final String TAG = "PelanggaranRepository";
    private static PelanggaranRepository INSTANCE;
    private static PelanggaranDao mPelanggaranDao;
    private PelanggaranService p5mService;

    private PelanggaranRepository(Context context) {
        p5mService = ApiUtils.getPelanggaranService();
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
                Log.e("PelanggaranRepository", "onFailure: " + t.getMessage());
            }
        });
        return mPelanggaranDao.getlistPelanggaranMutableLiveData();
    }

    public LiveData<PelanggaranResponse> updatePelanggaran(CrudType crudType, int position,Pelanggaran pelanggaran){
        MutableLiveData<PelanggaranResponse> pelanggaranResponseMutableLiveData = new MutableLiveData<>();
        Call<PelanggaranResponse> call = p5mService.updatePelanggaran(pelanggaran);
        call.enqueue(new Callback<PelanggaranResponse>() {
            @Override
            public void onResponse(Call<PelanggaranResponse> call, Response<PelanggaranResponse> response) {
                PelanggaranResponse pelanggaranResponse = response.body();
                pelanggaranResponseMutableLiveData.setValue(pelanggaranResponse);
                if (pelanggaranResponse.getStatus() == 200){
                   if (crudType == CrudType.ADD){
                       mPelanggaranDao.addToPosition(position,pelanggaranResponse.getmPelanggaran());
                     }else
                   {
                       mPelanggaranDao.updateSchedule(pelanggaranResponse.getmPelanggaran());
                   }
                    Log.d(TAG, "onResponse: Habis Update " + pelanggaranResponse);
                }
            }
            @Override
            public void onFailure(Call<PelanggaranResponse> call, Throwable t) {
                Log.e("PelanggaranRepository ", "onFailure onUpdate: " + t.getMessage());
            }
        });
        return pelanggaranResponseMutableLiveData;
    }

    public LiveData<PelanggaranResponse> savePelanggaran(Pelanggaran pelanggaran){
        MutableLiveData<PelanggaranResponse> pelanggaranResponseMutableLiveData = new MutableLiveData<>();
        Call<PelanggaranResponse> call = p5mService.savePelangggaran(pelanggaran);
        call.enqueue(new Callback<PelanggaranResponse>() {
            @Override
            public void onResponse(Call<PelanggaranResponse> call, Response<PelanggaranResponse> response) {
                PelanggaranResponse pelanggaranResponse = response.body();
                pelanggaranResponseMutableLiveData.setValue(pelanggaranResponse);
                if (pelanggaranResponse.getStatus() == 200){
                    mPelanggaranDao.savePelanggaran(pelanggaranResponse.getmPelanggaran());

                }
            }

            @Override
            public void onFailure(Call<PelanggaranResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
        return pelanggaranResponseMutableLiveData;
    }

    public LiveData<PelanggaranResponse> deletePelanggaran(int id){
        MutableLiveData<PelanggaranResponse> pelanggaranResponseMutableLiveData = new MutableLiveData<>();
        Call<PelanggaranResponse> call = p5mService.deletePelanggaran(id);
        call.enqueue(new Callback<PelanggaranResponse>() {
            @Override
            public void onResponse(Call<PelanggaranResponse> call, Response<PelanggaranResponse> response) {
                PelanggaranResponse pelanggaranResponse = response.body();
                pelanggaranResponseMutableLiveData.setValue(pelanggaranResponse);
                if (pelanggaranResponse.getStatus() == 200){
                   mPelanggaranDao.deletePelanggaran(id);
                }
            }

            @Override
            public void onFailure(Call<PelanggaranResponse> call, Throwable t) {
                Log.e("PelanggaranRepository", "onFailure: " + t.getMessage());
            }
        });
        return pelanggaranResponseMutableLiveData;
    }
}
