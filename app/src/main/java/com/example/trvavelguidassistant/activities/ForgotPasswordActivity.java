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

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText inputEmail;
    private MaterialButton buttonForgotPassword;
    private ProgressBar forgotPasswordProgressbar;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        ImageView imageBack = findViewById(R.id.imageBack);
        imageBack.setOnClickListener(v -> onBackPressed());

        mAuth = FirebaseAuth.getInstance();

        inputEmail = findViewById(R.id.inputEmail);
        buttonForgotPassword = findViewById(R.id.buttonForgotPassword);
        forgotPasswordProgressbar = findViewById(R.id.forgotPasswordProgressBar);

        buttonForgotPassword.setOnClickListener(v -> resetPassword());
    }

    private void resetPassword() {
        buttonForgotPassword.setVisibility(View.INVISIBLE);
        forgotPasswordProgressbar.setVisibility(View.VISIBLE);

        String email = inputEmail.getText().toString().trim();

        if(email.isEmpty()){
            buttonForgotPassword.setVisibility(View.VISIBLE);
            forgotPasswordProgressbar.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Error : Input your Email", Toast.LENGTH_SHORT).show();
            inputEmail.getText().clear();
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            buttonForgotPassword.setVisibility(View.VISIBLE);
            forgotPasswordProgressbar.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Error : Input valid type Email", Toast.LENGTH_SHORT).show();
            inputEmail.getText().clear();
        }else{
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    Toast.makeText(ForgotPasswordActivity.this, "Check your email to reset password", Toast.LENGTH_SHORT).show();
                } else {
                    buttonForgotPassword.setVisibility(View.VISIBLE);
                    forgotPasswordProgressbar.setVisibility(View.INVISIBLE);
                    Toast.makeText(ForgotPasswordActivity.this, "Error : This email haven't an account", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}