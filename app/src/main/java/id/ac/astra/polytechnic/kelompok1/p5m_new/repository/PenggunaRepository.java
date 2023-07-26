package id.ac.astra.polytechnic.kelompok1.p5m_new.repository;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import id.ac.astra.polytechnic.kelompok1.p5m_new.api.ApiUtils;
import id.ac.astra.polytechnic.kelompok1.p5m_new.dao.PenggunaDAO;
import id.ac.astra.polytechnic.kelompok1.p5m_new.helper.CrudType;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Pengguna;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.ListPenggunaResponse;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.PenggunaResponse;
import id.ac.astra.polytechnic.kelompok1.p5m_new.service.PenggunaService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PenggunaRepository {
    private static final String TAG = "PenggunaRepository";
    private static PenggunaRepository INSTANCE;
    private static PenggunaDAO penggunaDAO;

    private Context context;

    private PenggunaService penggunaService;
    private PenggunaRepository(Context context){
        penggunaService = ApiUtils.getPenggunaService();
        this.context = context;
    }

    public static void initialize(Context context){
        if (INSTANCE == null){
            penggunaDAO = new PenggunaDAO();
            INSTANCE = new PenggunaRepository(context);
        }
    }

    public static PenggunaRepository getInstance(){
        return INSTANCE;
    }



    public LiveData<List<Pengguna>> getAllPengguna(){
        Call<ListPenggunaResponse> call = penggunaService.getPengguna();
        call.enqueue(new Callback<ListPenggunaResponse>() {
            @Override
            public void onResponse(Call<ListPenggunaResponse> call, Response<ListPenggunaResponse> response) {
                ListPenggunaResponse listPenggunaResponse = response.body();
                Log.d(TAG,"onResponse Pengguna List View Model " + listPenggunaResponse);

                if(listPenggunaResponse.getStatus() == 200){
                    penggunaDAO.setListpenggunaUser(listPenggunaResponse.getmPengguna());
                }
            }
            @Override
            public void onFailure(Call<ListPenggunaResponse> call, Throwable t) {
                penggunaDAO.setListpenggunaUser(null);
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
        return penggunaDAO.getListPengguna();
    }


    public LiveData<PenggunaResponse> deletePengguna(int id){
        MutableLiveData<PenggunaResponse> penggunaResponseMutableLiveData = new MutableLiveData<>();
        Call<PenggunaResponse> call = penggunaService.deletePengguna(id);
        call.enqueue(new Callback<PenggunaResponse>() {
            @Override
            public void onResponse(Call<PenggunaResponse> call, Response<PenggunaResponse> response) {
                PenggunaResponse penggunaResponse = response.body();
                penggunaResponseMutableLiveData.setValue(penggunaResponse);
                if (penggunaResponse.getStatus() == 200){
                    penggunaDAO.deletePengguna(id);
                }
            }

            @Override
            public void onFailure(Call<PenggunaResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
        return penggunaResponseMutableLiveData;
    }


    public LiveData<PenggunaResponse> savePengguna(Pengguna pengguna){
        MutableLiveData<PenggunaResponse> penggunaResponseMutableLiveData = new MutableLiveData<>();

        Log.i(TAG,"Save Pengguna : " + pengguna);
        Call<PenggunaResponse> call = penggunaService.savePengguna(pengguna);
        call.enqueue(new Callback<PenggunaResponse>() {
            @Override
            public void onResponse(Call<PenggunaResponse> call, Response<PenggunaResponse> response) {
                PenggunaResponse penggunaResponse = response.body();
                penggunaResponseMutableLiveData.setValue(penggunaResponse);
                if (penggunaResponse.getStatus() == 200){
                    penggunaDAO.savePengguna(penggunaResponse.getmPengguna());
                }
            }

            @Override
            public void onFailure(Call<PenggunaResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
        return penggunaResponseMutableLiveData;
    }

    public LiveData<PenggunaResponse> updatePengguna(CrudType crudType, int position, Pengguna pengguna){
        MutableLiveData<PenggunaResponse> penggunaResponseMutableLiveData = new MutableLiveData<>();
        Log.i(TAG,"update Pengguna : " + pengguna);
        Call<PenggunaResponse> call = penggunaService.updatePengguna(pengguna);
        call.enqueue(new Callback<PenggunaResponse>() {
            @Override
            public void onResponse(Call<PenggunaResponse> call, Response<PenggunaResponse> response) {
                PenggunaResponse penggunaResponse = response.body();
                penggunaResponseMutableLiveData.setValue(penggunaResponse);
                if (penggunaResponse.getStatus() == 200){
                    if (crudType == CrudType.ADD) {
                        penggunaDAO.addToPosition(position,penggunaResponse.getmPengguna());
                    }else{
                        penggunaDAO.updateSchedule(penggunaResponse.getmPengguna());
                    }

                    Log.d(TAG, "onResponse: " + penggunaResponse);
                }
            }

            @Override
            public void onFailure(Call<PenggunaResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
        return penggunaResponseMutableLiveData;
    }

    public LiveData<PenggunaResponse> getPenggunaByName(String nama){
        Log.d(TAG, "getPenggunaByName: " + nama);
        MutableLiveData<PenggunaResponse> penggunaData = new MutableLiveData<>();
        Call<PenggunaResponse> call = penggunaService.getPenggunaByNama(nama);
        call.enqueue(new Callback<PenggunaResponse>() {
            @Override
            public void onResponse(Call<PenggunaResponse> call, Response<PenggunaResponse> response) {
                PenggunaResponse penggunaResponse = response.body();
                Log.d(TAG, "onResponse 56: " + response.body().getmPengguna().getNama());
                penggunaData.setValue(penggunaResponse);
                if (penggunaResponse.getmPengguna() != null) {
                    penggunaDAO.setPenggunaUser(penggunaResponse.getmPengguna());
                }

            }

            @Override
            public void onFailure(Call<PenggunaResponse> call, Throwable t) {
                penggunaData.setValue(null);
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
        return penggunaData;
    }
}
