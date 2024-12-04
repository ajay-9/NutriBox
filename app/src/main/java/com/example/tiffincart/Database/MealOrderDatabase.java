package com.example.tiffincart.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tiffincart.DAO.MealOrder;
import com.example.tiffincart.DAO.OrderDao;

@Database(entities = {MealOrder.class}, version = 1)
public abstract class MealOrderDatabase extends RoomDatabase {

    public abstract OrderDao orderDao();

    private static MealOrderDatabase instance;

    public static synchronized MealOrderDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            MealOrderDatabase.class, "meal_order_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
