package id.ac.astra.polytechnic.kelompok1.p5m_new.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Mahasiswa {
    @SerializedName("nim")
    @Expose
    private String nim;

    @SerializedName("nama")
    @Expose
    private String nama;

    @SerializedName("angkatan")
    @Expose
    private String angkatan;

    @SerializedName("kelas")
    @Expose
    private String kelas;

    @SerializedName("prodi")
    @Expose
    private String prodi;

    @SerializedName("dosen_wali")
    @Expose
    private String dosen_wali;

    @SerializedName("mhs_email")
    @Expose
    private String mhs_email;

    @SerializedName("dul_pas_foto")
    @Expose
    private String dul_pas_foto;

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAngkatan() {
        return angkatan;
    }

    public void setAngkatan(String angkatan) {
        this.angkatan = angkatan;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getProdi() {
        return prodi;
    }

    public void setProdi(String prodi) {
        this.prodi = prodi;
    }

    public String getDosen_wali() {
        return dosen_wali;
    }

    public void setDosen_wali(String dosen_wali) {
        this.dosen_wali = dosen_wali;
    }

    public String getMhs_email() {
        return mhs_email;
    }

    public void setMhs_email(String mhs_email) {
        this.mhs_email = mhs_email;
    }

    public String getDul_pas_foto() {
        return dul_pas_foto;
    }

    public void setDul_pas_foto(String dul_pas_foto) {
        this.dul_pas_foto = dul_pas_foto;
    }
}
