package id.ac.astra.polytechnic.kelompok1.p5m_new.api;

import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Kelas;
import id.ac.astra.polytechnic.kelompok1.p5m_new.service.AbsenService;
import id.ac.astra.polytechnic.kelompok1.p5m_new.service.KaryawanService;
import id.ac.astra.polytechnic.kelompok1.p5m_new.service.KelasService;
import id.ac.astra.polytechnic.kelompok1.p5m_new.service.MahasiswaService;
import id.ac.astra.polytechnic.kelompok1.p5m_new.service.P5mService;
import id.ac.astra.polytechnic.kelompok1.p5m_new.service.PelanggaranService;
import id.ac.astra.polytechnic.kelompok1.p5m_new.service.PenggunaService;

public class ApiUtils {
    public static final String BASE_URL_API = "http://192.168.62.201:8080/";
//    //public static final String BASE_URL_API = "http://192.168.166.223:8080/";
    //public static final String BASE_URL_API = "http://10.8.0.200:8080/";

    private ApiUtils() {
    }

    public static PelanggaranService getPelanggaranService() {
        return RetrofitClient.getClient(BASE_URL_API).create(PelanggaranService.class);
    }

    public static P5mService getP5mService() {
        return RetrofitClient.getClient(BASE_URL_API).create(P5mService.class);
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

    public static AbsenService getAbsenService() {
        return RetrofitClient.getClient(BASE_URL_API).create(AbsenService.class);
    }
}
