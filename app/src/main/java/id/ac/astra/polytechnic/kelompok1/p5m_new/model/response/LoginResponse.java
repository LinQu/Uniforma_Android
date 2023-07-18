package id.ac.astra.polytechnic.kelompok1.p5m_new.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Karyawan;

public class LoginResponse {
    @SerializedName("data")
    @Expose
    private Karyawan mKaryawan;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("status")
    @Expose
    private int status;

    public Karyawan getmKaryawan() {
        return mKaryawan;
    }

    public void setmKaryawan(Karyawan mKaryawan) {
        this.mKaryawan = mKaryawan;
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
                    "mKaryawwan=" + mKaryawan +
                    ", message='" + message + '\'' +
                    ", status=" + status +
                    '}';
        }
}
