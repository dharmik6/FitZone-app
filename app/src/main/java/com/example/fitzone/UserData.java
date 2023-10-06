package com.example.fitzone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserData extends AppCompatActivity {
    // Define your TextView elements
    TextView nameTextView,weightTextView,numberTextView,ageTextView,heightTextView,emailTextView,genderTextView;
    Button delete ;
    DatabaseReference databaseReference;

    FirebaseDatabase database;
    CircleImageView userimg;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
        delete = findViewById(R.id.btn_Delete);

        userimg=findViewById(R.id.uimage);
        database = FirebaseDatabase.getInstance();

        // Initialize your TextView elements
        nameTextView = findViewById(R.id.uname);
        numberTextView = findViewById(R.id.uPhone);
        ageTextView = findViewById(R.id.uAge);
        genderTextView = findViewById(R.id.uGender);
        emailTextView = findViewById(R.id.uEmail);
        heightTextView = findViewById(R.id.uHeight);
        weightTextView = findViewById(R.id.uWeight);

        // Retrieve the username from the intent
        String username = getIntent().getStringExtra("username");

        // Initialize the database reference to the specific user
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("users").child(username);
        setDatabaseListener();

        nameTextView.setText(getIntent().getStringExtra("username"));
        numberTextView.setText(getIntent().getStringExtra("number"));
        emailTextView.setText(getIntent().getStringExtra("email"));
        genderTextView.setText(getIntent().getStringExtra("gender"));
        heightTextView.setText(getIntent().getStringExtra("height"));
        weightTextView.setText(getIntent().getStringExtra("weight"));
        ageTextView.setText(getIntent().getStringExtra("age"));


        String getUserImage = getIntent().getStringExtra("userimage");

        Glide.with(UserData.this)
                .load(getUserImage) // Load image URL
                .into(userimg); // Set the loaded image to the ImageView
    }

    private void setDatabaseListener() {






        //**********************************
        //back page button
        ImageView back_page = findViewById(R.id.btn_next_page);
        back_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}