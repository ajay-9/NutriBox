package com.example.tiffincart.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tiffincart.DAO.ConsultantBooking;
import com.example.tiffincart.DAO.ConsultantBookingDao;
import com.example.tiffincart.DAO.MealOrder;
import com.example.tiffincart.DAO.OrderDao;

@Database(entities = {MealOrder.class, ConsultantBooking.class}, version = 2, exportSchema = false)
public abstract class TiffinCareDatabase extends RoomDatabase {

    // Define DAOs
    public abstract OrderDao mealOrderDao();
    public abstract ConsultantBookingDao consultantBookingDao();

    // Singleton instance
    private static volatile TiffinCareDatabase INSTANCE;

    // Method to get the database instance
    public static TiffinCareDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TiffinCareDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    TiffinCareDatabase.class, "tiffin_care_db")
                            .fallbackToDestructiveMigration() // Handles version migrations
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

