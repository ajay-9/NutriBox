package com.example.tiffincart.ChefPackage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiffincart.Adapter.DishAdapter;
import com.example.tiffincart.Fragment.PostDishFragment;
import com.example.tiffincart.Model.DishItem;
import com.example.tiffincart.R;
import com.example.tiffincart.Screen.ChefLogin;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChefDashboard extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DishAdapter dishAdapter;
    private List<DishItem> dishList;
    private Button postDishButton;

    private DatabaseReference databaseReference;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_dashboard);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("chefs");

        recyclerView = findViewById(R.id.dishes_recycler_view);
        postDishButton = findViewById(R.id.post_dish_button);

        dishList = new ArrayList<>();
        dishAdapter = new DishAdapter(dishList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(dishAdapter);

        // Set the OnClickListener for the "Post Dish" button
        postDishButton.setOnClickListener(v -> {
            // Navigate to Post Dish Activity
            Intent intent = new Intent(ChefDashboard.this, PostDishFragment.class);
            startActivity(intent);
        });


    }



    @Override
    protected void onStart() {
        super.onStart();

        // Log the current user to check if they are authenticated
        if (auth.getCurrentUser() == null) {
            Log.d("ChefDashboard", "User is not logged in.");
            Intent intent = new Intent(ChefDashboard.this, ChefLogin.class);
            startActivity(intent);
            finish();  // Close the current activity
        } else {
            Log.d("ChefDashboard", "User is logged in: " + auth.getCurrentUser().getEmail());
            loadChefDishes();
        }
    }

    private void loadChefDishes() {
        // Use the authenticated user's UID to get dishes
        String chefId = auth.getCurrentUser().getUid();
        Query query = databaseReference.child(chefId).child("dishes");

        query.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dishList.clear();
                for (DataSnapshot dishSnapshot : dataSnapshot.getChildren()) {
                    DishItem dish = dishSnapshot.getValue(DishItem.class);
                    dishList.add(dish);
                }
                dishAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ChefDashboard.this, "Failed to load dishes", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
