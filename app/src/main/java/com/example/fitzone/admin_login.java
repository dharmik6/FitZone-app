package com.example.fitzone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class admin_login extends AppCompatActivity {
    EditText admin_id , admin_password ;
    Button login ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        admin_id = (EditText) findViewById(R.id.txt_admin_id);
        admin_password = (EditText) findViewById(R.id.txt_admin_password);
        login = findViewById(R.id.admin_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent ilogin = new Intent(admin_login.this , admin_home_page.class);
//                startActivity(ilogin);
            }
        });

    }
}