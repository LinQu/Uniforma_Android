package id.ac.astra.polytechnic.kelompok1.p5m_new.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Kelas {
    @SerializedName("kel_id")
    @Expose
    private String kel_id;

    @SerializedName("kel_tahun_ajaran")
    @Expose
    private String kel_tahun_ajaran;

    @SerializedName("kel_tingkat")
    @Expose
    private String kel_tingkat;

    public String getId() {
        return kel_id;
    }

    public void setId(String id) {
        this.kel_id = id;
    }

    public String getKel_tahun_ajaran() {
        return kel_tahun_ajaran;
    }

    public void setKel_tahun_ajaran(String kel_tahun_ajaran) {
        this.kel_tahun_ajaran = kel_tahun_ajaran;
    }

    public String getKel_tingkat() {
        return kel_tingkat;
    }

    public void setKel_tingkat(String kel_tingkat) {
        this.kel_tingkat = kel_tingkat;
    }

    @Override
    public String toString() {
        return "Kelas{" +
                "id='" + kel_id + '\'' +
                ", kel_tahun_ajaran='" + kel_tahun_ajaran + '\'' +
                ", kel_tingkat='" + kel_tingkat + '\'' +
                '}';
    }
}
