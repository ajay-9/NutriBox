package com.example.tiffincart.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiffincart.Model.TiffinItem;
import com.example.tiffincart.R;

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

    }

    @Override
    public int getItemCount() {
        return tiffinItems.size();
    }

    public static class TiffinViewHolder extends RecyclerView.ViewHolder {
        ImageView tiffinImage;
        TextView tiffinName;

        public TiffinViewHolder(@NonNull View itemView) {
            super(itemView);
            tiffinImage = itemView.findViewById(R.id.tiffinImage);
            tiffinName = itemView.findViewById(R.id.tiffinName);
        }
    }
}
