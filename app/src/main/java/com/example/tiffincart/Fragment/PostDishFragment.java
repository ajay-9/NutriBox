package com.example.tiffincart.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tiffincart.Model.DishItem;
import com.example.tiffincart.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class PostDishFragment extends Fragment {

    private EditText dishNameEditText, dishDescriptionEditText, dishPriceEditText;
    private ImageView dishImageView;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private Uri selectedImageUri;

    private static final int IMAGE_PICKER_REQUEST = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_post_dish_fragment, container, false);

        // Initialize Firebase Auth and Database
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        storageReference = FirebaseStorage.getInstance().getReference("dish_images");

        dishNameEditText = view.findViewById(R.id.dish_name_input);
        dishDescriptionEditText = view.findViewById(R.id.dish_description_input);
        dishPriceEditText = view.findViewById(R.id.price_edi_text);
        Button postDishButton = view.findViewById(R.id.submit_dish_button);
        Button selectImageButton = view.findViewById(R.id.select_image_button);
        dishImageView = view.findViewById(R.id.dish_image);

        selectImageButton.setOnClickListener(v -> openImagePicker());
        postDishButton.setOnClickListener(v -> postDish());

        return view;
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICKER_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICKER_REQUEST && resultCode == getActivity().RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            dishImageView.setImageURI(selectedImageUri); // Show selected image in the ImageView
        }
    }

    private void postDish() {
        String dishName = dishNameEditText.getText().toString().trim();
        String dishPrice = dishPriceEditText.getText().toString().trim();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            Toast.makeText(getContext(), "Please log in to post a dish", Toast.LENGTH_SHORT).show();
            return;
        }

        String uid = auth.getCurrentUser().getUid();
        if (dishName.isEmpty() || dishPrice.isEmpty()) {
            Toast.makeText(getContext(), "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference dishRef = FirebaseDatabase.getInstance().getReference("chefs").child(uid).child("dishes");
        String dishId = dishRef.push().getKey();
        if (dishId == null) {
            Toast.makeText(getContext(), "Error generating dish ID", Toast.LENGTH_SHORT).show();
            return;
        }

        DishItem dish = new DishItem(dishId, dishName, dishPrice, dishImageView.toString());
        dishRef.child(dishId).setValue(dish).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getContext(), "Dish posted successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Failed to post dish", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
