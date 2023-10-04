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

public class trainer_login extends AppCompatActivity {
    EditText trainer_email, trainer_password;
    Button trainer_sign_in;
    ImageView change;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_login);

        mAuth = FirebaseAuth.getInstance();

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
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    mAuth.signInWithEmailAndPassword(trainer_email.getText().toString(), trainer_password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        SharedPreferences pref = getSharedPreferences("login2",MODE_PRIVATE);
                                        SharedPreferences.Editor editor = pref.edit();

                                        editor.putBoolean("flag2" ,true);
                                        editor.apply();
                                        // Sign-in successful, navigate to the next page
                                        Intent genderPageIntent = new Intent(trainer_login.this, com.example.fitzone.trainer_home_page.class);
                                        startActivity(genderPageIntent);
                                    } else {
                                        // Sign-in failed, display an error message
                                        Toast.makeText(trainer_login.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            });
                    //end validation
                }
            }
        });
    }


}
