package com.example.tiffincart.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiffincart.Model.CategoryItem;
import com.example.tiffincart.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {


    private List<CategoryItem> categoryItems;

    public CategoryAdapter(List<CategoryItem> categoryItems) {
        this.categoryItems = categoryItems;
    }



    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, int position) {
        CategoryItem item = categoryItems.get(position);
        holder.categoryName.setText(item.getName());
        holder.categoryImage.setImageResource(item.getImageResId());
    }

    @Override
    public int getItemCount() {
        return categoryItems.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {

        ImageView categoryImage;
        TextView categoryName;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.categoryImage);
            categoryName = itemView.findViewById(R.id.categoryName);
        }
    }
}
