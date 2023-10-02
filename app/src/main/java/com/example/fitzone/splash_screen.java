package com.example.fitzone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class splash_screen extends AppCompatActivity {

    TextView logo_text ;
    ImageView logo ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Animation fad_in = AnimationUtils.loadAnimation(this, R.anim.fade_in_out);

        logo = findViewById(R.id.logoImage);
        logo.startAnimation(fad_in);

        logo_text = findViewById(R.id.logo_text);
        logo_text.startAnimation(fad_in);





        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
                Boolean check = pref.getBoolean("flag", false);

                Intent inext;
                if (check) {
                    // User is logged in, navigate to home_page
                    inext = new Intent(splash_screen.this, home_page.class);
                } else {
                    // User is not logged in, navigate to login_page
                    inext = new Intent(splash_screen.this, login_page.class);
                }

                startActivity(inext);
                finish(); // Close the splash screen activity after navigating
            }
        }, 2000);




    }
}