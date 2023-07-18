package id.ac.astra.polytechnic.kelompok1.p5m_new.service;

import java.util.List;

import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Karyawan;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface KaryawanService {
    @POST("LoginKaryawan")
    @FormUrlEncoded
    Call<LoginResponse> loginKaryawan(@Field("username") String username);
}
