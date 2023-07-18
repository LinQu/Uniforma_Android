package id.ac.astra.polytechnic.kelompok1.p5m_new.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Karyawan;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Pengguna;

public class    PenggunaResponse {
    @SerializedName("data")
    private Pengguna mPengguna;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private int status;

    public Pengguna getmPengguna() {
        return mPengguna;
    }

    public void setmPengguna(Pengguna mPengguna) {
        this.mPengguna = mPengguna;
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
                "mPengguna=" + mPengguna +
                ", message='" + message + '\'' +
                ", status=" + status +
                '}';
    }

}
