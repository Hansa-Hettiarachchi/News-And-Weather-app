package com.example.trvavelguidassistant.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.trvavelguidassistant.R;

import java.util.Timer;
import java.util.TimerTask;

public class LoadingActivity extends AppCompatActivity {

    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent =new Intent(LoadingActivity.this,SelectSignActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2500);
    }
}