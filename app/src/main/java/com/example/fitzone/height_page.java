package com.example.fitzone;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shawnlin.numberpicker.NumberPicker;

public class height_page extends AppCompatActivity {

    ImageView back_Page ;
    NumberPicker height_num;
    Integer height;
    Button next_page;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_height_page);

        height_num = findViewById(R.id.height);
        next_page = findViewById((R.id.btn_continue));

        height_num.setValue(150);
        height=150;
        height_num.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if(height_num==null)
                {
                    height = 150 ;
                }
                else{
                    height = newVal;
                }

            }

        });

        next_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                            userRef.child("height").setValue(height);

                            // Navigate to the next activity
                            Intent height = new Intent(height_page.this, weight_page.class);
                            startActivity(height);
                            finish();
                        } else {
                            // Handle the case where the user is not authenticated
                            Toast.makeText(height_page.this, "User not authenticated", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        back_Page = findViewById(R.id.btn_next_page);
        back_Page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
