package com.example.fitzone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class WorkoutData extends AppCompatActivity {
    Button update, delete;
    TextView worknametxt, description, focus;
    ImageView workimg;
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_data);

        // Initialize your TextView elements
        worknametxt = findViewById(R.id.work_name);
        focus = findViewById(R.id.focus);
        description = findViewById(R.id.workdesc);

        workimg = findViewById(R.id.work_img);
        update = findViewById(R.id.btn_Update);
        delete = findViewById(R.id.btn_Delete);

        // Retrieve data from the intent and set it to TextViews and ImageView
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("workoutname") && intent.hasExtra("workoutesc") && intent.hasExtra("focus") && intent.hasExtra("workimage")) {
            String workname = intent.getStringExtra("workoutname");
            String workoutesc = intent.getStringExtra("workoutesc");
            String focusText = intent.getStringExtra("focus");

            // Set data to TextViews
            worknametxt.setText(workname);
            description.setText(workoutesc);
            focus.setText(focusText);

            // Load image using Glide
            String imageUrl = intent.getStringExtra("workimage");
            Glide.with(this)
                    .load(imageUrl)
                    .into(workimg);
        }

        // Set click listener for the delete button
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the workout name from the intent
                final String workoutname = getIntent().getStringExtra("workoutname");

                // Get reference to the "workouts" node in the Firebase database
                DatabaseReference workoutsRef = FirebaseDatabase.getInstance().getReference("workouts");

                // Query the database to find the specific workout by workout name
                Query query = workoutsRef.orderByChild("workoutName").equalTo(workoutname);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // Loop through the results and remove the workout
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            snapshot.getRef().removeValue();
                        }
                        Toast.makeText(WorkoutData.this, "Record Deleted", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle any errors here
                        Toast.makeText(WorkoutData.this, "Database Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
