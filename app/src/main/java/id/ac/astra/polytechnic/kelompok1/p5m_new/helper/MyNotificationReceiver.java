package id.ac.astra.polytechnic.kelompok1.p5m_new.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyNotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Di sini, Anda dapat menampilkan notifikasi yang diinginkan
        showNotification(context, "Judul Notifikasi", "Pesan Notifikasi");
    }

    private void showNotification(Context context, String title, String message) {
        // Kode untuk menampilkan notifikasi menggunakan NotificationManager
        // (lihat contoh di bawah)
    }
}