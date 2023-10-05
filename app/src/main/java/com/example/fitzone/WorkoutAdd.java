package com.example.fitzone;

import android.app.ProgressDialog;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class WorkoutAdd extends AppCompatActivity {

    EditText work_name , focuse_work , description ;
    Button add_work ,add_work_image;
    ImageView work_image ;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri;
    private StorageReference storageReference;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_add);

        work_name = findViewById(R.id.add_work_name);
        focuse_work = findViewById(R.id.add_focus_area);
        description = findViewById(R.id.add_workout_desc);
        add_work = findViewById(R.id.btn_work_add);
        work_image = findViewById(R.id.add_work_image);
        add_work_image = findViewById(R.id.btn_add_work_image);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("workouts");
        storageReference = FirebaseStorage.getInstance().getReference().child("workout_images");

        add_work_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, PICK_IMAGE_REQUEST);
            }
        });
//***********************
        add_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a ProgressDialog
                ProgressDialog pd = new ProgressDialog(WorkoutAdd.this);
                pd.setMessage("Loading user data");
                pd.setCancelable(false);

                // Show the ProgressDialog
                pd.show();

                // Get the values from EditText fields
                String workoutName = work_name.getText().toString();
                String focusArea = focuse_work.getText().toString();
                String workoutDescription = description.getText().toString();

                // Check if all fields are filled
                if (!workoutName.isEmpty() && !focusArea.isEmpty() && !workoutDescription.isEmpty() && selectedImageUri != null) {
                    // Create a reference for the image file in Firebase Storage
                    StorageReference imageRef = storageReference.child(selectedImageUri.getLastPathSegment());

                    // Upload the image to Firebase Storage
                    UploadTask uploadTask = imageRef.putFile(selectedImageUri);

                    // Listen for the completion of the upload task
                    uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {
                                // Get the download URL for the uploaded image
                                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        // Use uri.toString() to get the image URL
                                        String imageUrl = uri.toString();

                                        // Create a WorkoutItem object
                                       WorkoutItem workoutItem = new WorkoutItem(workoutName, focusArea, workoutDescription, imageUrl);

                                        // Push the workout data to the database
                                        databaseReference.push().setValue(workoutItem);
                                        startActivity(new Intent(WorkoutAdd.this, WorkoutList.class));
                                        Toast.makeText(WorkoutAdd.this, "Workout added", Toast.LENGTH_SHORT).show();
                                        pd.dismiss();
                                    }
                                });
                            } else {
                                // Handle the upload failure
                                Toast.makeText(WorkoutAdd.this, "Image upload failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    pd.dismiss();
                    Toast.makeText(WorkoutAdd.this, "Please fill in all fields and select an image", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageView back_page = findViewById(R.id.btn_next_page);
        back_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();

            try {
                // Load the selected image into the ImageView
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                work_image.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}