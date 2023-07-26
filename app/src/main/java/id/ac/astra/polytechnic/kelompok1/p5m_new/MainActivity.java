package id.ac.astra.polytechnic.kelompok1.p5m_new;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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

import id.ac.astra.polytechnic.kelompok1.p5m_new.fragment.DashboardFragment;
import id.ac.astra.polytechnic.kelompok1.p5m_new.fragment.DashboardMahasiswaFragment;
import id.ac.astra.polytechnic.kelompok1.p5m_new.fragment.HistoryAbsenFragment;
import id.ac.astra.polytechnic.kelompok1.p5m_new.fragment.MahasiswaFormFragment;
import id.ac.astra.polytechnic.kelompok1.p5m_new.fragment.MahasiswaListFragment;
import id.ac.astra.polytechnic.kelompok1.p5m_new.fragment.ProfileMahasiswaFragment;

public class MainActivity extends AppCompatActivity {
    SharedPreferences preferences;

    BottomNavigationView bottomNavigationView;
    MahasiswaListFragment mahasiswaListFragment = new MahasiswaListFragment();
    DashboardFragment dashboardFragment = new DashboardFragment();
    DashboardMahasiswaFragment dashboardMahasiswaFragment = new DashboardMahasiswaFragment();
    MahasiswaFormFragment mahasiswaFormFragment = new MahasiswaFormFragment();
    HistoryAbsenFragment historyAbsenFragment = new HistoryAbsenFragment();
    ProfileMahasiswaFragment profileMahasiswaFragment = new ProfileMahasiswaFragment();
    FloatingActionButton mFloatingActionButton;


    public ImageButton mButtonView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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