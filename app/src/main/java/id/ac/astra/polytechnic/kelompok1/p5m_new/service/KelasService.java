package id.ac.astra.polytechnic.kelompok1.p5m_new.service;

import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.ListKelasResponse;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.ListPenggunaResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface KelasService {
    @GET("getKelas")
    Call<ListKelasResponse> getKelas();
}
