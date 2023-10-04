package com.example.fitzone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registration_page extends AppCompatActivity {
    EditText name, phone_number, email, password;
    Button sign_up, google;
    CheckBox tnc;
    ImageView backpress;
    FirebaseAuth auth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        // Connect with EditText fields
        name = findViewById(R.id.txt_name);
        phone_number = findViewById(R.id.txt_phone);
        email = findViewById(R.id.txt_email);
        password = findViewById(R.id.txt_password);

        // Connect with buttons and checkbox
        sign_up = findViewById(R.id.sign_in);
//        google = findViewById(R.id.btn_google);
        tnc = findViewById(R.id.tnc);

        // Connect with ImageView for back button
        backpress = findViewById(R.id.btn_next_page);

        // Initialize Firebase Authentication and Realtime Database
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        // Back button functionality
        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Registration button click listener
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString();
                String pass = password.getText().toString();
                String uname = name.getText().toString();
                String number = phone_number.getText().toString();

                // Validation of all fields
                if (TextUtils.isEmpty(name.getText().toString())) {
                    name.setError("Name is compulsory");
                } else if (TextUtils.isEmpty(phone_number.getText().toString())) {
                    phone_number.setError("Phone number is compulsory");
                } else if (TextUtils.isEmpty(email.getText().toString())) {
                    email.setError("Email is compulsory");
                } else if (!isValidEmail(mail)) {
                    Toast.makeText(registration_page.this, "Invalid Email Address!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password.getText().toString())) {
                    password.setError("Password is compulsory");
                } else if (!tnc.isChecked()) {
                    Toast.makeText(registration_page.this, "Please accept the terms and conditions", Toast.LENGTH_SHORT).show();
                } else {
                    // Create a new user with email and password using Firebase Authentication
                    auth.createUserWithEmailAndPassword(mail, pass)
                            .addOnCompleteListener(registration_page.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = auth.getCurrentUser();
                                        // Store additional user data in the Realtime Database
                                        saveUserDataToDatabase(user.getUid(), uname, number, mail);
                                        // Proceed to the next activity or perform any desired actions
                                        Toast.makeText(registration_page.this, "Registration successful.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(registration_page.this, gender_page.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        String errorMessage = task.getException().getMessage();
                                        Toast.makeText(registration_page.this, "Registration failed: " + errorMessage, Toast.LENGTH_SHORT).show();                                    }
                                }
                            });
                }
            }
        });
    }

    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void saveUserDataToDatabase(String userId, String username, String phoneNumber, String email) {
        DatabaseReference userRef = databaseReference.child(userId);
        userRef.child("name").setValue(username);
        userRef.child("Number").setValue(phoneNumber);
        userRef.child("email").setValue(email);
    }
}
