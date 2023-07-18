package id.ac.astra.polytechnic.kelompok1.p5m_new.dao;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Pengguna;

public class PenggunaDAO {
    private static final String TAG = "PenggunaDao";
    private MutableLiveData<Pengguna> penggunaUser = new MutableLiveData<>();
    private MutableLiveData<List<Pengguna>> listpenggunaUser = new MutableLiveData<>();

    public  void setListpenggunaUser(List<Pengguna> penggunas){
        listpenggunaUser.setValue(penggunas);
    }

    public LiveData<List<Pengguna>> getListPengguna(){
        return listpenggunaUser;
    }

    public void setPenggunaUser(Pengguna pengguna) {
        penggunaUser.setValue(pengguna);
    }

    public LiveData<Pengguna> getPenggunaUser() {
        return penggunaUser;
    }

    public void savePengguna(Pengguna pengguna){
        List<Pengguna> penggunaList = listpenggunaUser.getValue();
        penggunaList.add(pengguna);
        listpenggunaUser.setValue(penggunaList);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private Pengguna getPenggunaById(int penggunaID, List<Pengguna> scheduleList) {
        return scheduleList
                .stream()
                .filter(schedule -> schedule.getId() == penggunaID)
                .findFirst()
                .orElse(null);
    }


    public void addToPosition(int position, Pengguna pengguna) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            List<Pengguna> penggunaList = listpenggunaUser.getValue();
            if(penggunaList != null) {
                penggunaList.add(position, pengguna);
                listpenggunaUser.setValue(penggunaList);
            }
        }
    }

    public void updateSchedule(Pengguna schedule) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            List<Pengguna> scheduleList = listpenggunaUser.getValue();
            if(scheduleList != null) {
                Pengguna selectedSchedule = getPenggunaById(schedule.getId(), scheduleList);

                if(selectedSchedule != null) {
                    int index = scheduleList.indexOf(selectedSchedule);
                    scheduleList.set(index, schedule);
                    listpenggunaUser.setValue(scheduleList);
                }
            }
        }
    }

    public void deletePengguna(int penggunaId){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            List<Pengguna> penggunaList = listpenggunaUser.getValue();
            if(penggunaList != null) {
                Pengguna selectedPengguna = penggunaList
                        .stream().filter(pengguna -> pengguna.getId() == penggunaId)
                        .findAny().orElse(null);

                if(selectedPengguna != null) {
                    penggunaList.remove(selectedPengguna);
                    listpenggunaUser.setValue(penggunaList);
                }
            }
        }
    }
}
