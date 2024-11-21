package com.example.tiffincart.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tiffincart.R;

import com.example.tiffincart.Model.ConsultantItem;

import java.util.List;

public class ConsultantAdapter extends RecyclerView.Adapter<ConsultantAdapter.ConsultantViewHolder>{

    private List<ConsultantItem> consultantItems;

    public ConsultantAdapter(List<ConsultantItem> consultantItems) {
        this.consultantItems = consultantItems;
    }

    @NonNull
    @Override
    public ConsultantAdapter.ConsultantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_consultant, parent, false);
        return new ConsultantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConsultantAdapter.ConsultantViewHolder holder, int position) {
        ConsultantItem item = consultantItems.get(position);
        holder.consultantName.setText(item.getName());
        holder.consultantImage.setImageResource(item.getImageResId());

    }

    @Override
    public int getItemCount() {
        return consultantItems.size();
    }

    public static class ConsultantViewHolder extends RecyclerView.ViewHolder {
        ImageView consultantImage;
        TextView consultantName;

        public ConsultantViewHolder(@NonNull View itemView) {
            super(itemView);
            consultantImage = itemView.findViewById(R.id.consultantImage);
            consultantName = itemView.findViewById(R.id.consultantName);
        }
    }
}
