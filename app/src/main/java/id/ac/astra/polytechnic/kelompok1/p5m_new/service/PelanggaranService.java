package id.ac.astra.polytechnic.kelompok1.p5m_new.service;

import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.LIstPelanggaranResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PelanggaranService {
    @GET("getPelanggarans")
    Call<LIstPelanggaranResponse> getPelanggarans();
}
