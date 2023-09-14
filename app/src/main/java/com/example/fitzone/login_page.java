package com.example.fitzone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class login_page extends AppCompatActivity {

    EditText email, password ;
    Button new_account, sign_in ;
    ImageView back_page;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        back_page = findViewById(R.id.btn_next_page);
        sign_in = findViewById(R.id.sign_in);
        email = findViewById(R.id.txt_email);
        password = findViewById(R.id.txt_password);
        new_account = findViewById(R.id.btn_new_account);

        //----------------- new account ------------------

        new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registration_page = new Intent(login_page.this, com.example.fitzone.registration_page.class);
                startActivity(registration_page);
            }
        });

      //------------------ back Button ------------------
        back_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


      //------------sign in button----------------
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //*******************************
                // start validation
                if  (TextUtils.isEmpty(email.getText().toString()) )
                {
                    email.setError("Email is compulsary");
                }
                if (TextUtils.isEmpty(password.getText().toString()))
                {
                    password.setError("Password is compulsary");
                }
                else {
                    // inten in age page page
                    Intent login_page = new Intent(login_page.this, com.example.fitzone.gender_page.class);
                    startActivity(login_page);
                    //***********************************
                    //end validation
                }
            }
        });


    }



    public  void onClickForget_Password()
    {

    }
}
