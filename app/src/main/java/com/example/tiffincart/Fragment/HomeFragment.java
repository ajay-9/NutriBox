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
                "Energy Booster Meal",
                R.drawable.energy_booster,
                "Packed with proteins and nutrients to keep you energized all day.",
                "40",
                "450 kcal",
                "Chicken, Sweet Potatoes, Spinach",
                "Fit Bites Restaurant"
        ));
        tiffinItems.add(new TiffinItem(
                "Vegan Feast",
                R.drawable.vegan_feast,
                "Plant-based tiffin packed with vibrant flavors.",
                "30",
                "300 kcal",
                "Quinoa, Chickpeas, Kale",
                "Plant Power Tiffin"
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
                "Weight Loss Bowl",
                R.drawable.wt_loss,
                "Balanced meal perfect for shedding some extra pounds.",
                "35",
                "350 kcal",
                "Brown Rice, Grilled Fish, Vegetables",
                "Healthy Eats"
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
        tiffinItems.add(new TiffinItem(
                "Exotic Asian Meal",
                R.drawable.exotic_asian_meal,
                "A blend of Asian flavors with noodles and stir-fried veggies.",
                "45",
                "420 kcal",
                "Rice Noodles, Tofu, Stir-Fried Vegetables",
                "Asian Spice"
        ));

        List<CategoryItem> categoryItems = new ArrayList<>();
        categoryItems.add(new CategoryItem(
                "Healthy Picks",
                R.drawable.healthy_picks // Replace with the correct image resource
        ));
        categoryItems.add(new CategoryItem(
                "Fitness Fuel",
                R.drawable.fitness_fuel // Replace with the correct image resource
        ));
        categoryItems.add(new CategoryItem(
                "Weight Loss Plans",
                R.drawable.weight_loss_plans // Replace with the correct image resource
        ));
        categoryItems.add(new CategoryItem(
                "Traditional Favorites",
                R.drawable.traditional_favorites // Replace with the correct image resource
        ));


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