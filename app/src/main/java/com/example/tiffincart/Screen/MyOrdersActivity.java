package com.example.tiffincart.Screen;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiffincart.Adapter.OrderAdapter;
import com.example.tiffincart.Database.MealOrderDatabase;
import com.example.tiffincart.DAO.MealOrder;
import com.example.tiffincart.R;

import java.util.List;

public class MyOrdersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        RecyclerView ordersRecyclerView = findViewById(R.id.ordersRecyclerView);
        ordersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadOrders(ordersRecyclerView);
    }

    private void loadOrders(RecyclerView recyclerView) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "N/A");
        String email = sharedPreferences.getString("email", "N/A");

        MealOrderDatabase db = MealOrderDatabase.getInstance(this);
        new Thread(() -> {
            List<MealOrder> orders = db.orderDao().getOrdersForUser(username, email);
            runOnUiThread(() -> recyclerView.setAdapter(new OrderAdapter(orders)));
        }).start();
    }
}
