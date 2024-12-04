package com.example.tiffincart.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiffincart.DAO.MealOrder;
import com.example.tiffincart.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private final List<MealOrder> orders;

    public OrderAdapter(List<MealOrder> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        MealOrder order = orders.get(position);
        holder.mealNameTxt.setText(order.getMealName());
        holder.mealTypeTxt.setText(order.getMealType());
        holder.priceTxt.setText(String.format("$%.2f", order.getPrice()));

        if (order.getMealType().equalsIgnoreCase("weekly") || order.getMealType().equalsIgnoreCase("monthly")) {
            holder.endDateTxt.setVisibility(View.VISIBLE);
            holder.endDateTxt.setText("Ends: " + order.getEndDate());
        } else {
            holder.endDateTxt.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView mealNameTxt, mealTypeTxt, priceTxt, endDateTxt;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            mealNameTxt = itemView.findViewById(R.id.mealNameTxt);
            mealTypeTxt = itemView.findViewById(R.id.mealTypeTxt);
            priceTxt = itemView.findViewById(R.id.priceTxt);
            endDateTxt = itemView.findViewById(R.id.endDateTxt);
        }
    }
}
