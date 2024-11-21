package com.example.tiffincart.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiffincart.Adapter.CategoryAdapter;
import com.example.tiffincart.Adapter.ChefAdapter;
import com.example.tiffincart.Adapter.TiffinAdapter;
import com.example.tiffincart.Model.CategoryItem;
import com.example.tiffincart.Model.ChefItem;
import com.example.tiffincart.Model.TiffinItem;
import com.example.tiffincart.R;

import java.util.ArrayList;
import java.util.List;

public class ChefHomeFragment extends Fragment {

    private RecyclerView trendingTiffinRecyclerView;
    private RecyclerView categoryRecyclerView;
    private RecyclerView chefRecyclerView;

    public ChefHomeFragment() {
        // Required empty constructor
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for ChefHomeFragment
        View view = inflater.inflate(R.layout.fragment_chef_home, container, false);

        // Initialize RecyclerViews
        trendingTiffinRecyclerView = view.findViewById(R.id.trendingTiffinRecyclerView);
        categoryRecyclerView = view.findViewById(R.id.categoriesRecyclerView);
        chefRecyclerView = view.findViewById(R.id.chefsRecyclerView);

        // Set LayoutManagers
        trendingTiffinRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        chefRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Add example data
        List<TiffinItem> tiffinItems = new ArrayList<>();
        tiffinItems.add(new TiffinItem("Special Meal 1", R.drawable.tiffin1));
        tiffinItems.add(new TiffinItem("Special Meal 2", R.drawable.tiffin1));
        tiffinItems.add(new TiffinItem("Special Meal 3", R.drawable.tiffin1));

        List<CategoryItem> categoryItems = new ArrayList<>();
        categoryItems.add(new CategoryItem("Veg", R.drawable.tiffin1));
        categoryItems.add(new CategoryItem("Non-Veg", R.drawable.tiffin1));
        categoryItems.add(new CategoryItem("Snacks", R.drawable.tiffin1));

        List<ChefItem> chefItems = new ArrayList<>();
        chefItems.add(new ChefItem("Chef Alex", R.drawable.user1));
        chefItems.add(new ChefItem("Chef Jamie", R.drawable.user2));
        chefItems.add(new ChefItem("Chef Olivia", R.drawable.user1));
        chefItems.add(new ChefItem("Chef Emma", R.drawable.user2));

        // Set adapters
        TiffinAdapter tiffinAdapter = new TiffinAdapter(tiffinItems);
        trendingTiffinRecyclerView.setAdapter(tiffinAdapter);

        CategoryAdapter categoryAdapter = new CategoryAdapter(categoryItems);
        categoryRecyclerView.setAdapter(categoryAdapter);

        ChefAdapter chefAdapter = new ChefAdapter(chefItems);

        return view;
    }
}
