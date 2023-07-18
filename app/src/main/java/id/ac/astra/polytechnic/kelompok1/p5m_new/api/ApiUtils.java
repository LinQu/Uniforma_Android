package id.ac.astra.polytechnic.kelompok1.p5m_new.api;

import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Kelas;
import id.ac.astra.polytechnic.kelompok1.p5m_new.service.KaryawanService;
import id.ac.astra.polytechnic.kelompok1.p5m_new.service.KelasService;
import id.ac.astra.polytechnic.kelompok1.p5m_new.service.MahasiswaService;
import id.ac.astra.polytechnic.kelompok1.p5m_new.service.PelanggaranService;
import id.ac.astra.polytechnic.kelompok1.p5m_new.service.PenggunaService;

public class ApiUtils {
    //public static final String BASE_URL_API = "http://172.20.10.3:8080/";
    public static final String BASE_URL_API = "http://172.105.123.136/";
    //public static final String BASE_URL_API = "http://10.8.0.200:8080/";

    private ApiUtils() {
    }

    public static PelanggaranService getP5mService() {
        return RetrofitClient.getClient(BASE_URL_API).create(PelanggaranService.class);
    }

    public static KaryawanService getKaryawanService() {
        return RetrofitClient.getClient(BASE_URL_API).create(KaryawanService.class);
    }

    public static MahasiswaService getMahasiswaService() {
        return RetrofitClient.getClient(BASE_URL_API).create(MahasiswaService.class);
    }

    public static PenggunaService getPenggunaService() {
        return RetrofitClient.getClient(BASE_URL_API).create(PenggunaService.class);
    }

    public static KelasService getKelasService() {
        return RetrofitClient.getClient(BASE_URL_API).create(KelasService.class);
    }
}
