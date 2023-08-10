package id.ac.astra.polytechnic.kelompok1.p5m_new.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.security.PublicKey;
import java.util.List;

import id.ac.astra.polytechnic.kelompok1.p5m_new.api.ApiUtils;
import id.ac.astra.polytechnic.kelompok1.p5m_new.dao.PenggunaDAO;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.KehadiranPerBulanDTO;
import id.ac.astra.polytechnic.kelompok1.p5m_new.service.AbsenService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AbsenRepository {
    private static final String TAG = "AbsenRepository";

    private static AbsenRepository INSTANCE;
    private Context context;
    private AbsenService mAbsenService;
    private MutableLiveData<List<Object[]>> listAbsen = new MutableLiveData<>();
    private AbsenRepository(Context context){
        mAbsenService = ApiUtils.getAbsenService();
        this.context = context;
    }

    public static void initialize(Context context){
        if (INSTANCE == null){
            INSTANCE = new AbsenRepository(context);
        }
    }

    public static AbsenRepository getInstance(){
        return INSTANCE;
    }
    public void setListAbsen(List<Object[]> listAbsen){
        this.listAbsen.setValue(listAbsen);
    }

    public LiveData<List<Object[]>> getListAbsen(){
        return listAbsen;
    }

    public LiveData<List<Object[]>> getHistoryAbsenByNim(String nim){
        Call<List<Object[]>> call = mAbsenService.getHistoryAbsenByNim(nim);
        call.enqueue(new Callback<List<Object[]>>() {
            @Override
            public void onResponse(Call<List<Object[]>> call, Response<List<Object[]>> response) {
                List<Object[]> listAbsen = response.body();
                Log.d(TAG,"onResponse Absen List View Model " + listAbsen);
                if(listAbsen != null){
                    setListAbsen(listAbsen);
                }
            }

            @Override
            public void onFailure(Call<List<Object[]>> call, Throwable t) {
                Log.e(TAG,"onFailure Absen List View Model " + t.getMessage());
            }
        });
        return getListAbsen();
    }

    public LiveData<List<Object[]>> getAbsenByNimAndMonth(String nim, int month, int year){
        Call<List<Object[]>> call = mAbsenService.getAbsenByNimAndMonth(year, month,nim);
        Log.d(TAG,"getAbsenByNimAndMonth: " + year + " " + month + " " + nim);
        call.enqueue(new Callback<List<Object[]>>() {
            @Override
            public void onResponse(Call<List<Object[]>> call, Response<List<Object[]>> response) {
                List<Object[]> listAbsen = response.body();
                Log.d(TAG,"onResponse Absen List View Model 75" + listAbsen);
                if(listAbsen != null){
                    setListAbsen(listAbsen);
                }
            }

            @Override
            public void onFailure(Call<List<Object[]>> call, Throwable t) {
                Log.e(TAG,"onFailure Absen List View Model " + t.getMessage());
            }
        });
        return getListAbsen();
    }

    public LiveData<List<KehadiranPerBulanDTO>> getPersentaseKehadiran(String NIM){
        final MutableLiveData<List<KehadiranPerBulanDTO>> listKehadiran = new MutableLiveData<>();
        Call<List<KehadiranPerBulanDTO>> call = mAbsenService.getPersentaseKehadiran(NIM);
        call.enqueue(new Callback<List<KehadiranPerBulanDTO>>() {
            @Override
            public void onResponse(Call<List<KehadiranPerBulanDTO>> call, Response<List<KehadiranPerBulanDTO>> response) {
                List<KehadiranPerBulanDTO> listKehadiranPerBulanDTO = response.body();
                Log.d(TAG,"onResponse Absen List View Model " + listKehadiranPerBulanDTO);
                if(listKehadiranPerBulanDTO != null){
                    listKehadiran.setValue(listKehadiranPerBulanDTO);
                }
            }

            @Override
            public void onFailure(Call<List<KehadiranPerBulanDTO>> call, Throwable t) {
                Log.e(TAG,"onFailure Absen List View Model " + t.getMessage());
            }
        });
        return listKehadiran;
    }

    public LiveData<KehadiranPerBulanDTO> getPersentaseKeahadiraByMonth(String NIM, int Year, int Month){
        final MutableLiveData<KehadiranPerBulanDTO> kehadiranPerBulanDTO = new MutableLiveData<>();
        Call<KehadiranPerBulanDTO> call = mAbsenService.getPersentaseKehadiranByBulan(NIM,Year,Month);
        call.enqueue(new Callback<KehadiranPerBulanDTO>() {
            @Override
            public void onResponse(Call<KehadiranPerBulanDTO> call, Response<KehadiranPerBulanDTO> response) {
                KehadiranPerBulanDTO kehadiranPerBulanDTO1 = response.body();
                Log.d(TAG,"onResponse Absen List View Model " + kehadiranPerBulanDTO1.toString());
                if(kehadiranPerBulanDTO1 != null){
                    kehadiranPerBulanDTO.setValue(kehadiranPerBulanDTO1);
                }
            }

            @Override
            public void onFailure(Call<KehadiranPerBulanDTO> call, Throwable t) {
                Log.e(TAG,"onFailure Absen List View Model Persen" + t.getMessage());
            }
        });
        return kehadiranPerBulanDTO;
    }

    public LiveData<List<Object[]>> getHistoryListMonthByNim(String NIM){
        final MutableLiveData<List<Object[]>> listHistoryMonth = new MutableLiveData<>();
        Call<List<Object[]>> call = mAbsenService.getHistoryListMonthByNim(NIM);
        call.enqueue(new Callback<List<Object[]>>() {
            @Override
            public void onResponse(Call<List<Object[]>> call, Response<List<Object[]>> response) {
                List<Object[]> listHistoryMonth1 = response.body();
                Log.d(TAG,"onResponse Absen List View Model List Bulan " + listHistoryMonth1);
                if(listHistoryMonth1 != null){
                    listHistoryMonth.setValue(listHistoryMonth1);
                }
            }

            @Override
            public void onFailure(Call<List<Object[]>> call, Throwable t) {
                Log.e(TAG,"onFailure Absen List View Model Bulan" + t.getMessage());
            }
        });
        return listHistoryMonth;
    }

    public LiveData<Object[]> calculateAbsen(String NIM, int Year, int Month){
        final MutableLiveData<Object[]> listAbsen = new MutableLiveData<>();
        Call<Object[]> call = mAbsenService.calculateAbsen(NIM,Year,Month);
        call.enqueue(new Callback<Object[]>() {
            @Override
            public void onResponse(Call<Object[]> call, Response<Object[]> response) {
                Object[] listAbsen1 = response.body();
                Log.d(TAG,"onResponse Absen List View Model List Bulan " + listAbsen1);
                if(listAbsen1 != null){
                    listAbsen.setValue(listAbsen1);
                }
            }

            @Override
            public void onFailure(Call<Object[]> call, Throwable t) {
                Log.e(TAG,"onFailure Absen List View Model Bulan" + t.getMessage());
            }
        });
        return listAbsen;
    }
}
