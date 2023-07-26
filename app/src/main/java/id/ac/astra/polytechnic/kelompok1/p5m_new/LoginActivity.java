package id.ac.astra.polytechnic.kelompok1.p5m_new;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.Fade;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.shashank.sony.fancytoastlib.FancyToast;

import id.ac.astra.polytechnic.kelompok1.p5m_new.helper.NetworkStateLiveData;
import id.ac.astra.polytechnic.kelompok1.p5m_new.helper.ValidationHelper;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Karyawan;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Pengguna;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.LoginMahasiswaResponse;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.LoginResponse;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.PenggunaResponse;
import id.ac.astra.polytechnic.kelompok1.p5m_new.repository.PenggunaRepository;
import id.ac.astra.polytechnic.kelompok1.p5m_new.viewmodel.KaryawanListViewModel;
import id.ac.astra.polytechnic.kelompok1.p5m_new.viewmodel.MahasiswaListViewModel;
import id.ac.astra.polytechnic.kelompok1.p5m_new.viewmodel.PenggunaListViewModel;

public class LoginActivity extends AppCompatActivity {
    SharedPreferences pref;
    private NetworkStateLiveData networkStateLiveData;
    private Button mButtonLogin;
    EditText mTxtUsername;
    TextInputLayout mTxtUsernameLayout;
    private KaryawanListViewModel mKaryawanListViewModel;
    private PenggunaListViewModel mPenggunaListViewModel;
    private MahasiswaListViewModel mMahasiswaListViewModel;
    private Pengguna mPengguna;

    public LoginActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_login);
        networkStateLiveData = new NetworkStateLiveData(this);
        networkStateLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isConnected) {
                if (isConnected) {
                    // Koneksi terhubung
                } else {
                    // Tidak ada koneksi
                    Toast.makeText(LoginActivity.this, "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
        getWindow().setEnterTransition(new Fade());
        pref = getSharedPreferences("user_pref", MODE_PRIVATE);
        if (pref.getBoolean("isLogin", false)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        mKaryawanListViewModel = new ViewModelProvider(this).get(KaryawanListViewModel.class);
        mPenggunaListViewModel = new ViewModelProvider(this).get(PenggunaListViewModel.class);
        mMahasiswaListViewModel = new ViewModelProvider(this).get(MahasiswaListViewModel.class);
        mTxtUsername = findViewById(R.id.inputuser);
        mTxtUsernameLayout = findViewById(R.id.LayoutUsername);
        mButtonLogin = findViewById(R.id.buttonlogin);
        mButtonLogin.setOnClickListener(v -> {
            if (TextUtils.isEmpty(mTxtUsername.getText().toString())) {
                Toast.makeText(this, "Please enter username", Toast.LENGTH_LONG).show();
                return;
            }
            String usr_username = mTxtUsername.getText().toString();

            if (!isConnected()) {
                FancyToast.makeText(this, "Please check your internet connection", FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                return;
            }
            if (validate(v)) {
                ProgressDialog progressDialog = ProgressDialog.show(this, "Sign In", "Signing in...");

                mMahasiswaListViewModel.getLoginMahasiswa(usr_username).observe(this, new Observer<LoginMahasiswaResponse>() {
                    @Override
                    public void onChanged(LoginMahasiswaResponse loginMahasiswaResponse) {
                        if (loginMahasiswaResponse != null) {
                            if (loginMahasiswaResponse.getStatus() == 200) {
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("nim", loginMahasiswaResponse.getmMahasiswa().getNim());
                                editor.putString("nama", loginMahasiswaResponse.getmMahasiswa().getNama());
                                editor.putString("kelas", loginMahasiswaResponse.getmMahasiswa().getKelas());
                                editor.putString("urlPhoto", loginMahasiswaResponse.getmMahasiswa().getDul_pas_foto());
                                editor.putString("role", "Mahasiswa");
                                editor.putBoolean("isLogin", true);
                                editor.apply();

                                FancyToast.makeText(LoginActivity.this, "Login Success", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                mKaryawanListViewModel.getLoginKaryawan(usr_username).observe(LoginActivity.this, new Observer<LoginResponse>() {
                                    @Override
                                    public void onChanged(LoginResponse loginResponse) {
                                        if (loginResponse != null) {
                                            if (loginResponse.getStatus() == 200) {
                                                mPenggunaListViewModel.getPenggunaByNama(loginResponse.getmKaryawan().getNama()).observe(LoginActivity.this, new Observer<PenggunaResponse>() {
                                                    @Override
                                                    public void onChanged(PenggunaResponse penggunaResponse) {
                                                        if (penggunaResponse != null) {
                                                            if (penggunaResponse.getStatus() == 200) {
                                                                mPengguna = penggunaResponse.getmPengguna();
                                                                SharedPreferences.Editor editor = pref.edit();
                                                                editor.putString("kry_username", loginResponse.getmKaryawan().getUsername());
                                                                //editor.putString("kry_nama", loginResponse.getmKaryawan().getNama());
                                                                editor.putString("kry_nama", mPengguna.getNama());
                                                                editor.putString("role", mPengguna.getRole());
                                                                editor.putString("kelas", mPengguna.getKelas());
                                                                editor.putBoolean("isLogin", true);
                                                                editor.apply();

                                                                FancyToast.makeText(LoginActivity.this, "Login Success", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                                startActivity(intent);
                                                                finish();
                                                            } else {
                                                                progressDialog.dismiss();
                                                                FancyToast.makeText(LoginActivity.this, "Login Failed", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                                                            }
                                                        } else {
                                                            progressDialog.dismiss();
                                                            FancyToast.makeText(LoginActivity.this, "Login Failed", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                                                        }

                                                    }
                                                });
                                            } else {
                                                progressDialog.dismiss();
                                                FancyToast.makeText(LoginActivity.this, "Login Failed", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                                            }
                                        } else {

                                            progressDialog.dismiss();
                                            FancyToast.makeText(LoginActivity.this, "Login Failed", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                                        }


                                    }
                                });
                            }
                        } else {
                            progressDialog.dismiss();
                            FancyToast.makeText(LoginActivity.this, "Login Failed", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                        }
                    }
                });
            }
        });
    }


    public boolean validate(View v) {
        boolean emailValidation = ValidationHelper.requiredTextInputValidation(mTxtUsernameLayout);
        //boolean passwordValidation = ValidationHelper.requiredTextInputValidation(mPasswordLayout);

        return emailValidation;
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }


}

