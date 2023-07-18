package id.ac.astra.polytechnic.kelompok1.p5m_new.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Pelanggaran;
import id.ac.astra.polytechnic.kelompok1.p5m_new.service.PelanggaranService;

public class LIstPelanggaranResponse {
    @SerializedName("data")
    private List<Pelanggaran> mPelanggaran;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private int status;

    public List<Pelanggaran> getmPelanggaran() {
        return mPelanggaran;
    }

    public void setmPelanggaran(List<Pelanggaran> mPelanggaran) {
        this.mPelanggaran = mPelanggaran;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PelanggaranResponse{" +
                "mPelanggaran=" + mPelanggaran +
                ", message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}

