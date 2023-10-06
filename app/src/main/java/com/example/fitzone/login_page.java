package com.example.fitzone;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class login_page extends AppCompatActivity {

    EditText email, password;
    Button new_account, sign_in;
    ImageView change;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);


        change = findViewById(R.id.change);
        sign_in = findViewById(R.id.sign_in);
        email = findViewById(R.id.txt_email);
        password = findViewById(R.id.txt_password);
        new_account = findViewById(R.id.btn_registration);


        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itrainer = new Intent(login_page.this, trainer_login.class);
                startActivity(itrainer);
            }
        });
        //----------------- new account ------------------

        new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registration_page = new Intent(login_page.this, com.example.fitzone.registration_page.class);
                startActivity(registration_page);
            }
        });




        //------------sign in button----------------
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //*******************************
                // start validation
                if (TextUtils.isEmpty(email.getText().toString())) {
                    email.setError("Email is compulsary");
                }
                if (TextUtils.isEmpty(password.getText().toString())) {
                    password.setError("Password is compulsary");
                } else {
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        SharedPreferences pref = getSharedPreferences("login",MODE_PRIVATE);
                                        SharedPreferences.Editor editor = pref.edit();

                                     editor.putBoolean("flag" ,true);
                                     editor.apply();
                                        // Sign-in successful, navigate to the next page
                                        Intent genderPageIntent = new Intent(login_page.this, com.example.fitzone.home_page.class);
                                        startActivity(genderPageIntent);
                                        finish();
                                    } else {
                                        // Sign-in failed, display an error message
                                        Toast.makeText(login_page.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            });
                    //end validation
                }
            }
        });
    }
}
