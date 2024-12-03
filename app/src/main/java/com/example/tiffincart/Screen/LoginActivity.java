package com.example.tiffincart.Screen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tiffincart.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private EditText loginUserName, loginPassword;
    private Button loginButton;
    private TextView signupRegister, forgotPassword;
    private FirebaseAuth auth;
    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth and Database Reference
        initFirebase();

        // Initialize UI components
        initUI();

        // Set up event listeners
        setEventListeners();
    }

    // Method to initialize Firebase
    private void initFirebase() {
        auth = FirebaseAuth.getInstance(); // For authentication
        usersRef = FirebaseDatabase.getInstance().getReference("users"); // Firebase Database
    }

    // Method to initialize UI components
    private void initUI() {
        loginUserName = findViewById(R.id.signIn_userName);
        loginPassword = findViewById(R.id.signIn_password);
        signupRegister = findViewById(R.id.notRegister);
        loginButton = findViewById(R.id.signIn_button);
        forgotPassword = findViewById(R.id.forgotPassword);

        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> navigateToChooseOne());
    }

    // Method to set up event listeners
    private void setEventListeners() {
        loginButton.setOnClickListener(v -> handleLogin());
        signupRegister.setOnClickListener(v -> navigateToSignUp());
        forgotPassword.setOnClickListener(v -> showForgotPasswordDialog());
    }

    // Handle Login Logic
    private void handleLogin() {
        if (!validateUserName() | !validatePassword()) return;
        authenticateUser();
    }

    // Firebase authentication and database validation
    private void authenticateUser() {
        String usernameInput = loginUserName.getText().toString().trim();
        String passwordInput = loginPassword.getText().toString().trim();

        if (TextUtils.isEmpty(usernameInput) || TextUtils.isEmpty(passwordInput)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        usersRef.child(usernameInput).addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String storedPassword = snapshot.child("password").getValue(String.class);
                    String fullName = snapshot.child("name").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);
                    if (storedPassword != null && storedPassword.equals(passwordInput)) {
                        saveUsernameToSharedPreferences(usernameInput, fullName, email);
                        navigateToHome();
                    } else {
                        Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LoginActivity.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Save username to SharedPreferences
    private void saveUsernameToSharedPreferences(String username, String fullName, String email) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("fullName", fullName);
        editor.putString("email", email);
        editor.apply();
    }

    // Validate username field
    private boolean validateUserName() {
        String val = loginUserName.getText().toString();
        if (val.isEmpty()) {
            loginUserName.setError("User name is must");
            return false;
        }
        loginUserName.setError(null);
        return true;
    }

    // Validate password field
    private boolean validatePassword() {
        String val = loginPassword.getText().toString();
        if (val.isEmpty()) {
            loginPassword.setError("Password is must");
            return false;
        }
        loginPassword.setError(null);
        return true;
    }

    // Show Forgot Password Dialog
    private void showForgotPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_forgot_password, null);
        builder.setView(dialogView);

        EditText emailBox = dialogView.findViewById(R.id.emailBox);
        AlertDialog dialog = builder.create();

        dialogView.findViewById(R.id.btnReset).setOnClickListener(v -> {
            String userEmail = emailBox.getText().toString();
            if (TextUtils.isEmpty(userEmail) || !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                Toast.makeText(LoginActivity.this, "Enter your registered email id", Toast.LENGTH_SHORT).show();
            } else {
                sendPasswordResetEmail(userEmail, dialog);
            }
        });

        dialogView.findViewById(R.id.btnCancel).setOnClickListener(v -> dialog.dismiss());

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        dialog.show();
    }

    // Send password reset email
    private void sendPasswordResetEmail(String email, AlertDialog dialog) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(LoginActivity.this, "Check your email", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            } else {
                Toast.makeText(LoginActivity.this, "Unable to send reset email", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Navigate to Home
    private void navigateToHome() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    // Navigate to ChooseOne activity
    private void navigateToChooseOne() {
        Intent intent = new Intent(this, ChooseOne.class);
        startActivity(intent);
        finish();
    }

    // Navigate to SignUpActivity
    private void navigateToSignUp() {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }
}
