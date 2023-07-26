package id.ac.astra.polytechnic.kelompok1.p5m_new.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KehadiranPerBulanDTO {
    @SerializedName("bulan")
    @Expose
    private String bulan;

    @SerializedName("persentaseKehadiran")
    @Expose
    private double persentaseKehadiran;

    public KehadiranPerBulanDTO() {
    }

    public KehadiranPerBulanDTO(String bulan, int persentaseKehadiran) {
        this.bulan = bulan;
        this.persentaseKehadiran = persentaseKehadiran;
    }

    public String getBulan() {
        return bulan;
    }

    public void setBulan(String bulan) {
        this.bulan = bulan;
    }

    public double getPersentaseKehadiran() {
        return persentaseKehadiran;
    }

    public void setPersentaseKehadiran(double persentaseKehadiran) {
        this.persentaseKehadiran = persentaseKehadiran;
    }

    @Override
    public String toString() {
        return "KehadiranPerBulanDTO{" +
                "bulan='" + bulan + '\'' +
                ", persentaseKehadiran=" + persentaseKehadiran +
                '}';
    }
}
