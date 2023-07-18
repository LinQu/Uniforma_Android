package id.ac.astra.polytechnic.kelompok1.p5m_new.service;

import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.ListMahasiswaResponse;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.LoginMahasiswaResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MahasiswaService {
    @POST("LoginMahasiswa")
    @FormUrlEncoded
    Call<LoginMahasiswaResponse> loginMahasiswa(@Field("nim") String nim);


    @GET("getMahasiswaByKelas")
    Call<ListMahasiswaResponse> getMahasiswaByKelas(@Query("kelas") String kelas );
}
