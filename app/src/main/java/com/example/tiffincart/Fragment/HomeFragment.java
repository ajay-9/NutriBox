package com.example.tiffincart.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiffincart.Adapter.CategoryAdapter;
import com.example.tiffincart.Adapter.ConsultantAdapter;
import com.example.tiffincart.Adapter.TiffinAdapter;
import com.example.tiffincart.Model.CategoryItem;
import com.example.tiffincart.Model.ConsultantItem;
import com.example.tiffincart.Model.TiffinItem;
import com.example.tiffincart.R;
import com.example.tiffincart.Screen.AllTiffinsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private RecyclerView trendingTiffinRecyclerView;
    private RecyclerView categoryRecyclerView;
    private RecyclerView consultantRecyclerView;
    private TextView helloUserTextView;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;


    public HomeFragment() {
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize RecyclerViews
        trendingTiffinRecyclerView = view.findViewById(R.id.trendingTiffinRecyclerView);
        categoryRecyclerView = view.findViewById(R.id.categoriesRecyclerView);
        consultantRecyclerView = view.findViewById(R.id.consultantsRecyclerView);

        // Set LayoutManager for RecyclerViews
        trendingTiffinRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        consultantRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        TextView greetingTextView = view.findViewById(R.id.helloUserTextView);

        // Retrieve the username from SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "User"); // Default is "User"

        // Set greeting
        greetingTextView.setText("Hello, " + username);


        // Example Data for Tiffin Meals, Categories, and Consultants
        List<TiffinItem> tiffinItems = new ArrayList<>();
        tiffinItems.add(new TiffinItem(
                "Protein Rich Meal",
                R.drawable.protein,
                "A balanced meal rich in proteins for muscle growth.",
                "35",
                "400 kcal",
                "Chicken, Quinoa, Broccoli",
                "Fit Bites Restaurant"
        ));
        tiffinItems.add(new TiffinItem(
                "High Carbs",
                R.drawable.carbs,
                "Carb-loaded meal for energy boost.",
                "30",
                "500 kcal",
                "Rice, Potatoes, Lentils",
                "Healthy Eats"
        ));
        tiffinItems.add(new TiffinItem(
                "Weight Loss Meal",
                R.drawable.wt_loss,
                "Low-calorie meal for weight management.",
                "25",
                "250 kcal",
                "Grilled Fish, Salad, Avocado",
                "Slimmer Meals"
        ));
        tiffinItems.add(new TiffinItem(
                "Keto Meal",
                R.drawable.keto,
                "High-fat, low-carb meal for keto enthusiasts.",
                "40",
                "600 kcal",
                "Bacon, Cheese, Eggs",
                "Keto Haven"
        ));

        List<CategoryItem> categoryItems = new ArrayList<>();
        categoryItems.add(new CategoryItem("Breakfast", R.drawable.tiffin1));
        categoryItems.add(new CategoryItem("Lunch", R.drawable.tiffin1));
        categoryItems.add(new CategoryItem("Dinner", R.drawable.tiffin1));
        categoryItems.add(new CategoryItem("Breakfast", R.drawable.tiffin1));
        categoryItems.add(new CategoryItem("Lunch", R.drawable.tiffin1));
        categoryItems.add(new CategoryItem("Dinner", R.drawable.tiffin1));

        List<ConsultantItem> consultantItems = new ArrayList<>();
        consultantItems.add(new ConsultantItem("Consultant 1", R.drawable.user1));
        consultantItems.add(new ConsultantItem("Consultant 2", R.drawable.user2));
        consultantItems.add(new ConsultantItem("Consultant 3", R.drawable.user1));
        consultantItems.add(new ConsultantItem("Consultant 4", R.drawable.user2));
        consultantItems.add(new ConsultantItem("Consultant 5", R.drawable.user1));
        consultantItems.add(new ConsultantItem("Consultant 6", R.drawable.user2));

        // Set Adapters for RecyclerViews
        TiffinAdapter tiffinAdapter = new TiffinAdapter(tiffinItems);
        trendingTiffinRecyclerView.setAdapter(tiffinAdapter);

        CategoryAdapter categoryAdapter = new CategoryAdapter(categoryItems);
        categoryRecyclerView.setAdapter(categoryAdapter);

        ConsultantAdapter consultantAdapter = new ConsultantAdapter(consultantItems);
        consultantRecyclerView.setAdapter(consultantAdapter);

        // View All Button
        TextView viewAllTrending = view.findViewById(R.id.viewAllTrending);
        viewAllTrending.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AllTiffinsActivity.class);
            startActivity(intent);
        });

        TextView viewAllText = view.findViewById(R.id.viewAllConsultants);
        viewAllText.setOnClickListener(v -> {
            // Replace with ViewAllFragment
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new AllConsultantFragment())
                    .addToBackStack(null) // Add to back stack to enable back navigation
                    .commit();
        });

        return view;
    }
}