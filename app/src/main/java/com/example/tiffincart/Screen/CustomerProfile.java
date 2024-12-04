package com.example.tiffincart.Screen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tiffincart.R;

public class CustomerProfile extends Fragment {

    private TextView logoutTextView, userName, userNameTextView, fullNameTextView, emailTextView;
    private LinearLayout detailsLayout;
    private ImageView arrowIcon;
    private boolean isExpanded = false;
    private TextView myBookingsOption;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_customer_profile, container, false);

        userNameTextView = view.findViewById(R.id.user_name);
        userName = view.findViewById(R.id.username);
        fullNameTextView = view.findViewById(R.id.full_name);
        emailTextView = view.findViewById(R.id.email);
        detailsLayout = view.findViewById(R.id.details_layout);
        arrowIcon = view.findViewById(R.id.arrow_icon);
        logoutTextView = view.findViewById(R.id.logout_text);
        myBookingsOption = view.findViewById(R.id.myBookingsOption);

        loadUserInfo();

        view.findViewById(R.id.personal_info_card).setOnClickListener(v -> toggleDetails());

        TextView myOrdersButton = view.findViewById(R.id.myOrdersButton);
        myOrdersButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MyOrdersActivity.class);
            startActivity(intent);
        });
        // Set click listener
        myBookingsOption.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MyConsultantBookingsActivity.class);
            startActivity(intent);
        });

        logoutTextView.setOnClickListener(v -> {
            clearUserPreferences();
            navigateToLoginActivity();
        });

        return view;
    }

    private void loadUserInfo() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserPrefs", requireContext().MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "User");
        String fullName = sharedPreferences.getString("fullName", "N/A");
        String email = sharedPreferences.getString("email", "N/A");

        userNameTextView.setText("Hello, " + username);
        userName.setText("Username: " + username);
        fullNameTextView.setText("Name: " + fullName);
        emailTextView.setText("Email: " + email);
    }

    private void clearUserPreferences() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserPrefs", requireContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    private void navigateToLoginActivity() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }

    private void toggleDetails() {
        if (isExpanded) {
            detailsLayout.setVisibility(View.GONE);
            arrowIcon.setRotation(0);
        } else {
            detailsLayout.setVisibility(View.VISIBLE);
            arrowIcon.setRotation(180);
        }
        isExpanded = !isExpanded;
    }
}
