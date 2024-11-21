package com.example.tiffincart.Screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {

    EditText signupName, signUpEmail, signupUserName, signupPassword;
    TextView loginRedirectText;
    Button signupButton;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(com.example.tiffincart.R.layout.activity_sign_up);

        signupName = findViewById(com.example.tiffincart.R.id.signUp_name);
        signUpEmail = findViewById(com.example.tiffincart.R.id.signUp_email);
        signupUserName = findViewById(com.example.tiffincart.R.id.signUp_userName);
        signupPassword = findViewById(com.example.tiffincart.R.id.signUp_password);
        signupButton = findViewById(com.example.tiffincart.R.id.signUp_button);
        loginRedirectText = findViewById(com.example.tiffincart.R.id.alreadyUser);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = signupName.getText().toString();
                String mail = signUpEmail.getText().toString();
                String userName = signupUserName.getText().toString();
                String password = signupPassword.getText().toString();

                if (name.isEmpty() || mail.isEmpty() || userName.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if the username already exists
                reference.child(userName).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            // Username already exists
                            Toast.makeText(SignUpActivity.this, "Username already exists. Please choose another one.", Toast.LENGTH_SHORT).show();
                        } else {
                            // Create a new user
                            com.example.tiffincart.HelperClass helperClass = new com.example.tiffincart.HelperClass(name, mail, userName, password);
                            reference.child(userName).setValue(helperClass).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignUpActivity.this, "Sign Up Successful", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(SignUpActivity.this, "Failed to Sign Up", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(SignUpActivity.this, "Error occurred while checking username", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
