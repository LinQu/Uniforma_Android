package id.ac.astra.polytechnic.kelompok1.p5m_new.service;

import java.util.List;

import id.ac.astra.polytechnic.kelompok1.p5m_new.model.KehadiranPerBulanDTO;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AbsenService {
    @GET("getHistoryAbsenByNim")
    Call<List<Object[]>> getHistoryAbsenByNim(@Query("nim") String nim);

    @GET("getAbsenByNimAndMonth")
    Call<List<Object[]>> getAbsenByNimAndMonth(@Query("year") int year,@Query("month") int month,@Query("nim") String nim  );

    @GET("{nim}/persentase")
    Call<List<KehadiranPerBulanDTO>> getPersentaseKehadiran(@Path("nim") String nim);

    @GET("{nim}/persentaseBulan")
    Call<KehadiranPerBulanDTO> getPersentaseKehadiranByBulan(@Path("nim") String nim, @Query("year") int tahun, @Query("month") int bulan);

    @GET("{nim}/listHistoryBulan")
    Call<List<Object[]>> getHistoryListMonthByNim(@Path("nim") String nim);
}
