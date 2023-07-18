package id.ac.astra.polytechnic.kelompok1.p5m_new.model.response;

import com.google.gson.annotations.SerializedName;

public class KaryawanResponse {
    @SerializedName("id")
    private String id;

    @SerializedName("username")
    private String username;

    @SerializedName("nama")
    private String nama;

    @SerializedName("jabatan")
    private String jabatan;

    @SerializedName("struktur")
    private String struktur;

    @SerializedName("parent")
    private String parent;


    @Override
    public String toString() {
        return "KaryawanResponse    {" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", nama='" + nama + '\'' +
                ", jabatan='" + jabatan + '\'' +
                ", struktur='" + struktur + '\'' +
                ", parent='" + parent + '\'' +
                '}';
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
