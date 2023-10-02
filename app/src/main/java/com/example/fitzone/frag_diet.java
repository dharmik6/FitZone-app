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

public class frag_diet extends Fragment {

    private RecyclerView recyclerView;
    private DietAdapter adapter;
    private List<DietItem> dietItems = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag_diet, container, false);


        // Sample diet items, you can add more items as needed
        dietItems.add(new DietItem("Diet 1", R.drawable.baseline_image_24));
        dietItems.add(new DietItem("Diet 2", R.drawable.baseline_image_24));
        dietItems.add(new DietItem("Diet 3", R.drawable.baseline_image_24));

        recyclerView = view.findViewById(R.id.diet_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new DietAdapter(getContext(), dietItems);
        recyclerView.setAdapter(adapter);

        return view;


    }
}