package com.example.tiffincart.Screen;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tiffincart.Fragment.CartFragment;
import com.example.tiffincart.Fragment.HomeFragment;
import com.example.tiffincart.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String navigateTo = getIntent().getStringExtra("navigateTo");
        if (navigateTo != null && navigateTo.equals("HomeFragment")) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        }

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set default fragment (HomeFragment)
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_home) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new HomeFragment())
                            .commit();
                    return true;
                }  else if (item.getItemId() == R.id.nav_consultant) {
                    // Replace with ConsultantFragment (not Activity)
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new ConsultantFragment())
                            .commit();
                    return true;
                } else if (item.getItemId() == R.id.nav_cart) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new CartFragment())
                            .commit();
                    return true;

                } else if (item.getItemId() == R.id.nav_profile) {
                    // Replace with CustomerProfile fragment
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new CustomerProfile())
                            .commit();
                    return true;
                }
                return false;
            }
        });
    }
}
