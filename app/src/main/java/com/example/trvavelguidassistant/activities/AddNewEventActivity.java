package com.example.trvavelguidassistant.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.trvavelguidassistant.R;
import com.example.trvavelguidassistant.utilities.Constants;
import com.example.trvavelguidassistant.utilities.PreferenceManager;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddNewEventActivity extends AppCompatActivity {

    private TextView dateSet;
    private EditText inputTitle,inputDescription;
    MaterialButton buttonAddEvent;
    ProgressBar AddNewEventProgressBar;
    private TimePicker endTime,startTime;
    DatabaseReference NewEventDatabase,NewEventDatabase1;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_event);

        preferenceManager = new PreferenceManager(getApplicationContext());

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

        String userID = preferenceManager.getString(Constants.KEY_USER_ID);
        NewEventDatabase = FirebaseDatabase.getInstance().getReference().child("Event/"+userID);

        dateSet = findViewById(R.id.dateSet);
        String fullDate = getIntent().getStringExtra("date");
        dateSet.setText(fullDate);

        inputTitle = findViewById(R.id.inputTitle);
        inputDescription = findViewById(R.id.inputDescription);
        endTime = findViewById(R.id.endTime);
        startTime = findViewById(R.id.startTime);

        buttonAddEvent = findViewById(R.id.buttonAddEvent);
        AddNewEventProgressBar = findViewById(R.id.AddNewEventProgressBar);

        buttonAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputTitle.getText().toString().trim().isEmpty()) {
                    Toast.makeText(AddNewEventActivity.this, "Enter Event Title", Toast.LENGTH_SHORT).show();
                }else{
                    buttonAddEvent.setVisibility(View.INVISIBLE);
                    AddNewEventProgressBar.setVisibility(View.VISIBLE);
                    int eHour = endTime.getCurrentHour();
                    int eMinute = endTime.getCurrentMinute();

                    int sHour = startTime.getCurrentHour();
                    int sMinute = startTime.getCurrentMinute();

                    String title = inputTitle.getText().toString();
                    String description = inputDescription.getText().toString();

                    String sTime = sHour +":"+ sMinute;
                    String eTime = eHour +":"+ eMinute;

                    NewEventDatabase1  = NewEventDatabase.child(fullDate).child(sTime);
                    NewEventDatabase1.child("Date").setValue(fullDate);
                    NewEventDatabase1.child("Title").setValue(title);
                    NewEventDatabase1.child("description").setValue(description);
                    NewEventDatabase1.child("Start").setValue(sTime);
                    NewEventDatabase1.child("End").setValue(eTime);

                    buttonAddEvent.setVisibility(View.VISIBLE);
                    AddNewEventProgressBar.setVisibility(View.INVISIBLE);

                    Toast.makeText(AddNewEventActivity.this, "New Event added", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), SchedulerActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}