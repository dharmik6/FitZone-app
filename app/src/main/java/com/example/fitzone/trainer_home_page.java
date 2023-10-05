package com.example.fitzone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class trainer_home_page extends AppCompatActivity {
    CardView user , diet ,work;
    TextView logout;
    DrawerLayout drawerLayout ;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_home_page);

        user = findViewById(R.id.cd_user);

        diet = findViewById(R.id.cd_diet);
        work = findViewById(R.id.cd_work);

        //this activity is logout
        logout=findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref2 = getSharedPreferences("login2", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref2.edit();
                editor.putBoolean("flag2", false);
                editor.apply();
                redirectActivity(trainer_home_page.this, login_page.class);
                finish();
            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent user = new Intent(trainer_home_page.this,UserList.class);
                startActivity(user);
            }
        });

        diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent user = new Intent(trainer_home_page.this,DietList.class);
                startActivity(user);
            }
        });
        work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent user = new Intent(trainer_home_page.this,WorkoutList.class);
                startActivity(user);
            }
        });
        //***************************************************
        //navigation bar
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationview);
        ImageView menu = findViewById(R.id.menu);



        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer(drawerLayout);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.user) {
                    redirectActivity(trainer_home_page.this, UserList.class);
                }
                 else if (id == R.id.workout) {
                    redirectActivity(trainer_home_page.this, WorkoutList.class);
                } else if (id == R.id.diet) {
                    redirectActivity(trainer_home_page.this, DietList.class);
                }  else if (id == R.id.logout) {
                    SharedPreferences pref2 = getSharedPreferences("login2", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref2.edit();
                    editor.putBoolean("flag2", false);
                    editor.apply();
                    redirectActivity(trainer_home_page.this, login_page.class);
                    finish();

                }
                else if (id == R.id.profile) {
                    redirectActivity(trainer_home_page.this, trainer_profile_page.class);
                }
                 else {
                    Toast.makeText(trainer_home_page.this, "profile", Toast.LENGTH_SHORT).show();
                }

                closeDrawer(drawerLayout);

                return true;
            }
        });
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public static void redirectActivity(Activity activity, Class secondActivity) {
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }

}

