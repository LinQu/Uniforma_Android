package id.ac.astra.polytechnic.kelompok1.p5m_new.model.response;

import com.google.gson.annotations.SerializedName;

import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Pelanggaran;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Pengguna;

public class PelanggaranResponse {
    @SerializedName("data")
    private Pelanggaran mPelanggaran;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private int status;

    public Pelanggaran getmPelanggaran() {
        return mPelanggaran;
    }

    public void setmPelanggaran(Pelanggaran mPelanggaran) {
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
        return "LoginResponse{" +
                "mPelanggaran=" + mPelanggaran +
                ", message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}
