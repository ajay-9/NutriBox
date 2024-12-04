package com.example.tiffincart.Screen;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.tiffincart.Database.TiffinCareDatabase;
import com.example.tiffincart.DAO.ConsultantBooking;
import com.example.tiffincart.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ConsultantDetailsActivity extends AppCompatActivity {

    private ImageView consultantImage;
    private TextView consultantName, consultantDetails;
    private Button bookButton;

    private TiffinCareDatabase database;
    private String userName;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultant_details);

        // Initialize Room Database
        database = Room.databaseBuilder(getApplicationContext(), TiffinCareDatabase.class, "tiffin_care_db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries() // Use background threads in production
                .build();

        // Retrieve user details from SharedPreferences
        userName = getSharedPreferences("UserPrefs", MODE_PRIVATE).getString("username", "Guest");
        email = getSharedPreferences("UserPrefs", MODE_PRIVATE).getString("email", "no-email");

        // Initialize views
        consultantImage = findViewById(R.id.consultantDetailsImage);
        consultantName = findViewById(R.id.consultantDetailsName);
        consultantDetails = findViewById(R.id.consultantDetailsDescription);
        bookButton = findViewById(R.id.bookConsultantButton);

        // Get data from Intent
        String name = getIntent().getStringExtra("name");
        String details = getIntent().getStringExtra("details");
        int imageResId = getIntent().getIntExtra("imageResId", R.drawable.placeholder);

        consultantName.setText(name);
//        consultantDetails.setText(details);
        consultantImage.setImageResource(imageResId);

        // Set Book button functionality
        bookButton.setOnClickListener(v -> bookConsultant(name, details));
    }

    private void bookConsultant(String consultantName, String consultantDetails) {
        // Calculate meeting date: 3 days later at 7 PM
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 3);
        calendar.set(Calendar.HOUR_OF_DAY, 19);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        String meetingDate = new SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(calendar.getTime());

        // Store in Room Database
        ConsultantBooking booking = new ConsultantBooking(consultantName, consultantDetails, userName, email, meetingDate);
        database.consultantBookingDao().insert(booking);

        // Show confirmation
        Toast.makeText(this, "Booking confirmed! Meeting on " + meetingDate, Toast.LENGTH_LONG).show();

        // Optional: Navigate back or update UI
        finish();
    }
}
