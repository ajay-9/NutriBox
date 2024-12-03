package com.example.tiffincart.Screen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tiffincart.R;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CheckoutActivity extends AppCompatActivity {

    private PaymentSheet paymentSheet;
    private String clientSecret;
    private String publishableKey = "pk_test_51QRjNW2MxqC0Yq0K5CbOEKCqIz9SBOa9lIDRSSpb17ZLN5YETUb3gapzmqCTToBX5hFxOoeRLcRkj3xufGFmihq600FonUi1NS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Retrieve Total Amount
        double totalAmount = getIntent().getDoubleExtra("totalAmount", 0.0);

        // Update Total Amount on UI
        TextView totalAmountTxt = findViewById(R.id.totalAmountTxt);
        totalAmountTxt.setText(String.format("$%.2f", totalAmount));

        // Initialize Stripe
        PaymentConfiguration.init(getApplicationContext(), publishableKey);

        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);

        // Pay Now Button
        Button payButton = findViewById(R.id.payButton);
        payButton.setOnClickListener(v -> presentPaymentSheet());

        // Fetch client secret from backend
        fetchPaymentIntent();
    }

    private void fetchPaymentIntent() {
        new Thread(() -> {
            try {
                // Stripe Test API URL
                String url = "https://api.stripe.com/v1/payment_intents";

                // Amount in cents (e.g., $10.00 = 1000 cents)
                int amountInCents = (int) (getIntent().getDoubleExtra("totalAmount", 0.0) * 100);

                // Setup connection
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Authorization", "Bearer sk_test_51QRjNW2MxqC0Yq0KlMT9fWRtOP0O3YQNHAxy4bmLpehJLz24kzgl3GjdOSzpAM4Ak6HdCjHKXzDtH1792kgeW0qs00MoSLCYAR");
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setDoOutput(true);

                // Post data for PaymentIntent
                String postData = "amount=" + amountInCents +
                        "&currency=usd" +
                        "&payment_method_types[]=card";

                // Write data
                try (OutputStream os = connection.getOutputStream()) {
                    os.write(postData.getBytes("UTF-8"));
                }

                // Read response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                // Parse client secret from JSON
                JSONObject jsonResponse = new JSONObject(response.toString());
                clientSecret = jsonResponse.getString("client_secret");

                runOnUiThread(() -> {
                    if (clientSecret != null) {
                        Toast.makeText(this, "Payment Initialized!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Failed to fetch client secret", Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "Error initializing payment: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private void presentPaymentSheet() {
        if (clientSecret == null) {
            Toast.makeText(this, "Failed to initialize payment", Toast.LENGTH_SHORT).show();
            return;
        }

        // Configure PaymentSheet
        PaymentSheet.Configuration configuration = new PaymentSheet.Configuration("Tiffin Care");

        // Present PaymentSheet
        paymentSheet.presentWithPaymentIntent(clientSecret, configuration);
    }

    private void onPaymentSheetResult(PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            Toast.makeText(this, "Payment Successful!", Toast.LENGTH_SHORT).show();
            clearCart();
            navigateToHomeFragment();
        } else if (paymentSheetResult instanceof PaymentSheetResult.Failed) {
            Toast.makeText(this, "Payment Failed. Please try again.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Payment Cancelled.", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearCart() {
        SharedPreferences sharedPreferences = getSharedPreferences("CartPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("cartItems", "[]");
        editor.apply();
    }

    private void navigateToHomeFragment() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("navigateTo", "HomeFragment");
        startActivity(intent);
        finish();
    }
}
