package com.example.tiffincart.ChefPackage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tiffincart.R;

public class ChefOrders extends Fragment {

    public ChefOrders() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the fragment's layout
        View rootView = inflater.inflate(R.layout.activity_chef_orders, container, false);

        // Handle window insets for proper padding
        ViewCompat.setOnApplyWindowInsetsListener(rootView.findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        return rootView;
    }
}
