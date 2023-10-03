package com.example.fitzone;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile_page extends AppCompatActivity {

    private ImageView profileImageView;
    private TextView userNameTextView, userAgeTextView, userGenderTextView, userHeightTextView, userWeightTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        profileImageView = findViewById(R.id.profile_img);
        userNameTextView = findViewById(R.id.user_name);
        userAgeTextView = findViewById(R.id.user_age);
        userGenderTextView = findViewById(R.id.user_gender);
        userHeightTextView = findViewById(R.id.user_height);
        userWeightTextView = findViewById(R.id.user_weight);

        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading user data");
        pd.setCancelable(false);
        pd.show();
// Retrieve and set the user's name, email, and profile image from Firebase Realtime Database
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        // Assuming `userId` is the unique identifier for the user
        String userId = currentUser.getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve user data
                    String name = dataSnapshot.child("name").getValue(String.class);
                    int age = dataSnapshot.child("age").getValue(Integer.class);
                    String gender = dataSnapshot.child("gender").getValue(String.class);
                    int height = dataSnapshot.child("height").getValue(Integer.class);
                    int weight = dataSnapshot.child("weight").getValue(Integer.class);
                    String imageUrl = dataSnapshot.child("img").getValue(String.class);

                    // Set user data to UI elements
                    userNameTextView.setText(name);
                    userAgeTextView.setText(String.valueOf(age));
                    userGenderTextView.setText(gender);
                    userHeightTextView.setText(String.valueOf(height));
                    userWeightTextView.setText(String.valueOf(weight));

                    // Load user profile image into ImageView using Glide
                    Glide.with(profile_page.this).load(imageUrl).into(profileImageView);
                }

                // Dismiss the progress dialog after loading data
                pd.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database errors
                pd.dismiss();
            }
        });
    }
}
