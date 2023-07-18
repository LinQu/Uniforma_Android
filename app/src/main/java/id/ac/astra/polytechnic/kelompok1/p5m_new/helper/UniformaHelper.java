package id.ac.astra.polytechnic.kelompok1.p5m_new.helper;

import java.util.Date;

public class UniformaHelper {
    public static String getTanggalSekarang(){
        //buatkan tanggak sekarang denga format Senin,15 Juni 2023 dengan format negara indonesia
        Date date = new Date();
        String[] namaHari = {"Minggu","Senin","Selasa","Rabu","Kamis","Jumat","Sabtu"};
        String[] namaBulan = {"Januari","Februari","Maret","April","Mei","Juni","Juli","Agustus",
                "September","Oktober","November","Desember"};
        String tanggalSekarang = namaHari[date.getDay()]+", "+date.getDate()+" "+namaBulan[date.getMonth()]+" "+(date.getYear()+1900);
        return tanggalSekarang;

    }
}
