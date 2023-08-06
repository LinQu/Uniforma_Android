package id.ac.astra.polytechnic.kelompok1.p5m_new.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

public class DetailP5m {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("id_p5m")
    @Expose
    private P5m id_p5m;
    @SerializedName("id_pelanggaran")
    private Pelanggaran id_pelanggaran;

    @Override
    public String toString() {
        return "DetailP5m{" +
                "id=" + id +
                ", id_p5m=" + id_p5m +
                ", id_pelanggaran=" + id_pelanggaran +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public P5m getId_p5m() {
        return id_p5m;
    }

    public void setId_p5m(P5m id_p5m) {
        this.id_p5m = id_p5m;
    }

    public Pelanggaran getId_pelanggaran() {
        return id_pelanggaran;
    }

    public void setId_pelanggaran(Pelanggaran id_pelanggaran) {
        this.id_pelanggaran = id_pelanggaran;
    }
}
