package com.example.fitzone;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class frag_workout extends Fragment {

    private RecyclerView recyclerView;
    private workout_adpater adapter;
    private List<workout_item> workoutItems = new ArrayList<>();
    DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag_workout, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading..."); // Set the message for the progress dialog
        progressDialog.setCancelable(false);

        // Initialize RecyclerView and its adapter
        recyclerView = view.findViewById(R.id.work_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new workout_adpater(getContext(), workoutItems);
        recyclerView.setAdapter(adapter);

        // Initialize Firebase database reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("workouts");

        // Set up the database listener to fetch workout data
        setDatabaseListener();

        return view;
    }

    private void setDatabaseListener() {
        progressDialog.show();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                workoutItems.clear();

                // Loop through the dataSnapshot and retrieve workout data
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String workName = dataSnapshot.child("workoutName").getValue(String.class);
                    String focus = dataSnapshot.child("workoutFocusArea").getValue(String.class);
                    String imageUrl = dataSnapshot.child("workoutImageResourceId").getValue(String.class);
                    String workDesc = dataSnapshot.child("workoutDescription").getValue(String.class);

                    if (workName != null && imageUrl != null && workDesc != null) {
                        // Create WorkoutItem objects and add them to the workoutItems list
                        workout_item workoutItem = new workout_item(workName, focus, workDesc, imageUrl);
                        workoutItems.add(workoutItem);
                    }
                }

                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
                Toast.makeText(getContext(), "Database Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}
