package com.example.tiffincart.Screen;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiffincart.Adapter.ConsultantBookingAdapter;
import com.example.tiffincart.DAO.ConsultantBooking;
import com.example.tiffincart.R;
import com.example.tiffincart.repository.ConsultantBookingRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyConsultantBookingsActivity extends AppCompatActivity {

    private RecyclerView bookingsRecyclerView;
    private ConsultantBookingAdapter adapter;
    private ConsultantBookingRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_consultant_bookings);

        bookingsRecyclerView = findViewById(R.id.bookingRecyclerView);
        repository = new ConsultantBookingRepository(getApplication());

        // Load bookings from the database
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            String username = prefs.getString("username", "");

            List<ConsultantBooking> bookings = repository.getBookingsForUser(username);
            runOnUiThread(() -> {
                for (ConsultantBooking booking : bookings) {
                    Log.d("Database Data", "Booking: " + booking.toString());
                }
                adapter = new ConsultantBookingAdapter(bookings, repository);
                bookingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                bookingsRecyclerView.setAdapter(adapter);
            });
        });
    }
}
