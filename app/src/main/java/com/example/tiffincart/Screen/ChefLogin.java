package com.example.tiffincart.Screen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tiffincart.ChefPackage.ChefHomePage;
import com.example.tiffincart.R;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ChefLogin extends AppCompatActivity {

    EditText loginEmail, loginPassword;
    Button loginButton;
    TextView signupRegister;
    FirebaseAuth auth;
    GoogleSignInOptions gOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chef_login);

        auth = FirebaseAuth.getInstance();

        loginEmail = findViewById(R.id.signIn_mail);
        loginPassword = findViewById(R.id.signIn_password);
        signupRegister = findViewById(R.id.notRegister);
        loginButton = findViewById(R.id.btnSignIn);
        ImageView backButton = findViewById(R.id.backButton);

        // Handle back button click
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(ChefLogin.this, ChooseOne.class);
            startActivity(intent);
            finish();
        });

        // Login button click
        loginButton.setOnClickListener(v -> {
            if (!validateEmail() | !validatePassword()) {
                return;
            }
            checkChef();
        });

        // Handle sign-up click
        signupRegister.setOnClickListener(v -> {
            Intent intent = new Intent(ChefLogin.this, ChefRegistration.class);
            startActivity(intent);
        });
    }

    public Boolean validateEmail() {
        String val = loginEmail.getText().toString();
        if (val.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(val).matches()) {
            loginEmail.setError("Valid email is required");
            return false;
        } else {
            loginEmail.setError(null);
            return true;
        }
    }

    public Boolean validatePassword() {
        String val = loginPassword.getText().toString();
        if (val.isEmpty()) {
            loginPassword.setError("Password is required");
            return false;
        } else {
            loginPassword.setError(null);
            return true;
        }
    }

    public void checkChef() {
        String chefEmail = loginEmail.getText().toString().trim();
        String chefPassword = loginPassword.getText().toString().trim();

        // Reference to the "chefs" node in Firebase
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("chefs");

        // Query to find the chef with the matching email
        Query checkChefDatabase = reference.orderByChild("email").equalTo(chefEmail);

        checkChefDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Get the first child (chef) from the snapshot
                    DataSnapshot chefSnapshot = snapshot.getChildren().iterator().next();

                    // Get the email and password from the chef data
                    String passwordFromDB = chefSnapshot.child("password").getValue(String.class);

                    // Debugging log to check the retrieved password
                    Log.d("ChefLogin", "Password from DB: " + passwordFromDB);

                    // Compare entered password with the one from the database
                    if (Objects.equals(passwordFromDB, chefPassword)) {
                        // If passwords match, navigate to ChefDashboard
                        Intent intent = new Intent(ChefLogin.this, ChefHomePage.class);
                        intent.putExtra("chefEmail", chefEmail); // Pass the chef's email
                        startActivity(intent);
                        finish(); // Close the login screen
                    } else {
                        // If passwords do not match
                        loginPassword.setError("Invalid Credentials");
                        loginPassword.requestFocus();
                    }
                } else {
                    // If no chef found with the given email
                    loginEmail.setError("Chef not found");
                    loginEmail.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors during the Firebase operation
                Log.e("ChefLogin", "Database error: " + error.getMessage());
                Toast.makeText(ChefLogin.this, "Database error. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
