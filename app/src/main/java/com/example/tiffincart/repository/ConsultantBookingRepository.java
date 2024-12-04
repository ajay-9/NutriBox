package com.example.tiffincart.repository;

import android.app.Application;
import android.util.Log;

import com.example.tiffincart.DAO.ConsultantBooking;
import com.example.tiffincart.DAO.ConsultantBookingDao;
import com.example.tiffincart.Database.TiffinCareDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConsultantBookingRepository {
    private ConsultantBookingDao bookingDao;

    public ConsultantBookingRepository(Application application) {

        TiffinCareDatabase db = TiffinCareDatabase.getDatabase(application);
        bookingDao = db.consultantBookingDao();
    }

    // Delete a booking
    public void deleteBooking(ConsultantBooking booking) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> bookingDao.delete(booking));
    }

    // Insert a new booking
    public void insertBooking(ConsultantBooking booking) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            bookingDao.insert(booking);
            Log.d("Database Insert", "Booking inserted: " + booking.toString());
        });
    }

    // Get all bookings for a specific user
    public List<ConsultantBooking> getBookingsForUser(String username) {
        return bookingDao.getBookingsByUser(username);
    }
}
