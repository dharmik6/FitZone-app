package com.example.fitzone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class registration_page extends AppCompatActivity {

    EditText name, phone_number, email, password;
    Button sign_up, google;
    CheckBox tnc;
    ImageView backpress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);



// connect with edittext
        name = findViewById(R.id.txt_name);
        phone_number = findViewById(R.id.txt_phone);
        email = findViewById(R.id.txt_email);
        password = findViewById(R.id.txt_password);

        // connect with button
        sign_up = findViewById(R.id.sign_in);
        google = findViewById(R.id.btn_google);

        //connect checkbox
        tnc = findViewById(R.id.tnc);

        backpress = findViewById(R.id.btn_next_page);


        // **********************************************
        //back button
        backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // *******************************
                // start validation of all fileds
                if( TextUtils.isEmpty(name.getText().toString()) )
                {
                    name.setError("name is compulsary");

                }
                if ( TextUtils.isEmpty(phone_number.getText().toString()) )
                {
                    phone_number.setError("Phone number is compulsary");

                }
                if  (TextUtils.isEmpty(email.getText().toString()) )
                {
                    email.setError("Email is compulsary");
                }
                if (TextUtils.isEmpty(password.getText().toString()))
                {
                    password.setError("Password is compulsary");
                }
                else if (!tnc.isChecked() ) {
                    Toast.makeText(registration_page.this, "check agreement ?", Toast.LENGTH_SHORT).show();
                }
                else {
                    // inten in age page page
                    Intent login_page = new Intent(registration_page.this, com.example.fitzone.gender_page.class);
                    startActivity(login_page);
                }
                // end validation
                //********************************
                //********************************
            }
        });
    }
}