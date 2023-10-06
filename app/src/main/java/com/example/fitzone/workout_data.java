package com.example.fitzone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class workout_data extends AppCompatActivity {
    TextView worknametxt, description, focus;
    ImageView workimg;
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_data2);

        // Initialize your TextView elements
        worknametxt = findViewById(R.id.work_name);
        focus = findViewById(R.id.focus);
        description = findViewById(R.id.workdesc);

        workimg = findViewById(R.id.work_img);

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

    }
}