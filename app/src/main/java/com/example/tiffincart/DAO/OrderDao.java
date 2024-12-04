package com.example.tiffincart.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface OrderDao {
    @Insert
    void insertMealOrder(MealOrder mealOrder);

    @Update
    void updateMealOrder(MealOrder mealOrder);

    @Delete
    void deleteMealOrder(MealOrder mealOrder);


    @Query("SELECT * FROM orders WHERE username = :username AND email = :email")
    List<MealOrder> getOrdersForUser(String username, String email);
}
