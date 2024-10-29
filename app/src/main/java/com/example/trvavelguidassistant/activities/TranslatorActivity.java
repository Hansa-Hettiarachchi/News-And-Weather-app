package com.example.trvavelguidassistant.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trvavelguidassistant.R;
import com.example.trvavelguidassistant.utilities.Constants;
import com.example.trvavelguidassistant.utilities.PreferenceManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class TranslatorActivity extends AppCompatActivity {

    private TextInputEditText sourceText;
    private TextView translateTV;
    String[] fromLanguage = {"From", "English", "Afrikaans", "Arabic", "Belarusian", "Bulgarian", "Bengali", "Catalan", "Czech",
            "Welsh", "Hindi", "Urdu"};
    String[] toLanguage = {"To", "English", "Afrikaans", "Arabic", "Belarusian", "Bulgarian", "Bengali", "Catalan", "Czech",
            "Welsh", "Hindi", "Urdu"};
    private static final int REQUEST_PERMISSION_CODE = 1;
    int languageCode, fromLanguageCode, toLanguageCode = 0;

    SweetAlertDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translator);

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

        Spinner fromSpinner = findViewById(R.id.idFromSpinner);
        Spinner toSpinner = findViewById(R.id.idToSpinner);
        sourceText = findViewById(R.id.idEditSource);
        ImageView micIV = findViewById(R.id.idIVMic);
        MaterialButton translateBtn = findViewById(R.id.idBtnTranslation);
        setUpProgressDialog();
        translateTV = findViewById(R.id.idTranslatedTV);
        fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fromLanguageCode = getLanguageCode(fromLanguage[i]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ArrayAdapter fromAdapter = new ArrayAdapter(this, R.layout.spinner_item, fromLanguage);
        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(fromAdapter);
        toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                toLanguageCode = getLanguageCode(toLanguage[i]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ArrayAdapter toAdapter = new ArrayAdapter(this, R.layout.spinner_item, toLanguage);
        toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSpinner.setAdapter(toAdapter);

        micIV.setOnClickListener(view -> {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say something to translate");
            try {
                startActivityForResult(intent, REQUEST_PERMISSION_CODE);
            }catch (Exception e){
                Toast.makeText(TranslatorActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        translateBtn.setOnClickListener(view -> {
            translateTV.setVisibility(View.VISIBLE);
            translateTV.setText("");
            if (sourceText.getText().toString().isEmpty()){
                Toast.makeText(TranslatorActivity.this, "Please enter text to translate", Toast.LENGTH_SHORT).show();
            }else if (fromLanguageCode == 0){
                Toast.makeText(TranslatorActivity.this, "Please select Source Language", Toast.LENGTH_SHORT).show();
            }else if (toLanguageCode == 0){
                Toast.makeText(TranslatorActivity.this, "Please select the language to make translation", Toast.LENGTH_SHORT).show();
            }else{
                    prepareModel(fromLanguageCode,toLanguageCode);
            }
        });
    }

    private void setUpProgressDialog() {
        pDialog = new SweetAlertDialog(TranslatorActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
    }

    private void prepareModel(int fromLanguageCode , int toLanguageCode) {
        String source= "",target = "";

        switch (fromLanguageCode){
            //English
            case 1:
                switch (toLanguageCode) {
                    case 1: source =TranslateLanguage.ENGLISH; target = TranslateLanguage.ENGLISH; break;
                    case 2: source =TranslateLanguage.ENGLISH; target = TranslateLanguage.AFRIKAANS; break;
                    case 3: source =TranslateLanguage.ENGLISH; target = TranslateLanguage.ARABIC; break;
                    case 4: source =TranslateLanguage.ENGLISH; target = TranslateLanguage.BELARUSIAN; break;
                    case 5: source =TranslateLanguage.ENGLISH; target = TranslateLanguage.BULGARIAN; break;
                    case 6: source =TranslateLanguage.ENGLISH; target = TranslateLanguage.BENGALI; break;
                    case 7: source =TranslateLanguage.ENGLISH; target = TranslateLanguage.CATALAN; break;
                    case 8: source =TranslateLanguage.ENGLISH; target = TranslateLanguage.CZECH; break;
                    case 9: source =TranslateLanguage.ENGLISH; target = TranslateLanguage.WELSH; break;
                    case 10: source =TranslateLanguage.ENGLISH; target = TranslateLanguage.HINDI; break;
                    case 11: source =TranslateLanguage.ENGLISH; target = TranslateLanguage.URDU; break;
                }
                break;
            //Afrikaans
            case 2:
                switch (toLanguageCode) {
                    case 1: source =TranslateLanguage.AFRIKAANS; target = TranslateLanguage.ENGLISH; break;
                    case 2: source =TranslateLanguage.AFRIKAANS; target = TranslateLanguage.AFRIKAANS; break;
                    case 3: source =TranslateLanguage.AFRIKAANS; target = TranslateLanguage.ARABIC; break;
                    case 4: source =TranslateLanguage.AFRIKAANS; target = TranslateLanguage.BELARUSIAN; break;
                    case 5: source =TranslateLanguage.AFRIKAANS; target = TranslateLanguage.BULGARIAN; break;
                    case 6: source =TranslateLanguage.AFRIKAANS; target = TranslateLanguage.BENGALI; break;
                    case 7: source =TranslateLanguage.AFRIKAANS; target = TranslateLanguage.CATALAN; break;
                    case 8: source =TranslateLanguage.AFRIKAANS; target = TranslateLanguage.CZECH; break;
                    case 9: source =TranslateLanguage.AFRIKAANS; target = TranslateLanguage.WELSH; break;
                    case 10: source = TranslateLanguage.AFRIKAANS; target = TranslateLanguage.HINDI; break;
                    case 11: source = TranslateLanguage.AFRIKAANS; target = TranslateLanguage.URDU; break;
                }
                break;
            //Arabic
            case 3:
                switch (toLanguageCode) {
                    case 1: source =TranslateLanguage.ARABIC; target = TranslateLanguage.ENGLISH; break;
                    case 2: source =TranslateLanguage.ARABIC; target = TranslateLanguage.AFRIKAANS; break;
                    case 3: source =TranslateLanguage.ARABIC; target = TranslateLanguage.ARABIC; break;
                    case 4: source =TranslateLanguage.ARABIC; target = TranslateLanguage.BELARUSIAN; break;
                    case 5: source =TranslateLanguage.ARABIC; target = TranslateLanguage.BULGARIAN; break;
                    case 6: source =TranslateLanguage.ARABIC; target = TranslateLanguage.BENGALI; break;
                    case 7: source =TranslateLanguage.ARABIC; target = TranslateLanguage.CATALAN; break;
                    case 8: source =TranslateLanguage.ARABIC; target = TranslateLanguage.CZECH; break;
                    case 9: source =TranslateLanguage.ARABIC; target = TranslateLanguage.WELSH; break;
                    case 10: source = TranslateLanguage.ARABIC; target = TranslateLanguage.HINDI; break;
                    case 11: source = TranslateLanguage.ARABIC; target = TranslateLanguage.URDU; break;
                }
                break;
            //Belarusian
            case 4:
                switch (toLanguageCode) {
                    case 1: source =TranslateLanguage.BELARUSIAN; target = TranslateLanguage.ENGLISH; break;
                    case 2: source =TranslateLanguage.BELARUSIAN; target = TranslateLanguage.AFRIKAANS; break;
                    case 3: source =TranslateLanguage.BELARUSIAN; target = TranslateLanguage.ARABIC; break;
                    case 4: source =TranslateLanguage.BELARUSIAN; target = TranslateLanguage.BELARUSIAN; break;
                    case 5: source =TranslateLanguage.BELARUSIAN; target = TranslateLanguage.BULGARIAN; break;
                    case 6: source =TranslateLanguage.BELARUSIAN; target = TranslateLanguage.BENGALI; break;
                    case 7: source =TranslateLanguage.BELARUSIAN; target = TranslateLanguage.CATALAN; break;
                    case 8: source =TranslateLanguage.BELARUSIAN; target = TranslateLanguage.CZECH; break;
                    case 9: source =TranslateLanguage.BELARUSIAN; target = TranslateLanguage.WELSH; break;
                    case 10: source =TranslateLanguage.BELARUSIAN; target = TranslateLanguage.HINDI; break;
                    case 11: source =TranslateLanguage.BELARUSIAN; target = TranslateLanguage.URDU; break;
                }
                break;
            //Bulgarian
            case 5:
                switch (toLanguageCode) {
                    case 1: source =TranslateLanguage.BULGARIAN; target = TranslateLanguage.ENGLISH; break;
                    case 2: source =TranslateLanguage.BULGARIAN; target = TranslateLanguage.AFRIKAANS; break;
                    case 3: source =TranslateLanguage.BULGARIAN; target = TranslateLanguage.ARABIC; break;
                    case 4: source =TranslateLanguage.BULGARIAN; target = TranslateLanguage.BELARUSIAN; break;
                    case 5: source =TranslateLanguage.BULGARIAN; target = TranslateLanguage.BULGARIAN; break;
                    case 6: source =TranslateLanguage.BULGARIAN; target = TranslateLanguage.BENGALI; break;
                    case 7: source =TranslateLanguage.BULGARIAN; target = TranslateLanguage.CATALAN; break;
                    case 8: source =TranslateLanguage.BULGARIAN; target = TranslateLanguage.CZECH; break;
                    case 9: source =TranslateLanguage.BULGARIAN; target = TranslateLanguage.WELSH; break;
                    case 10: source = TranslateLanguage.BULGARIAN; target = TranslateLanguage.HINDI; break;
                    case 11: source = TranslateLanguage.BULGARIAN; target = TranslateLanguage.URDU; break;
                }
                break;
            //Bengali
            case 6:
                switch (toLanguageCode) {
                    case 1: source =TranslateLanguage.BENGALI; target = TranslateLanguage.ENGLISH; break;
                    case 2: source =TranslateLanguage.BENGALI; target = TranslateLanguage.AFRIKAANS; break;
                    case 3: source =TranslateLanguage.BENGALI; target = TranslateLanguage.ARABIC; break;
                    case 4: source =TranslateLanguage.BENGALI; target = TranslateLanguage.BELARUSIAN; break;
                    case 5: source =TranslateLanguage.BENGALI; target = TranslateLanguage.BULGARIAN; break;
                    case 6: source =TranslateLanguage.BENGALI; target = TranslateLanguage.BENGALI; break;
                    case 7: source =TranslateLanguage.BENGALI; target = TranslateLanguage.CATALAN; break;
                    case 8: source =TranslateLanguage.BENGALI; target = TranslateLanguage.CZECH; break;
                    case 9: source =TranslateLanguage.BENGALI; target = TranslateLanguage.WELSH; break;
                    case 10: source = TranslateLanguage.BENGALI; target = TranslateLanguage.HINDI; break;
                    case 11: source = TranslateLanguage.BENGALI; target = TranslateLanguage.URDU; break;
                }
                break;
            //Catalan
            case 7:
                switch (toLanguageCode) {
                    case 1: source =TranslateLanguage.CATALAN; target = TranslateLanguage.ENGLISH; break;
                    case 2: source =TranslateLanguage.CATALAN; target = TranslateLanguage.AFRIKAANS; break;
                    case 3: source =TranslateLanguage.CATALAN; target = TranslateLanguage.ARABIC; break;
                    case 4: source =TranslateLanguage.CATALAN; target = TranslateLanguage.BELARUSIAN; break;
                    case 5: source =TranslateLanguage.CATALAN; target = TranslateLanguage.BULGARIAN; break;
                    case 6: source =TranslateLanguage.CATALAN; target = TranslateLanguage.BENGALI; break;
                    case 7: source =TranslateLanguage.CATALAN; target = TranslateLanguage.CATALAN; break;
                    case 8: source =TranslateLanguage.CATALAN; target = TranslateLanguage.CZECH; break;
                    case 9: source =TranslateLanguage.CATALAN; target = TranslateLanguage.WELSH; break;
                    case 10: source = TranslateLanguage.CATALAN; target = TranslateLanguage.HINDI; break;
                    case 11: source = TranslateLanguage.CATALAN; target = TranslateLanguage.URDU; break;
                }
                break;
            //Czech
            case 8:
                switch (toLanguageCode) {
                    case 1: source =TranslateLanguage.CZECH; target = TranslateLanguage.ENGLISH; break;
                    case 2: source =TranslateLanguage.CZECH; target = TranslateLanguage.AFRIKAANS; break;
                    case 3: source =TranslateLanguage.CZECH; target = TranslateLanguage.ARABIC; break;
                    case 4: source =TranslateLanguage.CZECH; target = TranslateLanguage.BELARUSIAN; break;
                    case 5: source =TranslateLanguage.CZECH; target = TranslateLanguage.BULGARIAN; break;
                    case 6: source =TranslateLanguage.CZECH; target = TranslateLanguage.BENGALI; break;
                    case 7: source =TranslateLanguage.CZECH; target = TranslateLanguage.CATALAN; break;
                    case 8: source =TranslateLanguage.CZECH; target = TranslateLanguage.CZECH; break;
                    case 9: source =TranslateLanguage.CZECH; target = TranslateLanguage.WELSH; break;
                    case 10: source = TranslateLanguage.CZECH; target = TranslateLanguage.HINDI; break;
                    case 11: source = TranslateLanguage.CZECH; target = TranslateLanguage.URDU; break;
                }
                break;
            //Welsh
            case 9:
                switch (toLanguageCode) {
                    case 1: source =TranslateLanguage.WELSH; target = TranslateLanguage.ENGLISH; break;
                    case 2: source =TranslateLanguage.WELSH; target = TranslateLanguage.AFRIKAANS; break;
                    case 3: source =TranslateLanguage.WELSH; target = TranslateLanguage.ARABIC; break;
                    case 4: source =TranslateLanguage.WELSH; target = TranslateLanguage.BELARUSIAN; break;
                    case 5: source =TranslateLanguage.WELSH; target = TranslateLanguage.BULGARIAN; break;
                    case 6: source =TranslateLanguage.WELSH; target = TranslateLanguage.BENGALI; break;
                    case 7: source =TranslateLanguage.WELSH; target = TranslateLanguage.CATALAN; break;
                    case 8: source =TranslateLanguage.WELSH; target = TranslateLanguage.CZECH; break;
                    case 9: source =TranslateLanguage.WELSH; target = TranslateLanguage.WELSH; break;
                    case 10: source = TranslateLanguage.WELSH; target = TranslateLanguage.HINDI; break;
                    case 11: source = TranslateLanguage.WELSH; target = TranslateLanguage.URDU; break;
                }
                break;
            //Hindi
            case 10:
                switch (toLanguageCode) {
                    case 1: source =TranslateLanguage.HINDI; target = TranslateLanguage.ENGLISH; break;
                    case 2: source =TranslateLanguage.HINDI; target = TranslateLanguage.AFRIKAANS; break;
                    case 3: source =TranslateLanguage.HINDI; target = TranslateLanguage.ARABIC; break;
                    case 4: source =TranslateLanguage.HINDI; target = TranslateLanguage.BELARUSIAN; break;
                    case 5: source =TranslateLanguage.HINDI; target = TranslateLanguage.BULGARIAN; break;
                    case 6: source =TranslateLanguage.HINDI; target = TranslateLanguage.BENGALI; break;
                    case 7: source =TranslateLanguage.HINDI; target = TranslateLanguage.CATALAN; break;
                    case 8: source =TranslateLanguage.HINDI; target = TranslateLanguage.CZECH; break;
                    case 9: source =TranslateLanguage.HINDI; target = TranslateLanguage.WELSH; break;
                    case 10: source = TranslateLanguage.HINDI; target = TranslateLanguage.HINDI; break;
                    case 11: source = TranslateLanguage.HINDI; target = TranslateLanguage.URDU; break;
                }
                break;
            //Urdu
            case 11:
                switch (toLanguageCode) {
                    case 1: source =TranslateLanguage.URDU; target = TranslateLanguage.ENGLISH; break;
                    case 2: source =TranslateLanguage.URDU; target = TranslateLanguage.AFRIKAANS; break;
                    case 3: source =TranslateLanguage.URDU; target = TranslateLanguage.ARABIC; break;
                    case 4: source =TranslateLanguage.URDU; target = TranslateLanguage.BELARUSIAN; break;
                    case 5: source =TranslateLanguage.URDU; target = TranslateLanguage.BULGARIAN; break;
                    case 6: source =TranslateLanguage.URDU; target = TranslateLanguage.BENGALI; break;
                    case 7: source =TranslateLanguage.URDU; target = TranslateLanguage.CATALAN; break;
                    case 8: source =TranslateLanguage.URDU; target = TranslateLanguage.CZECH; break;
                    case 9: source =TranslateLanguage.URDU; target = TranslateLanguage.WELSH; break;
                    case 10: source = TranslateLanguage.URDU; target = TranslateLanguage.HINDI; break;
                    case 11: source = TranslateLanguage.URDU; target = TranslateLanguage.URDU; break;
                }
                break;
        }
        TranslateLang(source,target);


    }

    private void TranslateLang(String SourceLang,String targetLang) {
        TranslatorOptions options =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(SourceLang)
                        .setTargetLanguage(targetLang)
                        .build();
        Translator translator =
                Translation.getClient(options);
        pDialog.setTitleText("Translate Model Downloading...");
        pDialog.show();
        translator.downloadModelIfNeeded()
                .addOnSuccessListener(unused -> {
                    pDialog.dismissWithAnimation();
                    pDialog.setTitleText("Language Translating...");
                    pDialog.show();
                    String text = Objects.requireNonNull(sourceText.getText()).toString();
                    translator.translate(text)
                            .addOnSuccessListener(s -> {
                                pDialog.dismissWithAnimation();
                                translateTV.setText(s);
                            })
                            .addOnFailureListener(
                                    e -> {
                                        pDialog.dismissWithAnimation();
                                        Toast.makeText(TranslatorActivity.this, "error in translate", Toast.LENGTH_SHORT).show();
                                    });
                })
                .addOnFailureListener(
                        e -> {
                            pDialog.dismissWithAnimation();
                            Toast.makeText(TranslatorActivity.this, "Error in model download", Toast.LENGTH_SHORT).show();
                        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_CODE){
            assert data != null;
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            sourceText.setText(result.get(0));
        }
    }

    private int getLanguageCode(String language) {
        languageCode = 0;
        switch (language){
            case "English":
                languageCode = 1;
                break;
            case "Afrikaans":
                languageCode = 2;
                break;
            case "Arabic":
                languageCode = 3;
                break;
            case "Belarusian":
                languageCode = 4;
                break;
            case "Bulgarian":
                languageCode = 5;
                break;
            case "Bengali":
                languageCode = 6;
                break;
            case "Catalan":
                languageCode = 7;
                break;
            case "Czech":
                languageCode = 8;
                break;
            case "Welsh":
                languageCode = 9;
                break;
            case "Hindi":
                languageCode = 10;
                break;
            case "Urdu":
                languageCode = 11;
                break;
            default:
        }
        return languageCode;
    }
}