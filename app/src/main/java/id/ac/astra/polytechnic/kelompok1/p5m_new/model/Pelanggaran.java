package id.ac.astra.polytechnic.kelompok1.p5m_new.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pelanggaran {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("nama")
    @Expose
    private String nama;

    @SerializedName("jam_minus")
    @Expose
    private String jamMinus;

    @SerializedName("status")
    @Expose
    private Integer status;
    private String nim;

    private boolean isSelected;

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Pelanggaran(Integer id, String nama, String jamMinus, boolean check) {
        this.id = id;
        this.nama = nama;
        this.jamMinus = jamMinus;
        this.isSelected = check;
    }
    public Pelanggaran() {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJamMinus() {
        return jamMinus;
    }

    public void setJamMinus(String jamMinus) {
        this.jamMinus = jamMinus;
    }

    @Override
    public String toString() {
        return "Pelanggaran{" +
                "id=" + id +
                ", nama='" + nama + '\'' +
                ", jamMinus='" + jamMinus + '\'' +
                ", status=" + status +
                ", nim='" + nim + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}
