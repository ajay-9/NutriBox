package com.example.tiffincart.Screen;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tiffincart.R;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    EditText loginUserName, loginPassword;
    Button loginButton;
    TextView signupRegister, forgotPassword;
    private FirebaseAuth auth;
    GoogleSignInOptions gOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        loginUserName = findViewById(R.id.signIn_userName);
        loginPassword = findViewById(R.id.signIn_password);
        signupRegister = findViewById(R.id.notRegister);
        loginButton = findViewById(R.id.signIn_button);
        forgotPassword = findViewById(R.id.forgotPassword);
        ImageView backButton = findViewById(R.id.backButton);

        // Enable the action bar back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ChooseOne.class);
                startActivity(intent);
                finish();
            }
        });

        // Set click listener for login
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateUserName() | !validatePassword()) {

                } else {
                    checkUser();
                }
            }
        });

        // Navigate to SignUpActivity when the user clicks on "Not Registered? Sign Up"
        signupRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_forgot_password, null);
                EditText emailBox = dialogView.findViewById(R.id.emailBox);
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();
                dialogView.findViewById(R.id.btnReset).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String userEmail = emailBox.getText().toString();
                        if (TextUtils.isEmpty(userEmail) || !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                            Toast.makeText(LoginActivity.this, "Enter your registered email id", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        auth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(LoginActivity.this, "Check your email", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                } else {
                                    String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unable to send, failed";
                                    Log.e("PasswordReset", errorMessage);
                                    Toast.makeText(LoginActivity.this, "Unable to send, failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                dialogView.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                if (dialog.getWindow() != null){
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                dialog.show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Navigate back to ChooseOne activity
        Intent intent = new Intent(LoginActivity.this, ChooseOne.class);
        startActivity(intent);
        finish(); // Optional: Close the current activity
        return true;
    }

    @Override
    public void onBackPressed() {
        // Handle the device back button to navigate back to ChooseOne activity
        super.onBackPressed();
        Intent intent = new Intent(LoginActivity.this, ChooseOne.class);
        startActivity(intent);
        finish(); // Optional: Close the current activity
    }

    public Boolean validateUserName() {
        String val = loginUserName.getText().toString();
        if (val.isEmpty()) {
            loginUserName.setError("User name is must");
            return false;
        } else {
            loginUserName.setError(null);
            return true;
        }
    }

    public Boolean validatePassword() {
        String val = loginPassword.getText().toString();
        if (val.isEmpty()) {
            loginPassword.setError("Password is must");
            return false;
        } else {
            loginPassword.setError(null);
            return true;
        }
    }

    public void checkUser() {
        String userUserName = loginUserName.getText().toString().trim();
        String userPassword = loginPassword.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByKey().equalTo(userUserName);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    DataSnapshot userSnapshot = snapshot.child(userUserName);
                    String passwordFromDB = userSnapshot.child("password").getValue(String.class);

                    if (Objects.equals(passwordFromDB, userPassword)) {
                        // Navigate to MainActivity, which will load HomeFragment
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("fragment", "home");  // Pass a flag to indicate HomeFragment
                        startActivity(intent);
                        finish();  // Close LoginActivity
                    } else {
                        loginPassword.setError("Invalid Credentials");
                        loginPassword.requestFocus();
                    }
                } else {
                    loginUserName.setError("User not found");
                    loginUserName.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any potential errors here
            }
        });
    }

}
