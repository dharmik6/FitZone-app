package com.example.fitzone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class DietAdd extends AppCompatActivity {

    EditText diet_name ,description ;
    Button add_diet , add_diet_image;
    ImageView diet_image ;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri;
    DatabaseReference databaseReference;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_add);
        diet_name = findViewById(R.id.add_diet_name);// workout name

        description = findViewById(R.id.add_diet_desc);// description of workout
        add_diet = findViewById(R.id.btn_diet_add);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("diets");
        storageReference = FirebaseStorage.getInstance().getReference().child("diet_images");

        diet_image = findViewById(R.id.add_diet_image);
        add_diet_image = findViewById(R.id.import_diet_image);
        //*************************************
        //import diet image
        add_diet_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, PICK_IMAGE_REQUEST);
            }
        });
        //**********************************
        //add button click event
        add_diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get the values from EditText fields
                String dietName = diet_name.getText().toString();
                String dietDescription = description.getText().toString();

                // Check if all fields are filled
                if (!dietName.isEmpty() && !dietDescription.isEmpty() && selectedImageUri != null) {
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
                                        DietItem dietItem = new DietItem(dietName, imageUrl, dietDescription);

                                        // Push the workout data to the database
                                        databaseReference.push().setValue(dietItem);
                                        startActivity(new Intent(DietAdd.this, DietList.class));
                                        Toast.makeText(DietAdd.this, "Diet added", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                // Handle the upload failure
                                Toast.makeText(DietAdd.this, "Image upload failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(DietAdd.this, "Please fill in all fields and select an image", Toast.LENGTH_SHORT).show();
                }
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
                diet_image.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}