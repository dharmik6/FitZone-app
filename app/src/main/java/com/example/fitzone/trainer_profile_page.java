package com.example.fitzone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class trainer_profile_page extends AppCompatActivity {
    Button btn_trimage ;

    private ImageView tr_profileImageView;
    private TextView trNameTextView, trAgeTextView, trGenderTextView, trPoneTextView, trWeightTextView;
    private static final int PICK_IMAGE_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_profile_page);
        tr_profileImageView = findViewById(R.id.tr_profile_img);
        trNameTextView = findViewById(R.id.trainer_name);
        trAgeTextView = findViewById(R.id.trainer_age);
        trGenderTextView = findViewById(R.id.trainer_gender);
        trPoneTextView = findViewById(R.id.trainer_phone);

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
        // Assuming `userId` is the unique identifier for the user
        String trainerId = currentUser.getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("trainer").child(trainerId);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve user data
                    String name = dataSnapshot.child("name").getValue(String.class);
                    int age = dataSnapshot.child("age").getValue(Integer.class);
                    String gender = dataSnapshot.child("gender").getValue(String.class);
                    int phone = dataSnapshot.child("phone").getValue(Integer.class);
                    String imageUrl = dataSnapshot.child("img").getValue(String.class);

                    // Set user data to UI elements
                    trNameTextView.setText(name);
                    trAgeTextView.setText(String.valueOf(age));
                    trGenderTextView.setText(gender);
                    trPoneTextView.setText(String.valueOf(phone));


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
}
