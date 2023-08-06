package id.ac.astra.polytechnic.kelompok1.p5m_new.service;

import java.util.List;
import java.util.Map;

import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.ListDetailP5mResponse;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.P5mResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface P5mService {
    @POST("/cobaSaveP5m")
    Call<ListDetailP5mResponse> cobaPostP5m(@Body Map<String, List<Integer>> p5mParam, @Query("kelasParam") String kelasParam);

    @GET("/{nim}/findPelanggaranOccurrencesByNim")
    Call<List<Object[]>> findPelanggaranOccurrencesByNim(@Path("nim") String NIM,@Query("year") int tahun, @Query("month") int bulan);
}
