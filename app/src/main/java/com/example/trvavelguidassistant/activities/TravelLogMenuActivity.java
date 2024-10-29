package com.example.trvavelguidassistant.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.trvavelguidassistant.R;
import com.example.trvavelguidassistant.utilities.Constants;
import com.example.trvavelguidassistant.utilities.PreferenceManager;

public class TravelLogMenuActivity extends AppCompatActivity {

    CardView cardUpload,cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_log_menu);

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

        cardUpload = findViewById(R.id.cardUpload);
        cardView = findViewById(R.id.cardView);

        cardUpload.setOnClickListener(v -> {
            Intent intent = new Intent(TravelLogMenuActivity.this, TravelLogUploadNewActivity.class);
            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        cardView.setOnClickListener(v -> {
            Intent intent = new Intent(TravelLogMenuActivity.this, TravelLogViewOldActivity.class);
            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TravelLogMenuActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}