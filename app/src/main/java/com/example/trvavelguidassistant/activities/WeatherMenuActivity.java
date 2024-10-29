package com.example.trvavelguidassistant.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trvavelguidassistant.R;
import com.example.trvavelguidassistant.utilities.Constants;
import com.example.trvavelguidassistant.utilities.PreferenceManager;
import com.google.android.material.button.MaterialButton;

public class WeatherMenuActivity extends AppCompatActivity {

    CardView cardCurrent,cardSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_menu);

        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        //set header bar name
        TextView textTitle = findViewById(R.id.textTitle);
        textTitle.setText(String.format(
                "%s %s",
                preferenceManager.getString(Constants.KEY_FIRST_NAME),
                preferenceManager.getString(Constants.KEY_LAST_NAME)
        ));

        //go back
        ImageView imageBack = findViewById(R.id.imageBack);
        imageBack.setOnClickListener(v -> onBackPressed());

        cardCurrent = findViewById(R.id.cardCurrent);
        cardSearch = findViewById(R.id.cardSearch);

        cardCurrent.setOnClickListener(v -> {
            Intent intent = new Intent(WeatherMenuActivity.this, WeatherCurrentLocationActivity.class);
            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        cardSearch.setOnClickListener(v -> {
            Intent intent = new Intent(WeatherMenuActivity.this, WeatherCityActivity.class);
            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(WeatherMenuActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}