package id.ac.astra.polytechnic.kelompok1.p5m_new.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Pelanggaran;
import id.ac.astra.polytechnic.kelompok1.p5m_new.service.PelanggaranService;

public class PelanggaranDao {
    private static final String TAG = "PelanggaranDao";
    private MutableLiveData<Pelanggaran> pelanggaranMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Pelanggaran>> listPelanggaranMutableLiveData = new MutableLiveData<>();

    public void setListPelanggaranMutableLiveData(List<Pelanggaran> pelanggaranServiceList){
        listPelanggaranMutableLiveData.setValue(pelanggaranServiceList);
    }

    public LiveData<List<Pelanggaran>> getlistPelanggaranMutableLiveData(){
        return listPelanggaranMutableLiveData;
    }
}
