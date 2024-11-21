package com.example.tiffincart.Screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.tiffincart.R;

public class ChooseOne extends AppCompatActivity {

    ImageButton chef;
    ImageButton customer;
    ImageButton consultant;
    Intent intent;
    String type;
    ConstraintLayout bgImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_one);

        customer = findViewById(R.id.customer_button);
        chef = findViewById(R.id.chef_button);
        consultant = findViewById(R.id.consultant_button);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        window.setStatusBarColor(getResources().getColor(R.color.purple));

        // Set click listeners for each button
        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Login Activity for Customer
                Intent intent = new Intent(ChooseOne.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        chef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Chef Activity (replace ChefActivity.class with your actual class)
                Intent intent = new Intent(ChooseOne.this, ChefLogin.class);
                startActivity(intent);
            }
        });

        consultant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Consultant Activity (replace ConsultantActivity.class with your actual class)
                Intent intent = new Intent(ChooseOne.this, ConsultantRegistration.class);
                startActivity(intent);
            }
        });
    }
}