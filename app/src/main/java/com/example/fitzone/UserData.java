package com.example.fitzone;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserData extends AppCompatActivity {
    // Define your TextView elements
    TextView nameTextView, numberTextView, ageTextView, genderTextView, emailTextView, heightTextView, weightTextView;
    Button delete ;
    DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
        delete = findViewById(R.id.btn_Delete);

        // Initialize your TextView elements
        nameTextView = findViewById(R.id.uname);
        numberTextView = findViewById(R.id.uPhone);
        ageTextView = findViewById(R.id.uAge);
        genderTextView = findViewById(R.id.uGender);
        emailTextView = findViewById(R.id.uEmail);
        heightTextView = findViewById(R.id.uHeight);
        weightTextView = findViewById(R.id.uWeight);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("users");

        setDatabaseListener();

    }

    private void setDatabaseListener() {

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Check if the user exists
                if (dataSnapshot.exists()) {
                    // Retrieve data from the dataSnapshot and set it to your TextViews
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String number = dataSnapshot.child("number").getValue(String.class);
                    String age = dataSnapshot.child("age").getValue(String.class);
                    String gender = dataSnapshot.child("gender").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String height = dataSnapshot.child("height").getValue(String.class);
                    String weight = dataSnapshot.child("weight").getValue(String.class);

                    // Set the retrieved data to your TextViews
                    nameTextView.setText(name);
                    numberTextView.setText(number);
                    ageTextView.setText(age);
                    genderTextView.setText(gender);
                    emailTextView.setText(email);
                    heightTextView.setText(height);
                    weightTextView.setText(weight);
                } else {
                    // Handle the case where the user does not exist
                    Toast.makeText(UserData.this, "User not found", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors here
                Toast.makeText(UserData.this, "Database Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Rest of your code

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Here, you can delete the user's data from the Firebase Realtime Database
                // Assuming you have a specific user ID, replace "userID" with the actual user ID
                String userID = "users";

                DatabaseReference userRef = databaseReference.child(userID);
                userRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // User data has been successfully deleted
                        Toast.makeText(UserData.this, "Record Deleted", Toast.LENGTH_SHORT).show();
                        onBackPressed(); // Go back to the previous screen
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle the case where deletion fails
                        Toast.makeText(UserData.this, "Failed to delete record: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

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