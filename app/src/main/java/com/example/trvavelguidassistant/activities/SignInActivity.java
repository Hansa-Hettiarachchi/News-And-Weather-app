package com.example.trvavelguidassistant.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trvavelguidassistant.R;
import com.example.trvavelguidassistant.utilities.Constants;
import com.example.trvavelguidassistant.utilities.PreferenceManager;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignInActivity extends AppCompatActivity {

    private EditText inputEmail , inputPassword;
    private MaterialButton buttonSignIn;
    private ProgressBar signInProgressbar;
    private PreferenceManager preferenceManager;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        preferenceManager = new PreferenceManager(getApplicationContext());

        ImageView imageBack = findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectSignActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        findViewById(R.id.textResendVerificationEmail).setOnClickListener(v-> startActivity(new Intent(SignInActivity.this, EmailVerificationActivity.class)));
        findViewById(R.id.textForgotPassword).setOnClickListener(v-> startActivity(new Intent(SignInActivity.this, ForgotPasswordActivity.class)));

        mAuth = FirebaseAuth.getInstance();

        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        buttonSignIn = findViewById(R.id.buttonSignIn);
        signInProgressbar = findViewById(R.id.signInProgressBar);

        buttonSignIn.setOnClickListener(view -> {
            if(inputEmail.getText().toString().trim().isEmpty()){
                Toast.makeText(SignInActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
            }else if(!Patterns.EMAIL_ADDRESS.matcher(inputEmail.getText().toString()).matches()){
                Toast.makeText(SignInActivity.this, "Enter valid Email", Toast.LENGTH_SHORT).show();
            }else if (inputPassword.getText().toString().trim().isEmpty()){
                Toast.makeText(SignInActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
            } else{
                signIn();
            }
        });
    }
    private void signIn(){
        buttonSignIn.setVisibility(View.INVISIBLE);
        signInProgressbar.setVisibility(View.VISIBLE);

        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        FirebaseFirestore database = FirebaseFirestore.getInstance();

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        if(Objects.requireNonNull(mAuth.getCurrentUser()).isEmailVerified()){
                            database.collection(Constants.KEY_COLLECTION_USERS)
                                    .whereEqualTo(Constants.KEY_EMAIL, inputEmail.getText().toString())
                                    .get()
                                    .addOnCompleteListener(task2 -> {
                                        if(task2.isSuccessful() && task2.getResult().getDocuments().size()>0){
                                            DocumentSnapshot documentSnapshot = task2.getResult().getDocuments().get(0);
                                            preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN,true);
                                            preferenceManager.putString(Constants.KEY_USER_ID, documentSnapshot.getId());
                                            preferenceManager.putString(Constants.KEY_FIRST_NAME , documentSnapshot.getString(Constants.KEY_FIRST_NAME));
                                            preferenceManager.putString(Constants.KEY_LAST_NAME, documentSnapshot.getString(Constants.KEY_LAST_NAME));
                                            preferenceManager.putString(Constants.KEY_ID_NUMBER, documentSnapshot.getString(Constants.KEY_ID_NUMBER));
                                            preferenceManager.putString(Constants.KEY_EMAIL, documentSnapshot.getString(Constants.KEY_EMAIL));
                                            preferenceManager.putString(Constants.KEY_OLD_EMAIL, documentSnapshot.getString(Constants.KEY_OLD_EMAIL));
                                            preferenceManager.putString(Constants.KEY_PROFILE_PICTURE, documentSnapshot.getString(Constants.KEY_PROFILE_PICTURE));
                                            preferenceManager.putString(Constants.KEY_LOG_NUMBER, documentSnapshot.getString(Constants.KEY_LOG_NUMBER));
                                            Toast.makeText(SignInActivity.this, "Logged in Successfully ", Toast.LENGTH_SHORT).show();
                                            inputEmail.getText().clear();
                                            inputPassword.getText().clear();
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                        } else {
                                            database.collection(Constants.KEY_COLLECTION_USERS)
                                                    .whereEqualTo(Constants.KEY_OLD_EMAIL, inputEmail.getText().toString())
                                                    .get()
                                                    .addOnCompleteListener(task3 -> {
                                                        if(task3.isSuccessful() && task3.getResult().getDocuments().size()>0){

                                                            Map<String,Object> userDetails = new HashMap<>();
                                                            String oldEm = "";
                                                            String newEm = inputEmail.getText().toString();
                                                            userDetails.put(Constants.KEY_EMAIL,newEm);
                                                            userDetails.put(Constants.KEY_OLD_EMAIL,oldEm);

                                                            DocumentSnapshot documentSnapshot2 = task3.getResult().getDocuments().get(0);
                                                            String documentID = documentSnapshot2.getId();
                                                            database.collection(Constants.KEY_COLLECTION_USERS)
                                                                    .document(documentID)
                                                                    .update(userDetails);

                                                            database.collection(Constants.KEY_COLLECTION_USERS)
                                                                    .whereEqualTo(Constants.KEY_EMAIL, inputEmail.getText().toString())
                                                                    .get()
                                                                    .addOnCompleteListener(task4 -> {
                                                                        if (task4.isSuccessful() && task4.getResult().getDocuments().size() > 0) {
                                                                            DocumentSnapshot documentSnapshot = task4.getResult().getDocuments().get(0);
                                                                            preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                                                                            preferenceManager.putString(Constants.KEY_USER_ID, documentSnapshot.getId());
                                                                            preferenceManager.putString(Constants.KEY_FIRST_NAME, documentSnapshot.getString(Constants.KEY_FIRST_NAME));
                                                                            preferenceManager.putString(Constants.KEY_LAST_NAME, documentSnapshot.getString(Constants.KEY_LAST_NAME));
                                                                            preferenceManager.putString(Constants.KEY_ID_NUMBER, documentSnapshot.getString(Constants.KEY_ID_NUMBER));
                                                                            preferenceManager.putString(Constants.KEY_EMAIL, documentSnapshot.getString(Constants.KEY_EMAIL));
                                                                            preferenceManager.putString(Constants.KEY_OLD_EMAIL, documentSnapshot.getString(Constants.KEY_OLD_EMAIL));
                                                                            preferenceManager.putString(Constants.KEY_PROFILE_PICTURE, documentSnapshot.getString(Constants.KEY_PROFILE_PICTURE));
                                                                            preferenceManager.putString(Constants.KEY_LOG_NUMBER, documentSnapshot.getString(Constants.KEY_LOG_NUMBER));
                                                                            Toast.makeText(SignInActivity.this, "Logged in Successfully ", Toast.LENGTH_SHORT).show();
                                                                            inputEmail.getText().clear();
                                                                            inputPassword.getText().clear();
                                                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                            startActivity(intent);
                                                                        }
                                                                    });
                                                        } else {
                                                            signInProgressbar.setVisibility(View.INVISIBLE);
                                                            buttonSignIn.setVisibility(View.VISIBLE);
                                                            Toast.makeText(SignInActivity.this, "Error logging to account", Toast.LENGTH_SHORT).show();
                                                            inputPassword.getText().clear();
                                                        }
                                                    });
                                        }
                                    });

                        }else{
                            signInProgressbar.setVisibility(View.INVISIBLE);
                            buttonSignIn.setVisibility(View.VISIBLE);
                            Toast.makeText(SignInActivity.this, "Please Verify Your Email", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        signInProgressbar.setVisibility(View.INVISIBLE);
                        buttonSignIn.setVisibility(View.VISIBLE);
                        Toast.makeText(SignInActivity.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                        inputPassword.getText().clear();
                    }
                });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), SelectSignActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}