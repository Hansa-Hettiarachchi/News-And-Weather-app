package com.example.trvavelguidassistant.activities;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trvavelguidassistant.R;
import com.example.trvavelguidassistant.utilities.Constants;
import com.example.trvavelguidassistant.utilities.PreferenceManager;
import com.google.android.material.button.MaterialButton;
import com.google.mlkit.nl.languageid.LanguageIdentification;
import com.google.mlkit.nl.languageid.LanguageIdentifier;

public class IdentifyLanguageActivity extends AppCompatActivity {

    TextView textIdentifiedLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_language);

        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        TextView textTitle = findViewById(R.id.textTitle);
        textTitle.setText(String.format(
                "%s %s",
                preferenceManager.getString(Constants.KEY_FIRST_NAME),
                preferenceManager.getString(Constants.KEY_LAST_NAME)
        ));

        //go back
        ImageView imageBack = findViewById(R.id.imageBack);
        imageBack.setOnClickListener(v -> onBackPressed());

        EditText enterLanguageIdentifyText = findViewById(R.id.enterLanguageIdentifyText);
        textIdentifiedLanguage = findViewById(R.id.textIdentifiedLanguage);
        MaterialButton identifyLangBtn = findViewById(R.id.identifyLangBtn);

        identifyLangBtn.setOnClickListener(v -> {
            String text = enterLanguageIdentifyText.getText().toString();
            if(!text.isEmpty()){
                identifyLanguage(text);
            }else{
                Toast.makeText(IdentifyLanguageActivity.this, "Enter text you want to identify", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void identifyLanguage(String text) {
        LanguageIdentifier languageIdentifier =
                LanguageIdentification.getClient();
        languageIdentifier.identifyLanguage(text)
                .addOnSuccessListener(
                        languageCode -> {
                            if (languageCode.equals("und")) {
                                Toast.makeText(IdentifyLanguageActivity.this, "Can't Identify Language", Toast.LENGTH_SHORT).show();
                            } else {
                                textIdentifiedLanguage.setText(languageCode);
                                Toast.makeText(IdentifyLanguageActivity.this, "Successfully Identified the Language", Toast.LENGTH_SHORT).show();

                            }
                        })
                .addOnFailureListener(
                        e -> Toast.makeText(IdentifyLanguageActivity.this, "Can't Identify Language", Toast.LENGTH_SHORT).show());
    }
}