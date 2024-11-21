package com.example.tiffincart.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiffincart.Model.DishItem;
import com.example.tiffincart.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.DishViewHolder> {

    private List<DishItem> dishList;

    public DishAdapter(List<DishItem> dishList) {
        this.dishList = dishList;
    }

    @NonNull
    @Override
    public DishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dish_item, parent, false);
        return new DishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishViewHolder holder, int position) {
        DishItem dish = dishList.get(position);
        holder.dishName.setText(dish.getName());
        holder.dishDescription.setText(dish.getDescription());
        Picasso.get().load(dish.getDishImage()).into(holder.dishImage);
    }

    @Override
    public int getItemCount() {
        return dishList.size();
    }

    public static class DishViewHolder extends RecyclerView.ViewHolder {
        TextView dishName, dishDescription;
        ImageView dishImage;

        public DishViewHolder(View itemView) {
            super(itemView);
            dishName = itemView.findViewById(R.id.dish_name);
            dishDescription = itemView.findViewById(R.id.dish_description);
            dishImage = itemView.findViewById(R.id.dish_image);
        }
    }
}
