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

public class WorkoutList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private WorkoutAdapter adapter;
    private List<WorkoutItem> workoutItems = new ArrayList<>();

    TextView add_work ;
    DrawerLayout drawerLayout ;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_list);

        // Sample workout items, you can add more items as needed
        workoutItems.add(new WorkoutItem("Workout 1", "Focus Area 1", R.drawable.baseline_image_24));
        workoutItems.add(new WorkoutItem("Workout 2", "Focus Area 2", R.drawable.baseline_image_24));
        workoutItems.add(new WorkoutItem("Workout 3", "Focus Area 3", R.drawable.baseline_image_24));

        recyclerView = findViewById(R.id.work_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        adapter = new WorkoutAdapter(getApplicationContext(), workoutItems);
        recyclerView.setAdapter(adapter);


        //***************************************************
        //navigation bar
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationview);
        ImageView menu = findViewById(R.id.menu);
        add_work = findViewById(R.id.add_workout);

        add_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iworkadd = new Intent(WorkoutList.this,WorkoutAdd.class);
                startActivity(iworkadd);
            }
        });


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
                    redirectActivity(WorkoutList.this, UserList.class);
                }  else if (id == R.id.workout) {
                    redirectActivity(WorkoutList.this, WorkoutList.class);
                } else if (id == R.id.diet) {
                    redirectActivity(WorkoutList.this, DietList.class);
                } else {
                    Toast.makeText(WorkoutList.this, "profile", Toast.LENGTH_SHORT).show();
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

