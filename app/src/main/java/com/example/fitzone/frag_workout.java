package com.example.fitzone;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class frag_workout extends Fragment {

    private RecyclerView recyclerView;
    private WorkoutAdapter adapter;
    private List<WorkoutItem> workoutItems = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag_workout, container, false);


        // Sample workout items, you can add more items as needed
        workoutItems.add(new WorkoutItem("Workout 1", "Focus Area 1", R.drawable.gym_24));
        workoutItems.add(new WorkoutItem("Workout 2", "Focus Area 2", R.drawable.gym_24));
        workoutItems.add(new WorkoutItem("Workout 3", "Focus Area 3", R.drawable.gym_24));

        recyclerView = view.findViewById(R.id.work_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new WorkoutAdapter(getContext(), workoutItems);
        recyclerView.setAdapter(adapter);

        return view;


    }
}