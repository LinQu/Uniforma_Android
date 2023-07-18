package id.ac.astra.polytechnic.kelompok1.p5m_new.dao;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Kelas;

public class KelasDao {
    private static final String TAG = "KelasDao";
    private MutableLiveData<Kelas> kelasMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Kelas>> listMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<Kelas> getKelasMutableLiveData() {
        return kelasMutableLiveData;
    }

    public void setKelasMutableLiveData(Kelas kelasMutableLiveData) {
       this.kelasMutableLiveData.setValue(kelasMutableLiveData);
    }

    public MutableLiveData<List<Kelas>> getListMutableLiveData() {
        return listMutableLiveData;
    }

    public void setListMutableLiveData(List<Kelas> listMutableLiveData) {
        this.listMutableLiveData.setValue(listMutableLiveData);
    }
}
