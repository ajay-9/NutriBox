package com.example.tiffincart.Screen;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tiffincart.Model.TiffinItem;
import com.example.tiffincart.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class TiffinDetailsActivity extends AppCompatActivity {

    private Button addToCartButton;
    private RadioGroup planOptionsGroup;
    private RadioButton oneTimeMealOption, weeklyPlanOption, monthlyPlanOption;
    private EditText specialInstructionsField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiffin_details);

        // Initialize views
        TextView tiffinName = findViewById(R.id.tiffinName);
        TextView tiffinDescription = findViewById(R.id.tiffinDescription);
        TextView tiffinPrice = findViewById(R.id.tiffinPrice);
        TextView tiffinCalories = findViewById(R.id.tiffinCalories);
        TextView tiffinIngredients = findViewById(R.id.tiffinIngredients);
        TextView tiffinRestaurant = findViewById(R.id.tiffinRestaurant);
        ImageView tiffinImage = findViewById(R.id.tiffinImage);
        addToCartButton = findViewById(R.id.addToCartButton);
        planOptionsGroup = findViewById(R.id.planOptionsGroup);
        oneTimeMealOption = findViewById(R.id.oneTimeMealOption);
        weeklyPlanOption = findViewById(R.id.weeklyPlanOption);
        monthlyPlanOption = findViewById(R.id.monthlyPlanOption);
        specialInstructionsField = findViewById(R.id.specialInstructions);

        SharedPreferences sharedPreferences = getSharedPreferences("CartPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        // Get data from Intent
        String name = getIntent().getStringExtra("name");
        String description = getIntent().getStringExtra("description");
        String price = getIntent().getStringExtra("price");
        String calories = getIntent().getStringExtra("calories");
        String ingredients = getIntent().getStringExtra("ingredients");
        String restaurant = getIntent().getStringExtra("restaurant");
        int imageResId = getIntent().getIntExtra("imageResId", R.drawable.placeholder);

        // Set data to views
        tiffinName.setText(name);
        tiffinDescription.setText(description);
        tiffinPrice.setText(price);
        tiffinCalories.setText(calories);
        tiffinIngredients.setText(ingredients);
        tiffinRestaurant.setText(restaurant);
        tiffinImage.setImageResource(imageResId);

        // Add to Cart button logic
        addToCartButton.setOnClickListener(v -> {
            int selectedPlanId = planOptionsGroup.getCheckedRadioButtonId();
            if (selectedPlanId == -1) {
                Toast.makeText(this, "Please select a plan", Toast.LENGTH_SHORT).show();
                return;
            }

            double selectedPrice = 0.0;
            String selectedPlan = null;
            if (selectedPlanId == oneTimeMealOption.getId()) {
                selectedPrice = Double.parseDouble(price.replace("$", ""));
                selectedPlan = "One-Time Meal";
            } else if (selectedPlanId == weeklyPlanOption.getId()) {
                selectedPrice = 100.0;
                selectedPlan = "Weekly Plan";
            } else if (selectedPlanId == monthlyPlanOption.getId()) {
                selectedPrice = 320.0;
                selectedPlan = "Monthly Plan";
            }

            // Get special instructions
            String specialInstructions = specialInstructionsField.getText().toString().trim();

            // Create TiffinItem with all details
            TiffinItem selectedItem = new TiffinItem(name, imageResId, description, "$" + selectedPrice, calories, ingredients, restaurant);
            selectedItem.setPlan(selectedPlan);
            selectedItem.setSpecialInstructions(specialInstructions);

            // Save to SharedPreferences
            String existingCartJson = sharedPreferences.getString("cartItems", "[]");
            Type type = new TypeToken<List<TiffinItem>>() {}.getType();
            List<TiffinItem> cartItems = gson.fromJson(existingCartJson, type);
            cartItems.add(selectedItem);

            editor.putString("cartItems", gson.toJson(cartItems));
            editor.apply();

            Toast.makeText(this, selectedPlan + " added to cart", Toast.LENGTH_SHORT).show();
        });


    }
}
