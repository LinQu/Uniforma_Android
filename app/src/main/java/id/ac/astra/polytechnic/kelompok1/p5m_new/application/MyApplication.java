package id.ac.astra.polytechnic.kelompok1.p5m_new.application;

import android.app.Application;
import android.util.Log;

import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Kelas;
import id.ac.astra.polytechnic.kelompok1.p5m_new.repository.AbsenRepository;
import id.ac.astra.polytechnic.kelompok1.p5m_new.repository.KaryawanRepository;
import id.ac.astra.polytechnic.kelompok1.p5m_new.repository.KelasRepository;
import id.ac.astra.polytechnic.kelompok1.p5m_new.repository.MahasiswaRepository;
import id.ac.astra.polytechnic.kelompok1.p5m_new.repository.PelanggaranRepository;
import id.ac.astra.polytechnic.kelompok1.p5m_new.repository.PenggunaRepository;

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "MyRepository.onCreate() called");
        KaryawanRepository.initialize(this);
        PenggunaRepository.initialize(this);
        PelanggaranRepository.initialize(this);
        KelasRepository.initialize(this);
        MahasiswaRepository.initialize(this);
        AbsenRepository.initialize(this);

    }
}
