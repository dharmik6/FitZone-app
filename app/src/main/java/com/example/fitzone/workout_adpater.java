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
import java.util.List;

public class workout_adpater extends RecyclerView.Adapter<workout_adpater.ViewHolder> {

    private List<workout_item> workoutItems;
    private Context context;

    public workout_adpater(Context context, List<workout_item> workoutItems) {
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
        workout_item currentItem = workoutItems.get(position);
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
                workout_item item = workoutItems.get(position);
                if (position != RecyclerView.NO_POSITION) {
                    String workoutname = item.getWorkoutName();
                    String workoutesc = item.getWorkoutDescription();
                    String focus = item.getWorkoutFocusArea();
                    Intent intent = new Intent(context, workout_data.class);
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

    @Override
    public int getItemCount() {
        return workoutItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView workoutNameTextView;
        ImageView workoutImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            workoutNameTextView = itemView.findViewById(R.id.workoutNameTextView);
            workoutImageView = itemView.findViewById(R.id.workoutImageView);
        }
    }
}
