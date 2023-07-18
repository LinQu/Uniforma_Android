package id.ac.astra.polytechnic.kelompok1.p5m_new.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Kelas;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Pelanggaran;

public class ListKelasResponse  {
    @SerializedName("data")
    private List<Kelas> mKelas;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private int status;

    public List<Kelas> getmKelas() {
        return mKelas;
    }

    public void setmKelas(List<Kelas> mKelas) {
        this.mKelas = mKelas;
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
        return "ListKelasResponse{" +
                "mPelanggaran=" + mKelas +
                ", message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}
