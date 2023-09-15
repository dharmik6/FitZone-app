package com.example.fitzone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class intro_page extends AppCompatActivity {
    Button admin , user , trainer ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_page);

        admin = findViewById(R.id.btn_admin);
        user = findViewById(R.id.btn_user);
        user = findViewById(R.id.btn_user);
        trainer = findViewById(R.id.btn_trainer);

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iadmin = new Intent(intro_page.this , admin_login.class);
                startActivity(iadmin);
            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iuser = new Intent(intro_page.this , login_page.class);
                startActivity(iuser);
            }
        });
        trainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent iadmin = new Intent(intro_page.this , admin_login.class);
//                startActivity(iadmin);
            }
        });
    }
}