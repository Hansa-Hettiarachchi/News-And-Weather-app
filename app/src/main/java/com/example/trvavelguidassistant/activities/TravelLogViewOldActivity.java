package com.example.trvavelguidassistant.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trvavelguidassistant.R;
import com.example.trvavelguidassistant.adapter.LogAdapter;
import com.example.trvavelguidassistant.model.LogModel;
import com.example.trvavelguidassistant.utilities.Constants;
import com.example.trvavelguidassistant.utilities.PreferenceManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class TravelLogViewOldActivity extends AppCompatActivity implements LogAdapter.logClickListener{

    RecyclerView recyclerView;
    private PreferenceManager preferenceManager;
    ArrayList<LogModel> logList,logListFilter;
    ArrayList<String> dateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_log_view_old);

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
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Logs/"+userID);

        recyclerView = findViewById(R.id.recyclerView);

        dateList = new ArrayList<>();

        //search
        SearchView searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        dateList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String Key = dataSnapshot.getKey();
                            dateList.add(Key);
                        }
                        logListFilter = new ArrayList<>();
                        for (int i = 1; i <= dateList.size(); i++) {
                            String userID = preferenceManager.getString(Constants.KEY_USER_ID);
                            DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Logs/"+userID + "/" + dateList.get(i-1));
                            int finalI = i-1;
                            databaseReference2.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                        String Key2 = dataSnapshot.getKey();
                                        String value = dataSnapshot.child("name").getValue(String.class);
                                        String ds=dataSnapshot.child("description").getValue(String.class);
                                        assert Key2 != null;
                                        recyclerView.setLayoutManager(new LinearLayoutManager(TravelLogViewOldActivity.this));
                                        recyclerView.setHasFixedSize(true);
                                        String d = dateList.get(finalI);
                                        String d1 = d.substring(0,2);
                                        String d2 = d.substring(2,4);
                                        String d3 = d.substring(4,6);
                                        String full = d1+"/"+d2+"/"+d3;
                                        assert value != null;
                                        if(value.toLowerCase().contains(newText.toLowerCase(Locale.ROOT)) || Key2.equals(newText) ||full.equals(newText)) {
                                            logListFilter.add(new LogModel(full, value, Integer.parseInt(Key2),ds));
                                        }
                                        LogAdapter adapter = new LogAdapter(TravelLogViewOldActivity.this, logListFilter,TravelLogViewOldActivity.this::selectedLog);
                                        recyclerView.setAdapter(adapter);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                return true;
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dateList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String Key = dataSnapshot.getKey();
                    dateList.add(Key);
                }

                logList = new ArrayList<>();

                for (int i = 1; i <= dateList.size(); i++) {

                    String userID = preferenceManager.getString(Constants.KEY_USER_ID);

                    DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Logs/"+userID + "/" + dateList.get(i-1));

                    int finalI = i-1;

                    databaseReference2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                String Key2 = dataSnapshot.getKey();
                                String value = dataSnapshot.child("name").getValue(String.class);
                                String ds=dataSnapshot.child("description").getValue(String.class);
                                assert Key2 != null;
                                recyclerView.setLayoutManager(new LinearLayoutManager(TravelLogViewOldActivity.this));
                                recyclerView.setHasFixedSize(true);
                                String d = dateList.get(finalI);
                                String d1 = d.substring(0,2);
                                String d2 = d.substring(2,4);
                                String d3 = d.substring(4,6);
                                String full = d1+"/"+d2+"/"+d3;
                                logList.add(new LogModel(full, value, Integer.parseInt(Key2),ds));
                                LogAdapter adapter = new LogAdapter(TravelLogViewOldActivity.this, logList,TravelLogViewOldActivity.this::selectedLog);
                                recyclerView.setAdapter(adapter);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void selectedLog(LogModel lgm) {
        Toast.makeText(this, "Selected Log : "+lgm.getName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(TravelLogViewOldActivity.this, SelectedLogActivity.class);
        intent.putExtra("logName1",lgm.getName());
        intent.putExtra("logNumber1",lgm.getNum());
        intent.putExtra("logDate1",lgm.getDate());
        intent.putExtra("logDs1",lgm.getDs());
        startActivity(intent);
    }
}