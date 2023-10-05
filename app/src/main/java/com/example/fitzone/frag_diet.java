package com.example.fitzone;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class frag_diet extends Fragment {

    //********************
    private RecyclerView recyclerView;
    private DietAdapter adapter;
    private List<DietItem> dietItems = new ArrayList<>();

    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag_diet, container, false);

        recyclerView = view.findViewById(R.id.diet_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new DietAdapter(getContext(), dietItems);
        recyclerView.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("diets");

        setDatabaseListener();

        return view;


    }

    private void setDatabaseListener() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dietItems.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String dietName = dataSnapshot.child("dietName").getValue(String.class);
                    String imageUrl = dataSnapshot.child("imageUrl").getValue(String.class); // Change to "imageUrl"
                    String dietdesc = dataSnapshot.child("dietDescription").getValue(String.class); // Change to "imageUrl"

                    if (dietName != null && imageUrl != null) {
                        DietItem dietItem = new DietItem(dietName, imageUrl);
                        dietItems.add(dietItem);
                    }
                }
                adapter.notifyDataSetChanged(); // Notify the adapter that the data has changed
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });
    }


}