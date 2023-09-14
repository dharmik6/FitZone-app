package com.example.fitzone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.shawnlin.numberpicker.NumberPicker;

public class weight_page extends AppCompatActivity {
    ImageView backButton;
    NumberPicker weight_num ;
    Button next_page ;
    Integer weight ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_page);
        weight_num = findViewById(R.id.weight);
        next_page = findViewById(R.id.btn_continue);
        backButton = findViewById(R.id.btn_next_page);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }

        });
        weight=60 ;

        weight_num.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if(weight_num==null)
                {
                    weight = 150 ;
                }
                else{
                    weight = newVal;
                }



            }
        });

        next_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(weight_page.this,"Your weight is "+weight, Toast.LENGTH_SHORT).show();
                Intent height = new Intent(weight_page.this,goal_page.class);
               startActivity(height);

            }
        });
    }
}