package com.example.fitzone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class trainer_login extends AppCompatActivity {
    EditText trainer_email, trainer_password;
    Button  trainer_sign_in;
    ImageView change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_login);



        change = findViewById(R.id.change);
        trainer_sign_in = findViewById(R.id.btn_trainer_sign_in);
        trainer_email = findViewById(R.id.txt_trainer_email);
        trainer_password = findViewById(R.id.txt_trainer_password);


        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itrainer = new Intent(trainer_login.this, login_page.class);
                startActivity(itrainer);
            }
        });




        //------------sign in button----------------
        trainer_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //*******************************
                // start validation
                if (TextUtils.isEmpty(trainer_email.getText().toString())) {
                    trainer_email.setError("Email is compulsary");
                }
                if (TextUtils.isEmpty(trainer_password.getText().toString())) {
                    trainer_password.setError("Password is compulsary");
                } else {
                    Intent genderPageIntent = new Intent(trainer_login.this, com.example.fitzone.trainer_home_page.class);
                    startActivity(genderPageIntent);
                }
            }
        });
    }
}


