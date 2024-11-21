package com.example.tiffincart.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiffincart.Model.ChefItem;
import com.example.tiffincart.R;

import java.util.List;

public class ChefAdapter extends RecyclerView.Adapter<ChefAdapter.ChefViewHolder> {

    private List<ChefItem> chefItems;

    // Constructor for passing the list of chef items
    public ChefAdapter(List<ChefItem> chefItems) {
        this.chefItems = chefItems;
    }

    @NonNull
    @Override
    public ChefAdapter.ChefViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each chef item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_chef_item, parent, false);
        return new ChefViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChefAdapter.ChefViewHolder holder, int position) {
        // Bind the chef data to the ViewHolder
        ChefItem item = chefItems.get(position);
        holder.chefName.setText(item.getName());
        holder.chefImage.setImageResource(item.getImageResId());
    }

    @Override
    public int getItemCount() {
        return chefItems.size(); // Return the total number of chef items
    }

    // ViewHolder class for holding the views of a chef item
    public static class ChefViewHolder extends RecyclerView.ViewHolder {
        ImageView chefImage;
        TextView chefName;

        public ChefViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize the views
            chefImage = itemView.findViewById(R.id.chefImage);
            chefName = itemView.findViewById(R.id.chefName);
        }
    }
}
