package com.example.tiffincart.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiffincart.Adapter.CartAdapter;
import com.example.tiffincart.Model.TiffinItem;
import com.example.tiffincart.R;
import com.example.tiffincart.Screen.CheckoutActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class CartFragment extends Fragment {

    private RecyclerView cartView;
    private TextView deliveryFeeTxt, taxTxt, totalFeeTxt, emptyTxt;
    private LinearLayout summaryLayout;
    private CartAdapter cartAdapter;

    private final double DELIVERY_FEE = 10.00;
    private final double TAX_RATE = 0.13;

    private List<TiffinItem> cartItems;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        // Initialize Views
        cartView = view.findViewById(R.id.cartView);
        deliveryFeeTxt = view.findViewById(R.id.deliveryFeeTxt);
        taxTxt = view.findViewById(R.id.taxTxt);
        totalFeeTxt = view.findViewById(R.id.totalFeeTxt);
        emptyTxt = view.findViewById(R.id.emptyTxt);
        summaryLayout = view.findViewById(R.id.summaryLayout);
        Button checkoutButton = view.findViewById(R.id.checkoutButton);

        sharedPreferences = requireContext().getSharedPreferences("CartPrefs", getContext().MODE_PRIVATE);
        gson = new Gson();

        // Set up RecyclerView
        cartView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Load Cart Items
        loadCartItems();

        // Handle Checkout Button Click
        checkoutButton.setOnClickListener(v -> {
            if (cartItems == null || cartItems.isEmpty()) {
                Toast.makeText(getContext(), "Your cart is empty!", Toast.LENGTH_SHORT).show();
            } else {
                // Navigate to CheckoutActivity and pass total price
                Intent intent = new Intent(getContext(), CheckoutActivity.class);
                intent.putExtra("totalAmount", calculateTotal());
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadCartItems();
    }

    private void loadCartItems() {
        // Load cart items from SharedPreferences
        String cartJson = sharedPreferences.getString("cartItems", "[]");
        Type type = new TypeToken<List<TiffinItem>>() {}.getType();
        cartItems = gson.fromJson(cartJson, type);

        if (cartItems.isEmpty()) {
            emptyTxt.setVisibility(View.VISIBLE);
            cartView.setVisibility(View.GONE);
            summaryLayout.setVisibility(View.GONE);
            updateTotals(); // Update totals to zero
            return;
        }

        emptyTxt.setVisibility(View.GONE);
        cartView.setVisibility(View.VISIBLE);
        summaryLayout.setVisibility(View.VISIBLE);

        // Set up adapter with delete callback
        cartAdapter = new CartAdapter(cartItems, sharedPreferences, this::onItemDeleted);
        cartView.setAdapter(cartAdapter);

        // Calculate and update totals
        updateTotals();
    }

    private void onItemDeleted(int position) {
        // Remove the item from the list
        cartItems.remove(position);

        // Save updated list to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("cartItems", gson.toJson(cartItems));
        editor.apply();

        // Notify the adapter about the item removal
        cartAdapter.notifyItemRemoved(position);
        cartAdapter.notifyItemRangeChanged(position, cartItems.size());

        // Check if cart is empty and update layout visibility
        if (cartItems.isEmpty()) {
            emptyTxt.setVisibility(View.VISIBLE);
            cartView.setVisibility(View.GONE);
            summaryLayout.setVisibility(View.GONE);
        }

        // Update totals
        updateTotals();
    }

    private void updateTotals() {
        double subTotal = 0.0;
        for (TiffinItem item : cartItems) {
            subTotal += Double.parseDouble(item.getPrice().replace("$", ""));
        }
        double tax = subTotal * TAX_RATE;
        double total = subTotal + tax + DELIVERY_FEE;

        taxTxt.setText(String.format("$%.2f", tax));
        totalFeeTxt.setText(String.format("$%.2f", total));
        deliveryFeeTxt.setText(String.format("$%.2f", DELIVERY_FEE));
    }

    private double calculateTotal() {
        double subTotal = 0.0;
        for (TiffinItem item : cartItems) {
            subTotal += Double.parseDouble(item.getPrice().replace("$", ""));
        }
        double tax = subTotal * TAX_RATE;
        return subTotal + tax + DELIVERY_FEE;
    }
}
