package com.example.tiffincart.Screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tiffincart.R;

public class CustomerProfile extends Fragment {

    private TextView logoutTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the fragment layout
        View view = inflater.inflate(R.layout.activity_customer_profile, container, false);

        // Find the logout TextView by its ID
        logoutTextView = view.findViewById(R.id.logout_text);

        // Set an OnClickListener on the TextView
        logoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to navigate back to LoginActivity
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();  // Finish the current activity to remove it from the back stack
            }
        });

        return view;
    }
}
