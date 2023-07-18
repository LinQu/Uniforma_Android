package id.ac.astra.polytechnic.kelompok1.p5m_new.service;


import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Pengguna;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.ListPenggunaResponse;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.PenggunaResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PenggunaService {

    @GET("getPengguna")
    Call<ListPenggunaResponse> getPengguna();

    @POST("getPenggunaByNama")
    @FormUrlEncoded
    Call<PenggunaResponse> getPenggunaByNama(@Field("nama") String nama);

    @POST("savePengguna")
    Call<PenggunaResponse> savePengguna(@Body Pengguna pengguna);

    @PUT("updatePengguna")
    Call<PenggunaResponse> updatePengguna(@Body Pengguna pengguna);

    @DELETE("deletePengguna/{id}")
    Call<PenggunaResponse> deletePengguna(@Path("id") int id);
}
