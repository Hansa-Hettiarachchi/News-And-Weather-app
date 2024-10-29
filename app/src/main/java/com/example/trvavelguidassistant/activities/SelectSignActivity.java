package com.example.trvavelguidassistant.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trvavelguidassistant.R;
import com.example.trvavelguidassistant.utilities.Constants;
import com.example.trvavelguidassistant.utilities.PreferenceManager;
import com.google.android.material.button.MaterialButton;

public class SelectSignActivity extends AppCompatActivity {

    MaterialButton buttonSignIn1,buttonSignUp1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_sign);

        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        if (preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN)){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

        buttonSignIn1 = findViewById(R.id.buttonSignIn1);
        buttonSignUp1 = findViewById(R.id.buttonSignUp1);

        buttonSignIn1.setOnClickListener(v -> {
            Intent intent = new Intent(SelectSignActivity.this, SignInActivity.class);
            startActivity(intent);
        });

        buttonSignUp1.setOnClickListener(v -> {
            Intent intent = new Intent(SelectSignActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("EXIT");
        builder.setMessage("Are you sure you want to exit?");

        builder.setPositiveButton("EXIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.show();
    }
}