package com.example.trvavelguidassistant.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trvavelguidassistant.R;
import com.example.trvavelguidassistant.utilities.Constants;
import com.example.trvavelguidassistant.utilities.PreferenceManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class SelectedLogActivity extends AppCompatActivity {

    private ImageView viewImage1,viewImage2,viewImage3;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_log);
        mAuth = FirebaseAuth.getInstance();

        FirebaseStorage storage = FirebaseStorage.getInstance();
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

        TextView viewLogDescription = findViewById(R.id.viewLogDescription);
        TextView viewLogDate = findViewById(R.id.viewLogDate);
        TextView viewLogName = findViewById(R.id.viewLogName);
        TextView viewLogNumber = findViewById(R.id.viewLogNumber);

        viewLogName.setText(getIntent().getStringExtra("logName1"));
        viewLogDate.setText(getIntent().getStringExtra("logDate1"));
        viewLogDescription.setText(getIntent().getStringExtra("logDs1"));
        viewLogNumber.setText(String.valueOf(getIntent().getIntExtra("logNumber1",0)));

        viewImage1 = findViewById(R.id.viewImage1);
        viewImage2 = findViewById(R.id.viewImage2);
        viewImage3 = findViewById(R.id.viewImage3);

        String pp = preferenceManager.getString(Constants.KEY_PROFILE_PICTURE);
        String userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        String date = getIntent().getStringExtra("logDate1");
        date = date.replaceAll("/","");

        String num = String.valueOf(getIntent().getIntExtra("logNumber1",0));

        StorageReference storageReference1 = storage.getReference().child("Travel_Log/" + userID + "/" + date + "/log" + num + "/image1.jpg");
        try {
            File localFile = File.createTempFile("tempImage1","jpg");
            storageReference1.getFile(localFile)
                    .addOnSuccessListener(taskSnapshot -> {
                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        viewImage1.setImageBitmap(bitmap);
                    });
        } catch (IOException e) {
            Toast.makeText(this, "Error : Retrieving Image 1", Toast.LENGTH_SHORT).show();
        }
        StorageReference storageReference2 = storage.getReference().child("Travel_Log/" + userID + "/" + date + "/log" + num + "/image2.jpg");
        try {
            File localFile = File.createTempFile("tempImage2","jpg");
            storageReference2.getFile(localFile)
                    .addOnSuccessListener(taskSnapshot -> {
                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        viewImage2.setImageBitmap(bitmap);
                    });
        } catch (IOException e) {
            Toast.makeText(this, "Error : Retrieving Image 2", Toast.LENGTH_SHORT).show();
        }
        StorageReference storageReference3 = storage.getReference().child("Travel_Log/" + userID + "/" + date + "/log" + num + "/image3.jpg");
        try {
            File localFile = File.createTempFile("tempImage1","jpg");
            storageReference3.getFile(localFile)
                    .addOnSuccessListener(taskSnapshot -> {
                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        viewImage3.setImageBitmap(bitmap);
                    });
        } catch (IOException e) {
            Toast.makeText(this, "Error : Retrieving Image 3", Toast.LENGTH_SHORT).show();
        }
    }
}