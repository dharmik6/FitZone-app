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
    private WorkoutAdapter.OnItemClickListener onItemClickListener;

    private DatabaseReference databaseReference;
    private int adapterPosition;

    public WorkoutAdapter(Context context, List<WorkoutItem> workoutItems) {
        this.context = context;
        this.workoutItems = workoutItems;

    }

    public WorkoutAdapter(View view) {
    }


    public WorkoutAdapter(String workName, String focusArea, String workoutDesc, String toString) {
    }

    public void setAdapterPosition(int adapterPosition) {
        this.adapterPosition = adapterPosition;
    }

    public int getAdapterPosition() {
        return adapterPosition;
    }
    public interface OnItemClickListener {
        void onItemClick(int position, WorkoutItem item);
    }

    public void setOnItemClickListener(WorkoutAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener; // Step 2: Set the item click listener
    }

    @NonNull
    @Override
    public WorkoutAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_workout, parent, false);
        return new WorkoutAdapter.ViewHolder(view);
    }
    public void onBindViewHolder(@NonNull WorkoutAdapter.ViewHolder holder, int position) {
        WorkoutItem item = workoutItems.get(position);
        holder.workoutNameTextView.setText(item.getWorkoutName());

        // Load user image from Firebase using Glide
        String imageUrl = item.getWorkoutImageResourceId(); // Assuming you have a method to get the image URL from your UserItem class
        Glide.with(context)
                .load(imageUrl) // Load image URL
                .into(holder.workoutImageView); // Set the loaded image to the ImageView

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    WorkoutItem item = workoutItems.get(position);
                    String workoutname = item.getWorkoutName();
                    String focus = item.getWorkoutFocusArea();
                    String workimage = item.getWorkoutImageResourceId();

                    Intent intent1 = new Intent(context, WorkoutData.class);
                    intent1.putExtra("workoutname", workoutname);
                    intent1.putExtra("focus", focus);
                    intent1.putExtra("image", workimage);

                    context.startActivity(intent1);
                } else {
                    // Handle the case where the position is invalid or the view holder is detached.
                    // You can log an error or display a message to the user.
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        if (workoutItems != null) {
            return workoutItems.size(); // Return the size of the list if it's not null
        } else {
            return 0; // Return 0 if the list is null
        }
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                        onItemClickListener.onItemClick(position, workoutItems.get(position));
                    }
                }
            });
        }
    }




}