package id.ac.astra.polytechnic.kelompok1.p5m_new.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import id.ac.astra.polytechnic.kelompok1.p5m_new.model.DetailP5m;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Pelanggaran;

public class ListDetailP5mResponse {
    @SerializedName("data")
    private List<DetailP5m> mDetailP5m;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private int status;

    @Override
    public String toString() {
        return "ListDetailP5mResponse{" +
                "mDetailP5m=" + mDetailP5m +
                ", message='" + message + '\'' +
                ", status=" + status +
                '}';
    }

    public ListDetailP5mResponse(List<DetailP5m> mDetailP5m, String message, int status) {
        this.mDetailP5m = mDetailP5m;
        this.message = message;
        this.status = status;
    }

    public ListDetailP5mResponse() {
    }

    public List<DetailP5m> getmDetailP5m() {
        return mDetailP5m;
    }

    public void setmDetailP5m(List<DetailP5m> mDetailP5m) {
        this.mDetailP5m = mDetailP5m;
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
