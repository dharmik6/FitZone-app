package com.example.fitzone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.ViewHolder> {

    private List<WorkoutItem> workoutItems;
    private Context context;

    public WorkoutAdapter(Context context, List<WorkoutItem> workoutItems) {
        this.context = context;
        this.workoutItems = workoutItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_workout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WorkoutItem item = workoutItems.get(position);
        holder.workoutNameTextView.setText(item.getWorkoutName());
        holder.focusAreaTextView.setText(item.getFocusArea());
        holder.workoutImageView.setImageResource(item.getWorkoutImageResourceId());
    }

    @Override
    public int getItemCount() {
        return workoutItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView workoutNameTextView;
        TextView focusAreaTextView;
        ImageView workoutImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            workoutNameTextView = itemView.findViewById(R.id.workoutNameTextView);
            focusAreaTextView = itemView.findViewById(R.id.focusAreaTextView);
            workoutImageView = itemView.findViewById(R.id.workoutImageView);
        }
    }
}