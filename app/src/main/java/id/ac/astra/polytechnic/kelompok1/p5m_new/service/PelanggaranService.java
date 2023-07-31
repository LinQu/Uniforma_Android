package id.ac.astra.polytechnic.kelompok1.p5m_new.service;

import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Pelanggaran;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.LIstPelanggaranResponse;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.PelanggaranResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PelanggaranService {
    @GET("getPelanggarans")
    Call<LIstPelanggaranResponse> getPelanggarans();

    @DELETE("deletePelanggaran/{id}")
    Call<PelanggaranResponse> deletePelanggaran(@Path("id") int id);

    @PUT("updatePelanggaran")
    Call<PelanggaranResponse> updatePelanggaran(@Body Pelanggaran pelanggaran);

    @POST("savePelanggaran")
    Call<PelanggaranResponse> savePelangggaran(@Body Pelanggaran pelanggaran);
}
