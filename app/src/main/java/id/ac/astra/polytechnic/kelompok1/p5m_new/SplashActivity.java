package id.ac.astra.polytechnic.kelompok1.p5m_new;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;


public class SplashActivity extends AppCompatActivity {

    Animation topAnim, bottomAnim;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setEnterTransition(new Fade());
        //Animation
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        // Load animasi fade-in dan fade-out
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation fadeOutAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_out);

        //Hooks
        image = findViewById(R.id.logoastra);
        image.setAnimation(bottomAnim);


        //handler 3 detik dan pindah ke login activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                android.content.Intent loginIntent = new android.content.Intent(SplashActivity.this, LoginActivity.class);

                startActivity(loginIntent);
                Animatoo.INSTANCE.animateFade(SplashActivity.this);
                finish();
            }
        }, 2200);

    }
}