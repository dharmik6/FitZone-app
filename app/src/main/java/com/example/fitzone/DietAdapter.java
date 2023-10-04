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

import com.squareup.picasso.Picasso;

import java.util.List;

public class DietAdapter extends RecyclerView.Adapter<DietAdapter.ViewHolder> {

    private List<DietItem> dietItems;
    private Context context;


    public DietAdapter(Context context, List<DietItem> dietItems) {
        this.context = context;
        this.dietItems = dietItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DietItem currentItem = dietItems.get(position);
        if (currentItem != null) {
            String dietName = currentItem.getDietName();
//            String dirtDescription = currentItem.();
            String imageUrl = currentItem.getImageUrl();

            // Check if the values are not null before using them
            if (dietName != null) {
                holder.dietNameTextView.setText(dietName);
            } else {
                holder.dietNameTextView.setText("");
            }

            // Load the image using Picasso
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Picasso.get().load(imageUrl).into(holder.dietImageView);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DietData.class);
                    intent.putExtra("wname", dietName);
//                    intent.putExtra("wdes", dirtDescription);
                    // Pass the URL or other identifier for the image, which can be loaded in WorkoutData activity
                    intent.putExtra("imag", imageUrl);
                    // Add the FLAG_ACTIVITY_NEW_TASK flag
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
//        DietItem item = dietItems.get(position);
//        holder.dietNameTextView.setText(item.getDietName());
//        holder.dietImageView.setImageResource(Integer.parseInt(item.getDietImageResourceId()));
    }

    @Override
    public int getItemCount() {
        return dietItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dietNameTextView;
        ImageView dietImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            dietNameTextView = itemView.findViewById(R.id.dietNameTextView);
            dietImageView = itemView.findViewById(R.id.dietImageView);
        }
    }
}