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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class diet_data extends AppCompatActivity {

    ImageView dietImage;
    TextView dietName, dietDesc;
    Button update, delete;
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_data2);


        dietImage = findViewById(R.id.diet_image);
        dietName = findViewById(R.id.diet_name);
        dietDesc = findViewById(R.id.diet_description);


        //**********************************
        //back page button
        ImageView back_page = findViewById(R.id.btn_next_page);
        back_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        // Retrieve the username from the intent
        String dietname = getIntent().getStringExtra("dietname");

        // Initialize the database reference to the specific user
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("diets").child(dietname);


        dietName.setText(getIntent().getStringExtra("dietname"));
        dietDesc.setText(getIntent().getStringExtra("dietdesc"));


        String getUserImage = getIntent().getStringExtra("dietimage");

        Glide.with(diet_data.this)
                .load(getUserImage) // Load image URL
                .into(dietImage); // Set the loaded image to the ImageView
    }
}



