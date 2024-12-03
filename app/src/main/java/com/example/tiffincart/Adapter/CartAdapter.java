package com.example.tiffincart.Adapter;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiffincart.Model.TiffinItem;
import com.example.tiffincart.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private final List<TiffinItem> cartItems;
    private final SharedPreferences sharedPreferences;
    private final OnItemDeletedListener deleteListener;

    public CartAdapter(List<TiffinItem> cartItems, SharedPreferences sharedPreferences, OnItemDeletedListener deleteListener) {
        this.cartItems = cartItems;
        this.sharedPreferences = sharedPreferences;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TiffinItem item = cartItems.get(position);
        holder.itemName.setText(item.getName());
        holder.itemPlan.setText(item.getPlan());
        holder.itemPrice.setText(item.getPrice());
        holder.cartItemImage.setImageResource(item.getImageResId());

        holder.deleteItemButton.setOnClickListener(v -> {
            deleteListener.onItemDeleted(position);
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cartItemImage;
        TextView itemName, itemPlan, itemPrice;
        ImageButton deleteItemButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cartItemImage = itemView.findViewById(R.id.cartItemImage);
            itemName = itemView.findViewById(R.id.itemName);
            itemPlan = itemView.findViewById(R.id.itemPlan);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            deleteItemButton = itemView.findViewById(R.id.deleteItemButton);
        }
    }

    public interface OnItemDeletedListener {
        void onItemDeleted(int position);
    }
}
