package id.ac.astra.polytechnic.kelompok1.p5m_new;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.view.Menu;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

import id.ac.astra.polytechnic.kelompok1.p5m_new.fragment.DashboardFragment;
import id.ac.astra.polytechnic.kelompok1.p5m_new.fragment.DashboardMahasiswaFragment;
import id.ac.astra.polytechnic.kelompok1.p5m_new.fragment.HistoryAbsenFragment;
import id.ac.astra.polytechnic.kelompok1.p5m_new.fragment.MahasiswaFormFragment;
import id.ac.astra.polytechnic.kelompok1.p5m_new.fragment.MahasiswaFormFragmentCoba;
import id.ac.astra.polytechnic.kelompok1.p5m_new.fragment.MahasiswaListFragment;
import id.ac.astra.polytechnic.kelompok1.p5m_new.fragment.ProfileMahasiswaFragment;
import id.ac.astra.polytechnic.kelompok1.p5m_new.helper.MyNotificationReceiver;

public class MainActivity extends AppCompatActivity {
    SharedPreferences preferences;

    BottomNavigationView bottomNavigationView;
    MahasiswaListFragment mahasiswaListFragment = new MahasiswaListFragment();
    DashboardFragment dashboardFragment = new DashboardFragment();
    DashboardMahasiswaFragment dashboardMahasiswaFragment = new DashboardMahasiswaFragment();
    //MahasiswaFormFragment mahasiswaFormFragment = new MahasiswaFormFragment();
    MahasiswaFormFragmentCoba mahasiswaFormFragment = new MahasiswaFormFragmentCoba();
    HistoryAbsenFragment historyAbsenFragment = new HistoryAbsenFragment();
    ProfileMahasiswaFragment profileMahasiswaFragment = new ProfileMahasiswaFragment();
    FloatingActionButton mFloatingActionButton;


    public ImageButton mButtonView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Dapatkan instance dari AlarmManager
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Buat Intent untuk mengeksekusi BroadcastReceiver ketika waktu yang ditentukan tercapai
        Intent intents = new Intent(this, MyNotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intents, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        // Tentukan waktu untuk notifikasi (07:20 pagi)
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        // Pastikan waktu notifikasi belum terlewati, jika ya, tambahkan 1 hari
        if (System.currentTimeMillis() > calendar.getTimeInMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        // Jadwalkan notifikasi menggunakan AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        preferences = this.getSharedPreferences("user_pref", Context.MODE_PRIVATE);
        String role = (preferences.getString("role", ""));
        boolean isLogin = (preferences.getBoolean("isLogin", false));

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (preferences == null){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            this.finish();
        }

        if (fragment == null) {
            if (role.equals("Mahasiswa")) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, dashboardMahasiswaFragment).commit();
                bottomNavigationView = findViewById(R.id.bottomNavigationView);
                bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, dashboardMahasiswaFragment).commit();
                            return true;
                        case R.id.nav_profile:
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, profileMahasiswaFragment).commit();
                            return true;
                    }
                    return false;
                });
                mFloatingActionButton = findViewById(R.id.cam);
                mFloatingActionButton.setOnClickListener(v -> {
                    //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mahasiswaFormFragment).commit();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, historyAbsenFragment).commit();
                });
            }else {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, dashboardFragment).commit();
                bottomNavigationView = findViewById(R.id.bottomNavigationView);
                bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, dashboardFragment).commit();
                            return true;
                        case R.id.nav_profile:
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mahasiswaListFragment).commit();

                            //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mahasiswaListFragment).addToBackStack("DashboardFragment").commit();
                            return true;
                    }
                    return false;
                });
                mFloatingActionButton = findViewById(R.id.cam);
                mFloatingActionButton.setOnClickListener(v -> {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mahasiswaFormFragment).commit();
                });

            }

        }
    }
}