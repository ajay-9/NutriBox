package com.example.tiffincart.DAO;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ConsultantBookingDao {
    @Insert
    void insert(ConsultantBooking booking);

    @Delete
    void delete(ConsultantBooking booking);

    @Query("SELECT * FROM consultant_bookings WHERE username = :username")
    List<ConsultantBooking> getBookingsByUser(String username);

}

