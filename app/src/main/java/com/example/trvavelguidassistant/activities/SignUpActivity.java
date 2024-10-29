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
import com.example.trvavelguidassistant.utilities.Constants;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    private EditText inputFirstName, inputLastName, inputIDNumber, inputEmail, inputPassword, inputConfirmPassword;
    private MaterialButton buttonSignUp;
    private ProgressBar signUpProgressBar;


    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ImageView imageBack = findViewById(R.id.imageBack);
        imageBack.setOnClickListener(v -> onBackPressed());
        findViewById(R.id.textSignIn).setOnClickListener(v-> startActivity(new Intent(SignUpActivity.this, SignInActivity.class)));

        inputFirstName = findViewById(R.id.inputFirstName);
        inputLastName = findViewById(R.id.inputLastName);
        inputIDNumber = findViewById(R.id.inputIDNumber);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        inputConfirmPassword = findViewById(R.id.inputConfirmPassword);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        signUpProgressBar = findViewById(R.id.signUpProgressBar);

        mAuth = FirebaseAuth.getInstance();

        buttonSignUp.setOnClickListener(view -> {
            if (inputFirstName.getText().toString().trim().isEmpty()) {
                Toast.makeText(SignUpActivity.this, "Enter First name", Toast.LENGTH_SHORT).show();
            } else if (inputLastName.getText().toString().trim().isEmpty()) {
                Toast.makeText(SignUpActivity.this, "Enter Last name", Toast.LENGTH_SHORT).show();
            } else if (inputIDNumber.getText().toString().trim().isEmpty()) {
                Toast.makeText(SignUpActivity.this, "Enter ID Number", Toast.LENGTH_SHORT).show();
            } else if (inputEmail.getText().toString().trim().isEmpty()) {
                Toast.makeText(SignUpActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
            } else if(!Patterns.EMAIL_ADDRESS.matcher(inputEmail.getText().toString()).matches()){
                Toast.makeText(SignUpActivity.this, "Enter valid Email", Toast.LENGTH_SHORT).show();
            } else if(inputPassword.getText().toString().trim().isEmpty()) {
                Toast.makeText(SignUpActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
            } else if(inputConfirmPassword.getText().toString().trim().isEmpty()) {
                Toast.makeText(SignUpActivity.this, "Confirm your password", Toast.LENGTH_SHORT).show();
            }else if(!inputPassword.getText().toString().equals(inputConfirmPassword.getText().toString())){
                Toast.makeText(SignUpActivity.this, "Password and Confirm Password must be same", Toast.LENGTH_SHORT).show();
            }else{
                signUp();
            }
        });
    }

    private void signUp(){
        buttonSignUp.setVisibility(View.INVISIBLE);
        signUpProgressBar.setVisibility(View.VISIBLE);

        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){

                FirebaseUser fUser= mAuth.getCurrentUser();
                assert fUser != null;
                fUser.sendEmailVerification()
                        .addOnSuccessListener(unused -> Toast.makeText(SignUpActivity.this, "User Created &Verification email has been sent.", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e -> Toast.makeText(SignUpActivity.this, "Error : "+ e.getMessage(), Toast.LENGTH_SHORT).show());

                FirebaseFirestore database = FirebaseFirestore.getInstance();
                HashMap<String, Object> user = new HashMap<>();
                user.put(Constants.KEY_FIRST_NAME, inputFirstName.getText().toString());
                user.put(Constants.KEY_LAST_NAME, inputLastName.getText().toString());
                user.put(Constants.KEY_ID_NUMBER, inputIDNumber.getText().toString());
                user.put(Constants.KEY_EMAIL, inputEmail.getText().toString());
                user.put(Constants.KEY_OLD_EMAIL,"");
                user.put(Constants.KEY_PROFILE_PICTURE,"");
                user.put(Constants.KEY_LOG_NUMBER,"0");

                database.collection(Constants.KEY_COLLECTION_USERS)
                        .add(user)
                        .addOnSuccessListener(documentReference -> {
                            Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        })
                        .addOnFailureListener(e -> {
                            signUpProgressBar.setVisibility(View.INVISIBLE);
                            buttonSignUp.setVisibility(View.VISIBLE);
                            Toast.makeText(SignUpActivity.this, "Error : "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }else{
                signUpProgressBar.setVisibility(View.INVISIBLE);
                buttonSignUp.setVisibility(View.VISIBLE);
                Toast.makeText(SignUpActivity.this, "Error : Used email address", Toast.LENGTH_SHORT).show();
            }
        });
    }
}