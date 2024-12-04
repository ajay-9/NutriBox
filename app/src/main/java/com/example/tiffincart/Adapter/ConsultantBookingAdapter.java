package com.example.tiffincart.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiffincart.DAO.ConsultantBooking;
import com.example.tiffincart.R;
import com.example.tiffincart.repository.ConsultantBookingRepository;

import java.util.List;

public class ConsultantBookingAdapter extends RecyclerView.Adapter<ConsultantBookingAdapter.ViewHolder> {

    private List<ConsultantBooking> bookingList;
    private ConsultantBookingRepository repository;

    public ConsultantBookingAdapter(List<ConsultantBooking> bookingList, ConsultantBookingRepository repository) {
        this.bookingList = bookingList;
        this.repository = repository;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_consultant_booking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ConsultantBooking booking = bookingList.get(position);

        holder.consultantName.setText(booking.getConsultantName());
        holder.meetingDate.setText("Meeting Date: " + booking.getMeetingDate());

        holder.cancelButton.setOnClickListener(v -> {
            // Remove booking from database
            repository.deleteBooking(booking);

            // Remove from list and notify adapter
            bookingList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, bookingList.size());
        });
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView consultantName, meetingDate;
        Button cancelButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            consultantName = itemView.findViewById(R.id.consultantName);
            meetingDate = itemView.findViewById(R.id.meetingDate);
            cancelButton = itemView.findViewById(R.id.cancelButton);
        }
    }
}
