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

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<UserItem> userItems;
    private Context context;

    public UserAdapter(Context context, List<UserItem> userItems) {
        this.context = context;
        this.userItems = userItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserItem item = userItems.get(position);
        holder.userNameTextView.setText(item.getUserName());
        holder.userImageView.setImageResource(item.getUserImageResourceId());
    }

    @Override
    public int getItemCount() {
        return userItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userNameTextView;
        ImageView userImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            userNameTextView = itemView.findViewById(R.id.userNameTextView);
            userImageView = itemView.findViewById(R.id.userImageView);
        }
    }
}