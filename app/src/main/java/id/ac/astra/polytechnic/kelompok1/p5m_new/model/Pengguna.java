package id.ac.astra.polytechnic.kelompok1.p5m_new.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pengguna {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("nama_pengguna")
    @Expose
    private String nama;

    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("kelas")
    @Expose
    private String kelas;

    @SerializedName("status")
    @Expose
    private int status;

    public Pengguna(){

    }

    public Pengguna(Integer id,String nama, String role,String kelas, int status){
        this.id = id;
        this.kelas = kelas;
        this.nama = nama;
        this.role = role;
        this.status = status;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @NonNull
    @Override
    public String toString() {
        return "DataItem{"+
                "id = '" + id + '\''+
                ",nama_pengguna = '" +nama + '\''+
                ",role = '"+ role +'\''+
                ",kelas = '" +kelas + '\''+
                ",status = '"+status + '\''+
                "}";
     }
}
