package id.ac.astra.polytechnic.kelompok1.p5m_new.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Mahasiswa;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Pelanggaran;

public class PelanggaranMahasiswa {
    private List<Pelanggaran> mPelanggaran;

    private Mahasiswa mMahasiswa;

    public PelanggaranMahasiswa(List<Pelanggaran> mPelanggaran, Mahasiswa mMahasiswa) {
        this.mPelanggaran = mPelanggaran;
        this.mMahasiswa = mMahasiswa;
    }

    public List<Pelanggaran> getmPelanggaran() {
        return mPelanggaran;
    }

    public void setmPelanggaran(List<Pelanggaran> mPelanggaran) {
        this.mPelanggaran = mPelanggaran;
    }

    public Mahasiswa getmMahasiswa() {
        return mMahasiswa;
    }

    public void setmMahasiswa(Mahasiswa mMahasiswa) {
        this.mMahasiswa = mMahasiswa;
    }

    @Override
    public String toString() {
        return "PelanggaranMahasiswa{" +
                "mPelanggaran=" + mPelanggaran +
                ", mMahasiswa=" + mMahasiswa +
                '}';
    }
}
