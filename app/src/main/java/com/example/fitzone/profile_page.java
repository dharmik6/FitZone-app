package com.example.fitzone;



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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

public class profile_page extends AppCompatActivity {
    Button btn_image ;

    private ImageView profileImageView;
    private TextView userNameTextView, userAgeTextView, userGenderTextView, userHeightTextView, userWeightTextView;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final String STORAGE_PATH = "profile_images/"; // Firebase Storage path
    private Uri selectedImageUri;
    private StorageReference storageReference;

    Button profile ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        profileImageView = findViewById(R.id.profile_img);
        userNameTextView = findViewById(R.id.user_name);
        userAgeTextView = findViewById(R.id.user_age);
        userGenderTextView = findViewById(R.id.user_gender);
        userHeightTextView = findViewById(R.id.user_height);
        userWeightTextView = findViewById(R.id.user_weight);
        btn_image = findViewById(R.id.btn_Uimage);
        profile = findViewById(R.id.updt_profile);

        storageReference = FirebaseStorage.getInstance().getReference();

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the image picker
                Intent iuser = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iuser, PICK_IMAGE_REQUEST);
            }
        });
        btn_image.setOnClickListener(new View.OnClickListener() {
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
        // Assuming `userId` is the unique identifier for the user
        String userId = currentUser.getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve user data
                    String name = dataSnapshot.child("name").getValue(String.class);
                    int age = dataSnapshot.child("age").getValue(Integer.class);
                    String gender = dataSnapshot.child("gender").getValue(String.class);
                    int height = dataSnapshot.child("height").getValue(Integer.class);
                    int weight = dataSnapshot.child("weight").getValue(Integer.class);
                    String imageUrl = dataSnapshot.child("img").getValue(String.class);

                    // Set user data to UI elements
                    userNameTextView.setText(name);
                    userAgeTextView.setText(String.valueOf(age));
                    userGenderTextView.setText(gender);
                    userHeightTextView.setText(String.valueOf(height));
                    userWeightTextView.setText(String.valueOf(weight));

                    // Load user profile image into ImageView using Glide

                    Glide.with(profile_page.this)
                            .load(imageUrl)
                            .placeholder(R.drawable.baseline_image_24) // Placeholder image while loading
                            .error(R.drawable.baseline_image_24) // Image to show if loading fails
                            .into(profileImageView);

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
            // Now you have the URI of the selected image
            // You can display it or upload it to Firebase Storage here
            uploadImageToFirebaseStorage();
        }
    }

    private void uploadImageToFirebaseStorage() {
        if (selectedImageUri != null) {
            final ProgressDialog pd = new ProgressDialog(this);
            pd.setMessage("Uploading image");
            pd.setCancelable(false);
            pd.show();

            // Create a reference to the Firebase Storage location where the image will be stored
            final StorageReference imageRef = storageReference.child(STORAGE_PATH + System.currentTimeMillis() + ".jpg");

            // Upload the image to Firebase Storage
            imageRef.putFile(selectedImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Image uploaded successfully, get the download URL
                            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri downloadUri) {
                                    // Save the download URL to the Realtime Database
                                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                                    String userId = currentUser.getUid();
                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("img");
                                    databaseReference.setValue(downloadUri.toString());

                                    // Load the user's new profile image into ImageView using Glide
                                    Glide.with(profile_page.this)
                                            .load(downloadUri)
                                            .placeholder(R.drawable.baseline_image_24)
                                            .error(R.drawable.baseline_image_24)
                                            .into(profileImageView);

                                    // Dismiss the progress dialog
                                    pd.dismiss();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle the error
                            pd.dismiss();
                            Toast.makeText(profile_page.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
