package com.example.tiffincart.Screen;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tiffincart.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ChefPhoneVerify extends AppCompatActivity {

    private EditText otpEditText;
    private Button verifyButton;
    private FirebaseAuth auth;
    private String verificationId;
    private TextView txt, Resend;

    String phoneNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_phone_verify);

        otpEditText = findViewById(R.id.signIn_password);
        phoneNo = Objects.requireNonNull(getIntent().getStringExtra("phoneNumber")).trim();
        txt = findViewById(R.id.text);
        txt.setVisibility(View.INVISIBLE);
        Resend = findViewById(R.id.resendOTP);
        verifyButton = findViewById(R.id.btnSignIn);
        auth = FirebaseAuth.getInstance();

        // Initially hide Resend OTP button
        Resend.setVisibility(View.INVISIBLE);

        // Get the phone number from the previous activity
        String phoneNumber = getIntent().getStringExtra("phoneNumber");
        if (phoneNumber != null) {
            sendOTP(phoneNumber);
        } else {
            Toast.makeText(this, "Phone number is missing", Toast.LENGTH_SHORT).show();
        }

        // Verify the OTP when the button is clicked
        verifyButton.setOnClickListener(v -> {
            String otpCode = otpEditText.getText().toString().trim();
            if (TextUtils.isEmpty(otpCode)) {
                otpEditText.setError("Enter OTP");
                return;
            }
            verifyOTP(otpCode);
        });

        // Resend OTP when clicked
        Resend.setOnClickListener(v -> {
            // Hide the Resend button after clicking
            Resend.setVisibility(View.INVISIBLE);
            sendOTP(phoneNo);
        });
    }

    // Method to send OTP
    private void sendOTP(String phoneNumber) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                        // Auto-retrieval or instant verification succeeded
                        signInWithCredential(credential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        // Verification failed
                        Toast.makeText(ChefPhoneVerify.this, "Verification Failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("ChefPhoneVerify", "Verification failed", e);
                    }

                    @Override
                    public void onCodeSent(@NonNull String id, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                        // Code has been sent successfully
                        verificationId = id;
                        Toast.makeText(ChefPhoneVerify.this, "OTP sent successfully", Toast.LENGTH_SHORT).show();
                        // Show the Resend OTP button after OTP is sent
                        Resend.setVisibility(View.VISIBLE);
                    }
                }).build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    // Method to verify OTP
    private void verifyOTP(String otpCode) {
        if (verificationId != null) {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otpCode);
            signInWithCredential(credential);
        } else {
            Toast.makeText(this, "Verification ID is null", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to sign in with credential
    private void signInWithCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                // OTP verification successful
                Toast.makeText(ChefPhoneVerify.this, "Verification Successful", Toast.LENGTH_SHORT).show();
                // Navigate to next screen
                Intent intent = new Intent(ChefPhoneVerify.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                // OTP verification failed
                Toast.makeText(ChefPhoneVerify.this, "Verification Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
