package id.ac.astra.polytechnic.kelompok1.p5m_new.dao;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Pelanggaran;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Pengguna;
import id.ac.astra.polytechnic.kelompok1.p5m_new.service.PelanggaranService;

public class PelanggaranDao {
    private static final String TAG = "PelanggaranDao";
    private MutableLiveData<Pelanggaran> pelanggaranMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Pelanggaran>> listPelanggaranMutableLiveData = new MutableLiveData<>();

    public void setListPelanggaranMutableLiveData(List<Pelanggaran> pelanggaranServiceList){
        listPelanggaranMutableLiveData.setValue(pelanggaranServiceList);
    }

    public void savePelanggaran(Pelanggaran pelanggaran){
        List<Pelanggaran> pelanggaranList = listPelanggaranMutableLiveData.getValue();
        pelanggaranList.add(pelanggaran);
        listPelanggaranMutableLiveData.setValue(pelanggaranList);
    }

    public LiveData<List<Pelanggaran>> getlistPelanggaranMutableLiveData(){
        return listPelanggaranMutableLiveData;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private Pelanggaran getPelanggaranById(int pelanggaranID, List<Pelanggaran> scheduleList) {
        return scheduleList
                .stream()
                .filter(schedule -> schedule.getId() == pelanggaranID)
                .findFirst()
                .orElse(null);
    }

    public void updateSchedule(Pelanggaran schedule) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            List<Pelanggaran> scheduleList = listPelanggaranMutableLiveData.getValue();
            if(scheduleList != null) {
                Pelanggaran selectedSchedule = getPelanggaranById(schedule.getId(), scheduleList);

                if(selectedSchedule != null) {
                    int index = scheduleList.indexOf(selectedSchedule);
                    scheduleList.set(index, schedule);
                    listPelanggaranMutableLiveData.setValue(scheduleList);
                }
            }
        }
    }

    public void addToPosition(int position, Pelanggaran pelanggaran) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            List<Pelanggaran> pelanggaranList = listPelanggaranMutableLiveData.getValue();
            if(pelanggaranList != null) {
                pelanggaranList.add(position, pelanggaran);
                listPelanggaranMutableLiveData.setValue(pelanggaranList);
            }
        }
    }


    public void deletePelanggaran(int pelanggaraID){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            List<Pelanggaran> pelanggaranList = listPelanggaranMutableLiveData.getValue();
            if(pelanggaranList != null) {
                Pelanggaran selectedPelanggaran = pelanggaranList
                        .stream().filter(pelanggaran -> pelanggaran.getId() == pelanggaraID)
                        .findAny().orElse(null);

                if(selectedPelanggaran != null) {
                    pelanggaranList.remove(selectedPelanggaran);
                    listPelanggaranMutableLiveData.setValue(pelanggaranList);
                }
            }
        }
    }
}
