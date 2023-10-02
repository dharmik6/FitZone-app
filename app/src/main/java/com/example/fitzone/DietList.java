package com.example.fitzone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class DietList extends AppCompatActivity {
    TextView add_diet ;
    private RecyclerView recyclerView;
    private DietAdapter adapter;
    private List<DietItem> dietItems = new ArrayList<>();
    DrawerLayout drawerLayout ;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_list);

        //*****************************
        // add diet
        add_diet = findViewById(R.id.add_diet);

        add_diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent idietadd = new Intent(DietList.this,WorkoutAdd.class);
                startActivity(idietadd);
            }
        });
        // Sample diet items, you can add more items as needed
        dietItems.add(new DietItem("Diet 1", R.drawable.round_menu_24));
        dietItems.add(new DietItem("Diet 2", R.drawable.round_menu_24));
        dietItems.add(new DietItem("Diet 3", R.drawable.round_menu_24));

        recyclerView = findViewById(R.id.diet_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        adapter = new DietAdapter(getApplicationContext(), dietItems);
        recyclerView.setAdapter(adapter);

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
                    redirectActivity(DietList.this, UserList.class);
                }
                else if (id == R.id.workout) {
                    redirectActivity(DietList.this, WorkoutList.class);
                } else if (id == R.id.diet) {
                    redirectActivity(DietList.this, DietList.class);
                } else {
                    Toast.makeText(DietList.this, "profile", Toast.LENGTH_SHORT).show();
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

