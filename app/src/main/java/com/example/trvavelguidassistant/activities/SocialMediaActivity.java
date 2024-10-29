package com.example.trvavelguidassistant.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trvavelguidassistant.R;
import com.example.trvavelguidassistant.adapter.LogAdapter;
import com.example.trvavelguidassistant.adapter.SocialMediaAdapter;
import com.example.trvavelguidassistant.model.LogModel;
import com.example.trvavelguidassistant.model.SocialMediaModel;
import com.example.trvavelguidassistant.utilities.Constants;
import com.example.trvavelguidassistant.utilities.LikeSocialMediaSelectItem;
import com.example.trvavelguidassistant.utilities.PreferenceManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class SocialMediaActivity extends AppCompatActivity implements LikeSocialMediaSelectItem {

    RecyclerView recyclerView3;
    private PreferenceManager preferenceManager;
    SocialMediaAdapter socialMediaAdapter;
    ArrayList<SocialMediaModel> SMList;
    ImageView homeImg,addImg,accountImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);

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
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });




        //homeImg = findViewById(R.id.homeImg);
        addImg = findViewById(R.id.addImg);
        //homeText = findViewById(R.id.homeText);
        accountImg = findViewById(R.id.accountImg);

        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocialMediaActivity.this, AddSocialMediaActivity.class);
                startActivity(intent);
            }
        });

        accountImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocialMediaActivity.this, SocialMediaAccountActivity.class);
                startActivity(intent);
            }
        });

        recyclerView3 = findViewById(R.id.recyclerView3);
        DatabaseReference databaseReference5 = FirebaseDatabase.getInstance().getReference("SocialMedia");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView3.setHasFixedSize(true);

        recyclerView3.setLayoutManager(layoutManager);

        SMList = new ArrayList<>();
        socialMediaAdapter = new SocialMediaAdapter(this,SMList,this);
        recyclerView3.setAdapter(socialMediaAdapter);


        databaseReference5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SMList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    SocialMediaModel socialMediaModel = dataSnapshot.getValue(SocialMediaModel.class);
                    SMList.add(socialMediaModel);
                }
                socialMediaAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //search
        SearchView searchView1 = findViewById(R.id.searchView1);
        searchView1.clearFocus();
        searchView1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                databaseReference5.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        SMList.clear();
                        for(DataSnapshot dataSnapshot :snapshot.getChildren()){
                            String value = dataSnapshot.child("userName").getValue(String.class);
                            value = value.toLowerCase(Locale.ROOT);
                            String value2 = dataSnapshot.child("location").getValue(String.class);
                            value2 = value2.toLowerCase(Locale.ROOT);
                            if (value.contains(newText.toLowerCase(Locale.ROOT)) || value2.contains(newText.toLowerCase(Locale.ROOT))){
                                SocialMediaModel socialMediaModel = dataSnapshot.getValue(SocialMediaModel.class);
                                SMList.add(socialMediaModel);
                            }
                            socialMediaAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                return true;
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

    @Override
    public void onItemClicked(SocialMediaModel socialMediaModel) {
        Long count = socialMediaModel.getLikeCount() +1;

        DatabaseReference databaseReference6 = FirebaseDatabase.getInstance().getReference("SocialMedia");
        databaseReference6 = databaseReference6.child(socialMediaModel.getKey5());
        databaseReference6.child("likeCount").setValue(count);
    }
}