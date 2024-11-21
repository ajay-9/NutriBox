package com.example.tiffincart.Screen;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tiffincart.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MealBrowsingActivity extends AppCompatActivity {

    private TextView mealDetails;
    private Button fetchMealsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_browsing);

        mealDetails = findViewById(R.id.mealDetails);
        fetchMealsButton = findViewById(R.id.fetchMealsButton);

        fetchMealsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchMealData();
            }
        });
    }

    private void fetchMealData() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://foodiefetch.p.rapidapi.com/swiggy?query=grandamas%20cafe%20pune")
                .get()
                .addHeader("x-rapidapi-key", "1e099732b0msh8238a73272a1ea1p144b28jsn5f1255c06578")
                .addHeader("x-rapidapi-host", "foodiefetch.p.rapidapi.com")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("MealBrowsingActivity", "API call failed", e);
                runOnUiThread(() -> mealDetails.setText("Failed to load data."));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    parseMealData(responseData);
                }
            }
        });
    }

    private void parseMealData(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray mealsArray = jsonObject.getJSONArray("meals");

            final StringBuilder mealInfo = new StringBuilder();

            for (int i = 0; i < mealsArray.length(); i++) {
                JSONObject meal = mealsArray.getJSONObject(i);
                String name = meal.getString("name");
                String price = meal.getString("price");
                String description = meal.getString("description");

                mealInfo.append("Name: ").append(name).append("\n")
                        .append("Price: ").append(price).append("\n")
                        .append("Description: ").append(description).append("\n\n");
            }

            Log.d("MealBrowsingActivity", "Parsed Meal Info: " + mealInfo.toString());  // Log the meal info
            runOnUiThread(() -> mealDetails.setText(mealInfo.toString()));

        } catch (Exception e) {
            Log.e("MealBrowsingActivity", "Error parsing JSON", e);
            runOnUiThread(() -> mealDetails.setText("Error parsing data."));
        }
    }

}
