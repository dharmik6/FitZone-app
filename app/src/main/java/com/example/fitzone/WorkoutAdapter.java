package com.example.fitzone;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.ViewHolder> {

    private List<WorkoutItem> workoutItems;
    private Context context;

    //***********************************
    public WorkoutAdapter(Context context, List<WorkoutItem> workoutItems) {
        this.context = context;
        this.workoutItems = workoutItems;

    }

    public WorkoutAdapter(String workName, String focusArea, String workoutDesc, String toString) {
    }

    @NonNull
    @Override
    public WorkoutAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_workout, parent, false);
        return new WorkoutAdapter.ViewHolder(view);
    }

    public void onBindViewHolder(@NonNull WorkoutAdapter.ViewHolder holder, int position) {
        WorkoutItem currentItem = workoutItems.get(position);
        holder.workoutNameTextView.setText(currentItem.getWorkoutName());


        // Load user image from Firebase using Glide
        String imageUrl = currentItem.getWorkoutImageResourceId();
        Glide.with(context)
                .load(imageUrl)
                .into(holder.workoutImageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                WorkoutItem item = workoutItems.get(position);
                if (position != RecyclerView.NO_POSITION) {
                    String workoutname = item.getWorkoutName();
                    String workoutesc = item.getWorkoutDescription();
                    String focus = item.getWorkoutFocusArea();
                    Intent intent = new Intent(context, WorkoutData.class);
                    intent.putExtra("workoutname", workoutname);
                    intent.putExtra("workoutesc", workoutesc);
                    intent.putExtra("focus", focus);
                    intent.putExtra("workimage", imageUrl);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    // Handle the case where the position is invalid or the view holder is detached.
                    // You can log an error or display a message to the user.
                }
            }
        });
    }

    public int getItemCount() {
        return workoutItems.size();
    }

    //************************************
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