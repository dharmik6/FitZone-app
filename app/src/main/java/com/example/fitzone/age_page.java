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


        age_num.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                 age = newVal;

            }
        });

        next_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(age_page.this,"Your Age is "+age, Toast.LENGTH_SHORT).show();
                Intent height = new Intent(age_page.this, height_page.class);
                startActivity(height);

            }
        });


    }



}