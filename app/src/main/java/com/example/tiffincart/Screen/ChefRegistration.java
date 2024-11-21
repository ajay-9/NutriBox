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

import com.example.tiffincart.ChefHelperClass;
import com.example.tiffincart.Fragment.ChefHomeFragment;
import com.example.tiffincart.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChefRegistration extends AppCompatActivity {

//    String[] Ontario = {"Kitchener", "Waterloo", "Cambridge", "Guelph", "Toronto", "Hamilton", "Brampton", "Mississauga", "Brantford", "Scarborough", "Woodstock", "Etobicoke", "London"};

    EditText Name, Email, PhoneNumber, Password, ConfPassword;
//    Spinner StateSpin, CitySpin;
    TextView alreadyUser;
    Button SignUp, BtnEmail, Phone;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    String StrName, StrEmail, StrPhoneNumber, StrPassword, StrConfPassword, role = "Chef";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chef_registration);

        Name = findViewById(R.id.signUp_name);
        Email = findViewById(R.id.signUp_email);
        PhoneNumber = findViewById(R.id.signUp_phone);
        Password = findViewById(R.id.signUp_password);
        ConfPassword = findViewById(R.id.signUp_confirm_password);
        SignUp = findViewById(R.id.btnSignIn);
        Phone = findViewById(R.id.btnPhone);
        BtnEmail = findViewById(R.id.btnEmail);
        alreadyUser = findViewById(R.id.alreadyUser);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("chefs");

        // Setup City Spinner
//        CitySpin = findViewById(R.id.citySpinner);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Ontario);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set up OnClickListener for 'Already User? Login'
        alreadyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to ChefLogin Activity
                Intent intent = new Intent(ChefRegistration.this, ChefLogin.class);
                startActivity(intent);
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrName = Name.getText().toString();
                StrEmail = Email.getText().toString();
                StrPhoneNumber = PhoneNumber.getText().toString();
                StrPassword = Password.getText().toString();
                StrConfPassword = ConfPassword.getText().toString();

                if (StrName.isEmpty() || StrEmail.isEmpty() || StrPhoneNumber.isEmpty() || StrPassword.isEmpty() || StrConfPassword.isEmpty()) {
                    Toast.makeText(ChefRegistration.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!StrPassword.equals(StrConfPassword)) {
                    Toast.makeText(ChefRegistration.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check and format the phone number
                if (!StrPhoneNumber.startsWith("+")) {
                    StrPhoneNumber = "+1" + StrPhoneNumber; // Add default country code, e.g., +1 for U.S.
                }

                if (StrPhoneNumber.length() < 10) {
                    Toast.makeText(ChefRegistration.this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Save Chef details to Firebase
                databaseReference.child(StrPhoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Toast.makeText(ChefRegistration.this, "Phone number already registered. Please use a different number.", Toast.LENGTH_SHORT).show();
                        } else {
                            ChefHelperClass chef = new ChefHelperClass(StrName, StrEmail, StrPhoneNumber, StrPassword, role);
                            databaseReference.child(StrPhoneNumber).setValue(chef).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ChefRegistration.this, "Registration Successful", Toast.LENGTH_LONG).show();
                                    // Pass the formatted phone number to ChefPhoneVerify Activity
                                    Intent intent = new Intent(ChefRegistration.this, ChefHomeFragment.class);
                                    intent.putExtra("phoneNumber", StrPhoneNumber); // Pass formatted phone number here
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(ChefRegistration.this, "Failed to Register", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(ChefRegistration.this, "Error occurred while registering", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



        BtnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement email registration or verification if required
            }
        });

        Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement phone verification if required
            }
        });
    }
}
