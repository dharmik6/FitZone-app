package com.example.fitzone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DietData extends AppCompatActivity {

    ImageView dietImage ;
    TextView dietName , dietDesc ;
    Button update , delete ;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_data);



        dietImage = findViewById(R.id.diet_image);
        dietName = findViewById(R.id.diet_name);
        dietDesc = findViewById(R.id.diet_description);
        update = findViewById(R.id.btn_Update);
        delete = findViewById(R.id.btn_Delete);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(DietData.this, DietEdit.class);
                startActivity(homeIntent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DietData.this, "Record Deleted", Toast.LENGTH_SHORT).show();
                onBackPressed();
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


        // Retrieve the username from the intent
        String dietname = getIntent().getStringExtra("dietname");

        // Initialize the database reference to the specific user
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("diets").child(dietname);
        setDatabaseListener();

        dietName.setText(getIntent().getStringExtra("dietname"));
        dietDesc.setText(getIntent().getStringExtra("dietdesc"));



        String getUserImage = getIntent().getStringExtra("dietimage");

        Glide.with(DietData.this)
                .load(getUserImage) // Load image URL
                .into(dietImage); // Set the loaded image to the ImageView
    }

    private void setDatabaseListener() {
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the dietname from the intent
                final String dietname = getIntent().getStringExtra("dietname");

                // Get reference to the "diets" node in the Firebase database
                DatabaseReference dietsRef = FirebaseDatabase.getInstance().getReference("diets");

                // Query the database to find the specific diet by dietname
                Query query = dietsRef.orderByChild("dietName").equalTo(dietname);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Loop through the dataSnapshot and delete the diet with the specified dietname
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                snapshot.getRef().removeValue()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                // Diet has been successfully deleted from the Realtime Database
                                                Toast.makeText(DietData.this, "Record Deleted", Toast.LENGTH_SHORT).show();
                                                onBackPressed(); // Navigate back after deletion
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Handle errors here
                                                Toast.makeText(DietData.this, "Failed to delete record: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        } else {
                            // Diet with the specified dietname does not exist in the Realtime Database
                            Toast.makeText(DietData.this, "Diet not found in the database", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle any errors here
                        Toast.makeText(DietData.this, "Database Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}