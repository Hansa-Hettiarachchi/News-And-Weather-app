package com.example.trvavelguidassistant.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trvavelguidassistant.R;
import com.example.trvavelguidassistant.utilities.Constants;
import com.example.trvavelguidassistant.utilities.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SchedulerActivity extends AppCompatActivity {

    private CalendarView calender;
    private TextView selectDate,addNewEvent,viewTodayEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler);

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
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


        calender =(CalendarView) findViewById(R.id.calender);
        selectDate = findViewById(R.id.selectDate);
        addNewEvent =findViewById(R.id.addNewEvent);

        viewTodayEvent = findViewById(R.id.viewTodayEvent);

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        selectDate.setText(date);

        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month = month+1;
                String dt1;
                String mn;
                if (month==1||month==2||month==3||month==4||month==5||month==6||month==7||month==8||month==9) {
                    mn = "0" + String.valueOf(month);
                } else{
                    mn = String.valueOf(month);
                }
                if (dayOfMonth ==1||dayOfMonth ==2||dayOfMonth ==3||dayOfMonth ==4||dayOfMonth ==5||dayOfMonth ==6||dayOfMonth ==7||dayOfMonth ==8||dayOfMonth ==9){
                    dt1 = "0" + String.valueOf(dayOfMonth);
                } else{
                    dt1 = String.valueOf(dayOfMonth);
                }
                String dt = year+"-"+mn+"-"+dt1;
                selectDate.setText(dt);
            }
        });

        addNewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date1 = selectDate.getText().toString();
                Intent intent = new Intent(SchedulerActivity.this, AddNewEventActivity.class);
                intent.putExtra("date",date1);
                startActivity(intent);
            }
        });

        viewTodayEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date1 = selectDate.getText().toString();
                Intent intent = new Intent(SchedulerActivity.this, ViewTodayEventActivity.class);
                intent.putExtra("date",date1);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}