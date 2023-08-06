package id.ac.astra.polytechnic.kelompok1.p5m_new.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import id.ac.astra.polytechnic.kelompok1.p5m_new.model.P5m;

public class P5mResponse {
    @SerializedName("data")
    @Expose
    private P5m mP5m;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("status")
    @Expose
    private int status;

    @Override
    public String toString() {
        return "P5mResponse{" +
                "mP5m=" + mP5m +
                ", message='" + message + '\'' +
                ", status=" + status +
                '}';
    }

    public P5m getmP5m() {
        return mP5m;
    }

    public void setmP5m(P5m mP5m) {
        this.mP5m = mP5m;
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
}
