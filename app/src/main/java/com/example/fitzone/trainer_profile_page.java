package com.example.fitzone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class trainer_profile_page extends AppCompatActivity {
    Button btn_trimage ;

    private ImageView tr_profileImageView;
    private TextView trNameTextView, trAgeTextView, trGenderTextView, trPoneTextView, trEmailTextView;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final String STORAGE_PATH = "trainer_images/"; // Firebase Storage path
    private Uri selectedImageUri;
    private FirebaseStorage storage;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_profile_page);
        tr_profileImageView = findViewById(R.id.tr_profile_img);
        trNameTextView = findViewById(R.id.trainer_name);
        trAgeTextView = findViewById(R.id.trainer_age);
        trGenderTextView = findViewById(R.id.trainer_gender);
        trPoneTextView = findViewById(R.id.trainer_phone);
        trEmailTextView = findViewById(R.id.trainer_email);
        tr_profileImageView=findViewById(R.id.tr_profile_img);

        storage = FirebaseStorage.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("trainers");

        btn_trimage = findViewById(R.id.btn_trimage);

        btn_trimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iuser = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iuser,PICK_IMAGE_REQUEST);
            }
        });
        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading user data");
        pd.setCancelable(false);
        pd.show();

        // Retrieve and set the user's name, email, and profile image from Firebase Realtime Database
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        // Assuming userId is the unique identifier for the user
        String uid = currentUser.getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("trainers").child(uid);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve user data
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String phone = dataSnapshot.child("Number").getValue(String.class);
                    String age = dataSnapshot.child("age").getValue(String.class);
                    String gender = dataSnapshot.child("gender").getValue(String.class);
                    String imageUrl = dataSnapshot.child("img").getValue(String.class);

                    // Set user data to UI elements
                    trNameTextView.setText(name);
                    trEmailTextView.setText(email);
                    trPoneTextView.setText(phone);
                    trAgeTextView.setText(age);
                    trGenderTextView.setText(gender);

                    // Load user profile image into ImageView using Glide

                    Glide.with(trainer_profile_page.this)
                            .load(imageUrl)
                            .placeholder(R.drawable.baseline_image_24) // Placeholder image while loading
                            .error(R.drawable.baseline_image_24) // Image to show if loading fails
                            .into(tr_profileImageView);
                }
                // Dismiss the progress dialog after loading data
                pd.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database errors
                pd.dismiss();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();

            // Generate a unique key for the image file
            String imageKey = databaseReference.push().getKey();

            // Create a storage reference for the image
            StorageReference imageRef = storage.getReference().child(STORAGE_PATH + imageKey);

            // Upload the image to Firebase Storage
            imageRef.putFile(selectedImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Image upload successful, now get the download URL
                            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri downloadUri) {
                                    // Store the download URL in the Realtime Database
                                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                                    if (currentUser != null) {
                                        String uid = currentUser.getUid();
                                        databaseReference.child(uid).child("img").setValue(downloadUri.toString());
                                    }

                                    // Display a success message to the user
                                    Toast.makeText(trainer_profile_page.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();

                                    // Load the selected image into the ImageView
                                    Glide.with(trainer_profile_page.this)
                                            .load(downloadUri) // Use the download URI of the uploaded image
                                            .placeholder(R.drawable.baseline_image_24) // Placeholder image while loading
                                            .error(R.drawable.baseline_image_24) // Image to show if loading fails
                                            .into(tr_profileImageView);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle image upload failure
                            Toast.makeText(trainer_profile_page.this, "Image upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

}