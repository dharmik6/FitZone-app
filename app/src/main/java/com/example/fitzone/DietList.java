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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DietList extends AppCompatActivity {
    TextView add_diet ;
    private RecyclerView recyclerView;
    private DietAdapter adapter;
    private List<DietItem> dietItems = new ArrayList<>();
    DrawerLayout drawerLayout ;
    NavigationView navigationView;
    DatabaseReference databaseReference;

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
                Intent idietadd = new Intent(DietList.this,DietAdd.class);
                startActivity(idietadd);
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
        recyclerView = findViewById(R.id.diet_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DietAdapter(this, dietItems);
        recyclerView.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("diets");

        setDatabaseListener();
    }

    private void setDatabaseListener() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dietItems.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String dietName = dataSnapshot.child("dietName").getValue(String.class);
                    String imageUrl = dataSnapshot.child("imageUrl").getValue(String.class); // Change to "imageUrl"
                    String dietdesc = dataSnapshot.child("dietDescription").getValue(String.class); // Change to "imageUrl"

                    if (dietName != null && imageUrl != null) {
                        DietItem dietItem = new DietItem(dietName, imageUrl);
                        dietItems.add(dietItem);
                    }
                }
                adapter.notifyDataSetChanged(); // Notify the adapter that the data has changed
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
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

