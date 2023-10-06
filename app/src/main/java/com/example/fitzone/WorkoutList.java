package com.example.fitzone;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WorkoutList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private WorkoutAdapter adapter;
    private List<WorkoutItem> workoutItems = new ArrayList<>();

    TextView add_work;
    DatabaseReference databaseReference;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_list);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..."); // Set the message for the progress dialog
        progressDialog.setCancelable(false);

        // Initialize RecyclerView and its adapter
        recyclerView = findViewById(R.id.work_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WorkoutAdapter(this, workoutItems);
        recyclerView.setAdapter(adapter);

        // Initialize Firebase database reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("workouts");

        // Set up the database listener to fetch workout data
        setDatabaseListener();

        // Set up navigation drawer and menu items
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationview);
        ImageView menu = findViewById(R.id.menu);
        add_work = findViewById(R.id.add_workout);

        add_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the WorkoutAdd activity
                Intent iworkadd = new Intent(WorkoutList.this, WorkoutAdd.class);
                startActivity(iworkadd);
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the navigation drawer
                openDrawer(drawerLayout);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.user) {
                    // Redirect to UserList activity
                    redirectActivity(WorkoutList.this, UserList.class);
                } else if (id == R.id.workout) {
                    // Already in the WorkoutList activity, do nothing
                }
                else if (id == R.id.diet) {
                    // Redirect to DietList activity
                    redirectActivity(WorkoutList.this, DietList.class);
                } else if (id == R.id.logout)
                {
                    SharedPreferences pref = getSharedPreferences("login", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("flag", false);
                    editor.apply();

                    // After logging out, navigate to the LoginActivity
                    Intent intent = new Intent(WorkoutList.this, trainer_login.class);
                    startActivity(intent);
                    finish(); // Close th
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
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }

    private void setDatabaseListener() {
        progressDialog.show();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                workoutItems.clear();

                // Loop through the dataSnapshot and retrieve workout data
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String workName = dataSnapshot.child("workoutName").getValue(String.class);
                    String focus = dataSnapshot.child( "workoutFocusArea").getValue(String.class);
                    String imageUrl = dataSnapshot.child("workoutImageResourceId").getValue(String.class);
                    String workDesc = dataSnapshot.child("workoutDescription").getValue(String.class);

                    if (workName != null && imageUrl != null && workDesc != null) {
                        // Create WorkoutItem objects and add them to the workoutItems list
                        WorkoutItem workoutItem = new WorkoutItem(workName, focus, workDesc,imageUrl);
                        workoutItems.add(workoutItem);
                    }
                }

                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
                Toast.makeText(WorkoutList.this, "Database Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}