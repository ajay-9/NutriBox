package com.example.tiffincart.Screen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.tiffincart.R;

public class IntroductoryActivity extends AppCompatActivity {

    ImageView logo, splashImg;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_introductory);

        logo = findViewById(R.id.logo);
        lottieAnimationView = findViewById(R.id.lottie);
        splashImg = findViewById(R.id.img);

        // Animations for logo, image, and Lottie file
        splashImg.animate().translationY(-1600).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(1400).setDuration(1000).setStartDelay(4000);

        // Handler to move to next activity after a delay (same as the animation duration)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start MainActivity (or the activity you want to load after the intro)
                Intent intent = new Intent(IntroductoryActivity.this, ChooseOne.class);
                startActivity(intent);
                finish();  // Close the current activity
            }
        }, 5000);
    }
}
