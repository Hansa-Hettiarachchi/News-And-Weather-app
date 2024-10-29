package com.example.trvavelguidassistant.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trvavelguidassistant.R;
import com.example.trvavelguidassistant.adapter.EventAdapter;
import com.example.trvavelguidassistant.adapter.LogAdapter;
import com.example.trvavelguidassistant.model.EventModel;
import com.example.trvavelguidassistant.model.LogModel;
import com.example.trvavelguidassistant.utilities.Constants;
import com.example.trvavelguidassistant.utilities.PreferenceManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewTodayEventActivity extends AppCompatActivity {

    TextView dateSet;
    RecyclerView recyclerView1;
    ArrayList<EventModel> EventList;
    EventAdapter adapter;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_today_event);

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

        dateSet = findViewById(R.id.dateSet);
        String fullDate = getIntent().getStringExtra("date");
        dateSet.setText(fullDate);

        String userID = preferenceManager.getString(Constants.KEY_USER_ID);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Event/"+userID+"/"+fullDate);
        recyclerView1 = findViewById(R.id.recyclerView1);

        EventList = new ArrayList<>();
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EventAdapter(this,EventList);
        recyclerView1.setAdapter(adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    EventModel eventModel = dataSnapshot.getValue(EventModel.class);
                    EventList.add(eventModel);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}