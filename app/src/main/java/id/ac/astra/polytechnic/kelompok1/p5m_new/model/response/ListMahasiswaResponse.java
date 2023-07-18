package id.ac.astra.polytechnic.kelompok1.p5m_new.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Mahasiswa;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Pengguna;

public class ListMahasiswaResponse {
    @SerializedName("data")
    private List<Mahasiswa> mMahasiswa;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private int status;

    public List<Mahasiswa> getmMahasiswa() {
        return mMahasiswa;
    }

    public void setmMahasiswa(List<Mahasiswa> mMahasiswa) {
        this.mMahasiswa = mMahasiswa;
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
                "mMahasiswa=" + mMahasiswa +
                ", message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}
