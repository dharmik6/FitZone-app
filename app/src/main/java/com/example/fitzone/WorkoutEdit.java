package com.example.fitzone;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WorkoutEdit extends AppCompatActivity {
    Button submit,btn_change_work_image;
    ImageView changework_image ;
    EditText change_work_name,change_focus_area,change_workout_desc;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_edit);

        change_work_name  = findViewById(R.id.change_work_name);
        change_focus_area  = findViewById(R.id.change_focus_area);
        change_workout_desc  = findViewById(R.id.change_diet_desc);

        //**************************************************
        btn_change_work_image  = findViewById(R.id.btn_change_work_image);
        changework_image = findViewById(R.id.change_work_image);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference workoutRef = database.getReference("workouts");

        btn_change_work_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, PICK_IMAGE_REQUEST);
            }
        });
        submit = findViewById(R.id.btn_work_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String workName = change_work_name.getText().toString();
                String focusArea = change_focus_area.getText().toString();
                String workoutDesc = change_workout_desc.getText().toString();

                if (selectedImageUri != null) {
                    // Assuming you have a Workout class to store data
                    WorkoutAdapter workout = new WorkoutAdapter(workName, focusArea, workoutDesc, selectedImageUri.toString());

                    // Push the workout data to the database
                    workoutRef.push().setValue(workout);

                    Toast.makeText(WorkoutEdit.this, "Record Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(WorkoutEdit.this, "Please select an image", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(WorkoutEdit.this, "Record Updated", Toast.LENGTH_SHORT).show();
            }
        });

        //**********************************
        //back page button
        ImageView back_page = findViewById(R.id.btn_next_page);
        back_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();

            try {
                // Load the selected image into the ImageView
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                changework_image.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}