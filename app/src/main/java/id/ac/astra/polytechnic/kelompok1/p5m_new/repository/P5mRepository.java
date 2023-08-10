package id.ac.astra.polytechnic.kelompok1.p5m_new.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import id.ac.astra.polytechnic.kelompok1.p5m_new.api.ApiUtils;
import id.ac.astra.polytechnic.kelompok1.p5m_new.dao.P5mDao;
import id.ac.astra.polytechnic.kelompok1.p5m_new.dao.PelanggaranDao;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.DetailP5m;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.P5m;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.ListDetailP5mResponse;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.P5mResponse;
import id.ac.astra.polytechnic.kelompok1.p5m_new.service.P5mService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class P5mRepository {
    private static final String TAG = "P5mRepository";

    private static P5mRepository INSTANCE;
    private P5mService p5mService;
    private static P5mDao mP5mDao;

    private P5mRepository(Context context) {
        p5mService = ApiUtils.getP5mService();
    }

    public static void initialize(Context context) {
        if (INSTANCE == null) {
            mP5mDao = new P5mDao();
            INSTANCE = new P5mRepository(context);

        }
    }

    public static P5mRepository get() {
        return INSTANCE;
    }

    public LiveData<List<Object[]>> findPelanggaranOccurrencesByNim(String nim,int year,int month){
         MutableLiveData<List<Object[]>> pelanggaranListMutbaleLiveData = new MutableLiveData<>();
         Call<List<Object[]>> call = p5mService.findPelanggaranOccurrencesByNim(nim,year,month);
         call.enqueue(new Callback<List<Object[]>>() {
             @Override
             public void onResponse(Call<List<Object[]>> call, Response<List<Object[]>> response) {
                pelanggaranListMutbaleLiveData.setValue(response.body());
             }

             @Override
             public void onFailure(Call<List<Object[]>> call, Throwable t) {
                 Log.e(TAG, "onFailure P5M: " + t.getMessage());
             }
         });
         return pelanggaranListMutbaleLiveData;
    }

    public LiveData<List<Object[]>> top3NimAndTotalJamMinus(String kelas){
        MutableLiveData<List<Object[]>> listTopp = new MutableLiveData<>();
        Call<List<Object[]>> call = p5mService.top3NimAndTotalJamMinus(kelas);
        call.enqueue(new Callback<List<Object[]>>() {
            @Override
            public void onResponse(Call<List<Object[]>> call, Response<List<Object[]>> response) {
                List<Object[]> listTop = response.body();
                Log.d(TAG, "Top 3 " + listTop);
                if (listTop != null){
                    listTopp.setValue(listTop);
                }
            }

            @Override
            public void onFailure(Call<List<Object[]>> call, Throwable t) {
                List<Object[]>  listTop = new ArrayList<>();
                listTopp.setValue(listTop);
                Log.e(TAG,"onFailure List View Model " + t.getMessage());
            }
        });
        return listTopp;
    }

    public LiveData<List<DetailP5m>> postP5m(Map<String, List<Integer>> p5mParam, String kelasParam){
        MutableLiveData<List<DetailP5m>> p5mResponseMutableLiveData = new MutableLiveData<>();
        Call<ListDetailP5mResponse> call = p5mService.cobaPostP5m(p5mParam,kelasParam);
        call.enqueue(new Callback<ListDetailP5mResponse>() {
            @Override
            public void onResponse(Call<ListDetailP5mResponse> call, Response<ListDetailP5mResponse> response) {
                ListDetailP5mResponse p5mResponse = response.body();

                if(p5mResponse.getStatus() == 200) {
                    p5mResponseMutableLiveData.setValue(p5mResponse.getmDetailP5m());
                    Log.d(TAG,"OnResponse P5M: "+p5mResponse);
                    Log.d(TAG,"OnResponse P5M Mutable: "+p5mResponseMutableLiveData);

                }
            }

            @Override
            public void onFailure(Call<ListDetailP5mResponse> call, Throwable t) {
                Log.e(TAG, "onFailure  Form P5M: " + t.getMessage());
            }
        });
        return  p5mResponseMutableLiveData;
    }

}
