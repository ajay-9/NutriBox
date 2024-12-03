package com.example.tiffincart.Screen;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiffincart.Adapter.TiffinAdapter;
import com.example.tiffincart.Model.TiffinItem;
import com.example.tiffincart.R;

import java.util.ArrayList;
import java.util.List;

public class AllTiffinsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tiffins);

        RecyclerView allTiffinsRecyclerView = findViewById(R.id.allTiffinsRecyclerView);
        allTiffinsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        TiffinAdapter adapter = new TiffinAdapter(getTiffinItems());
        allTiffinsRecyclerView.setAdapter(adapter);
    }

    private List<TiffinItem> getTiffinItems() {
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

        return tiffinItems;
    }
}
