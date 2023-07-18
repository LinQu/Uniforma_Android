package id.ac.astra.polytechnic.kelompok1.p5m_new.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Karyawan {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("username")
    @Expose
    private String username;


    @SerializedName("nama")
    @Expose
    private String nama;

    @SerializedName("jabatan")
    @Expose
    private String jabatan;

    @SerializedName("struktur")
    @Expose
    private String struktur;

    @SerializedName("parent")
    @Expose
    private String parent;


    public Karyawan(String id, String username, String nama, String jabatan, String struktur, String parent) {
        this.id = id;
        this.username = username;
        this.nama = nama;
        this.jabatan = jabatan;
        this.struktur = struktur;
        this.parent = parent;
    }

    public Karyawan() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getStruktur() {
        return struktur;
    }

    public void setStruktur(String struktur) {
        this.struktur = struktur;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
