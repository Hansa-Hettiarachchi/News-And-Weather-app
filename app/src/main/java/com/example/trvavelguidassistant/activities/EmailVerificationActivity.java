package com.example.trvavelguidassistant.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trvavelguidassistant.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailVerificationActivity extends AppCompatActivity {

    private EditText inputEmail,inputPassword;
    private MaterialButton buttonVerificationResend;
    private ProgressBar verificationResendProgressBar;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);

        ImageView imageBack = findViewById(R.id.imageBack);
        imageBack.setOnClickListener(v -> onBackPressed());

        mAuth = FirebaseAuth.getInstance();

        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        buttonVerificationResend = findViewById(R.id.buttonVerificationResend);
        verificationResendProgressBar = findViewById(R.id.verificationResendProgressBar);

        buttonVerificationResend.setOnClickListener(view -> {
            String email = inputEmail.getText().toString();
            String password = inputPassword.getText().toString();

            if(inputEmail.getText().toString().trim().isEmpty()){
                Toast.makeText(EmailVerificationActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
            }else if(!Patterns.EMAIL_ADDRESS.matcher(inputEmail.getText().toString()).matches()){
                Toast.makeText(EmailVerificationActivity.this, "Enter valid Email", Toast.LENGTH_SHORT).show();
            }else if (inputPassword.getText().toString().trim().isEmpty()){
                Toast.makeText(EmailVerificationActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
            } else{
                resendVerificationEmail(email,password);
            }
        });
    }

    private void resendVerificationEmail(String email,String password) {
        buttonVerificationResend.setVisibility(View.INVISIBLE);
        verificationResendProgressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser fUser = mAuth.getCurrentUser();
                        assert fUser != null;
                        if (fUser.isEmailVerified()) {
                            Toast.makeText(this, "Already Verified Account", Toast.LENGTH_SHORT).show();
                        } else {
                            fUser.sendEmailVerification()
                                    .addOnSuccessListener(unused -> Toast.makeText(
                                            EmailVerificationActivity.this, "Verification email has been sent.", Toast.LENGTH_SHORT).show())
                                    .addOnFailureListener(e -> Toast.makeText(EmailVerificationActivity.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show());
                        }
                        FirebaseAuth.getInstance().signOut();
                        buttonVerificationResend.setVisibility(View.VISIBLE);
                        verificationResendProgressBar.setVisibility(View.INVISIBLE);
                        Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }else{
                        buttonVerificationResend.setVisibility(View.VISIBLE);
                        verificationResendProgressBar.setVisibility(View.INVISIBLE);
                        inputPassword.getText().clear();
                        Toast.makeText(this, "Error : Invalid Email or Password", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}