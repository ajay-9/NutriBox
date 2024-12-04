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

import com.example.tiffincart.Model.ConsultantItem;
import com.example.tiffincart.R;
import com.example.tiffincart.Screen.ConsultantDetailsActivity;

import java.util.List;

public class AllConsultantAdapter extends RecyclerView.Adapter<AllConsultantAdapter.AllConsultantViewHolder>{
    private List<ConsultantItem> consultantItems;

    public AllConsultantAdapter(List<ConsultantItem> consultantItems) {
        this.consultantItems = consultantItems;
    }

    @NonNull
    @Override
    public AllConsultantAdapter.AllConsultantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_all_consultant, parent, false);
        return new AllConsultantAdapter.AllConsultantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllConsultantAdapter.AllConsultantViewHolder holder, int position) {
        ConsultantItem item = consultantItems.get(position);
        holder.consultantName.setText(item.getName());
        holder.consultantImage.setImageResource(item.getImageResId());
        holder.consultantIntro.setText(item.getProfileInfo());

        holder.bookButton.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), ConsultantDetailsActivity.class);
            intent.putExtra("name", item.getName());
            intent.putExtra("imageResId", item.getImageResId());
            holder.itemView.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return consultantItems.size();
    }

    public class AllConsultantViewHolder extends RecyclerView.ViewHolder {

        ImageView consultantImage;
        TextView consultantName, consultantIntro;
        Button bookButton;

        public AllConsultantViewHolder(@NonNull View itemView) {
            super(itemView);
            consultantImage = itemView.findViewById(R.id.consultantImage);
            consultantName = itemView.findViewById(R.id.consultantName);
            consultantIntro = itemView.findViewById(R.id.consultantIntro);
            bookButton = itemView.findViewById(R.id.bookButton);
        }
    }
}
