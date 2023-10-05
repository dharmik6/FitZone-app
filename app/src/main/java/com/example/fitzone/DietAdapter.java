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

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DietItem currentItem = dietItems.get(position);
        holder.dietNameTextView.setText(currentItem.getDietName());


        // Load user image from Firebase using Glide
        String imageUrl = currentItem.getDietImageResourceId();
        Glide.with(context)
                .load(imageUrl)
                .into(holder.dietImageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                DietItem item = dietItems.get(position);
                if (position != RecyclerView.NO_POSITION) {
                    String dietname = item.getDietName();
                    String dietdesc = item.getDietDescription();
                    Intent intent = new Intent(context, DietData.class);
                    intent.putExtra("dietname", dietname);
                    intent.putExtra("dietdesc", dietdesc);
                    intent.putExtra("dietimage", imageUrl);
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
        return dietItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dietNameTextView;

        ImageView dietImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            dietNameTextView = itemView.findViewById(R.id.dietNameTextView);
             // Initialize TextView
            dietImageView = itemView.findViewById(R.id.dietImageView);
        }
    }
}