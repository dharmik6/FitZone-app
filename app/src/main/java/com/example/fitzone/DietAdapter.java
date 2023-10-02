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
        DietItem item = dietItems.get(position);
        holder.dietNameTextView.setText(item.getDietName());
        holder.dietImageView.setImageResource(item.getDietImageResourceId());
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