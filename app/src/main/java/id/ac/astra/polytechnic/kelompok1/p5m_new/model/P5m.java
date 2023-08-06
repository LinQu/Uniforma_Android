package id.ac.astra.polytechnic.kelompok1.p5m_new.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

public class P5m {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nim")
    @Expose
    private String nim;
    @SerializedName("tgl_transaksi")
    @Expose
    private String tgl_transaksi;

    @SerializedName("kelas")
    @Expose
    private String kelas;

    @SerializedName("total_jam_minus")
    @Expose
    private Integer total_jam_minus;

    @Override
    public String toString() {
        return "P5m{" +
                "id=" + id +
                ", nim='" + nim + '\'' +
                ", tgl_transaksi=" + tgl_transaksi +
                ", kelas='" + kelas + '\'' +
                ", total_jam_minus=" + total_jam_minus +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getTgl_transaksi() {
        return tgl_transaksi;
    }

    public void setTgl_transaksi(String tgl_transaksi) {
        this.tgl_transaksi = tgl_transaksi;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public Integer getTotal_jam_minus() {
        return total_jam_minus;
    }

    public void setTotal_jam_minus(Integer total_jam_minus) {
        this.total_jam_minus = total_jam_minus;
    }
}
