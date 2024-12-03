package com.example.tiffincart.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiffincart.Model.TiffinItem;
import com.example.tiffincart.R;
import com.example.tiffincart.Screen.TiffinDetailsActivity;

import java.util.List;

public class TiffinAdapter extends RecyclerView.Adapter<TiffinAdapter.TiffinViewHolder>{

    private List<TiffinItem> tiffinItems;

    public TiffinAdapter(List<TiffinItem> tiffinItems) {
        this.tiffinItems = tiffinItems;
    }

    @NonNull
    @Override
    public TiffinAdapter.TiffinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tiffin, parent, false);
        return new TiffinViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TiffinAdapter.TiffinViewHolder holder, int position) {
        TiffinItem item = tiffinItems.get(position);
        holder.tiffinName.setText(item.getName());
        holder.tiffinImage.setImageResource(item.getImageResId());
        holder.tiffinPrice.setText("$ " + item.getPrice());
        holder.restaurant.setText(item.getRestaurant());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), TiffinDetailsActivity.class);
            intent.putExtra("name", item.getName());
            intent.putExtra("description", item.getDescription());
            intent.putExtra("price", item.getPrice());
            intent.putExtra("calories", item.getCalories());
            intent.putExtra("ingredients", item.getIngredients());
            intent.putExtra("restaurant", item.getRestaurant());
            intent.putExtra("imageResId", item.getImageResId());
            holder.itemView.getContext().startActivity(intent);
        });
        holder.addButton.setOnClickListener(v -> {
            Intent intent = new Intent(holder.addButton.getContext(), TiffinDetailsActivity.class);
            intent.putExtra("name", item.getName());
            intent.putExtra("description", item.getDescription());
            intent.putExtra("price", item.getPrice());
            intent.putExtra("calories", item.getCalories());
            intent.putExtra("ingredients", item.getIngredients());
            intent.putExtra("restaurant", item.getRestaurant());
            intent.putExtra("imageResId", item.getImageResId());
            holder.addButton.getContext().startActivity(intent);
        });


    }


    @Override
    public int getItemCount() {
        return tiffinItems.size();
    }

    public static class TiffinViewHolder extends RecyclerView.ViewHolder {
        ImageView tiffinImage;
        TextView tiffinName;
        TextView tiffinPrice, restaurant;
        Button addButton;


        public TiffinViewHolder(@NonNull View itemView) {
            super(itemView);
            tiffinImage = itemView.findViewById(R.id.tiffinImage);
            tiffinName = itemView.findViewById(R.id.tiffinName);
            restaurant = itemView.findViewById(R.id.textViewSubTitle);
            tiffinPrice = itemView.findViewById(R.id.textView2);
            addButton = itemView.findViewById(R.id.btnCart);
        }
    }
}


