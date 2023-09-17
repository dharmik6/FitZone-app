package com.example.fitzone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.Locale;


public class age_page extends AppCompatActivity {

    ImageView backButton;
    NumberPicker age_num ;
    Button next_page ;
    int age ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age_page);

        backButton = findViewById(R.id.btn_next_page);
        age_num = findViewById(R.id.age_num);
        next_page = findViewById(R.id.btn_continue);

        age_num.setValue(18);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }

        });

        age= 18 ;
        age_num.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if(age_num==null)
                {
                    age = 150 ;
                }
                else{
                    age = newVal;
                }
            }
        });

        next_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize Firebase Database reference
                DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();

                // Assuming you have a "users" node where you want to store the age information
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    String userId = user.getUid();
                    DatabaseReference userRef = databaseRef.child("users").child(userId);

                    // Store the selected age under the user's data
                    userRef.child("age").setValue(age);

                    // Navigate to the next activity
                    Intent height = new Intent(age_page.this, height_page.class);
                    startActivity(height);
                    finish();
                } else {
                    // Handle the case where the user is not authenticated
                    Toast.makeText(age_page.this, "User not authenticated", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}