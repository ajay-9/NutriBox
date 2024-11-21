package com.example.tiffincart.ChefPackage;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.tiffincart.Fragment.ChefHomeFragment;
import com.example.tiffincart.Fragment.PostDishFragment;
import com.example.tiffincart.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ChefHomePage extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_home_fragment);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Load ChefHomeFragment as the default fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ChefHomeFragment())
                    .commit();
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                // Navigate to different fragments based on menu item selection
                if (item.getItemId() == R.id.nav_home) {
                    selectedFragment = new ChefHomeFragment();
                } else if (item.getItemId() == R.id.nav_pending_orders) {
                    selectedFragment = new ChefPendingOrders();
                } else if (item.getItemId() == R.id.nav_orders) {
                    selectedFragment = new ChefOrders();
                } else if (item.getItemId() == R.id.nav_post) {
                    selectedFragment = new PostDishFragment();
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .commit();
                    return true;
                }

                return false;
            }
        });
    }
}