package id.ac.astra.polytechnic.kelompok1.p5m_new.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Karyawan;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Mahasiswa;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Pengguna;

public class LoginMahasiswaResponse {
    @SerializedName("data")
    @Expose
    private Mahasiswa mMahasiswa;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("status")
    @Expose
    private int status;

    public Mahasiswa getmMahasiswa() {
        return mMahasiswa;
    }

    public void setmPengguna(Mahasiswa mMahasiswa) {
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
