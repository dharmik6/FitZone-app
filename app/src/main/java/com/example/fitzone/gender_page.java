package com.example.fitzone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

public class gender_page extends AppCompatActivity {
    ImageView male,female;
    boolean isSelected,isready;
   ImageView backButton;
   Button next_page ;
    String gender;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender_page);

        male = findViewById(R.id.btn_male);
        female = findViewById(R.id.btn_female);
        backButton = findViewById(R.id.btn_next_page);
        next_page = findViewById(R.id.btn_continue);



        isready = false ;

        //*******************************************************
        //back page
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSelected) {
                    male.setImageResource(R.drawable.male_checked);
                    female.setImageResource(R.drawable.female);
                    isSelected = true;
                    isready = true ;
                    gender = "male" ;
                } else {
                    male.setImageResource(R.drawable.male);
                    isSelected = false;
                    isready = false ;
                    gender = null ;
                }
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSelected) {
                    male.setImageResource(R.drawable.male);
                    female.setImageResource(R.drawable.female_checkd);
                    isSelected = false;
                    isready = true ;
                    gender = "female" ;
                } else {
                    female.setImageResource(R.drawable.female);
                    isSelected = true;
                    isready = false ;
                    gender = null ;
                }
            }
        });



        next_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(gender==null)
                {
                    Toast.makeText(gender_page.this, "plese selece gender", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent nextPage =new Intent(gender_page.this, com.example.fitzone.age_page.class);
                    startActivity(nextPage);
                    Toast.makeText(gender_page.this, gender+" selected", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


}