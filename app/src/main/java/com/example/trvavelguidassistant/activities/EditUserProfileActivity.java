package com.example.trvavelguidassistant.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trvavelguidassistant.R;
import com.example.trvavelguidassistant.utilities.Constants;
import com.example.trvavelguidassistant.utilities.PreferenceManager;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditUserProfileActivity extends AppCompatActivity {

    private PreferenceManager preferenceManager;
    private EditText inputFirstName,inputLastName,inputIDNumber;
    private MaterialButton buttonEditUser;
    private ProgressBar editUserProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        preferenceManager = new PreferenceManager(getApplicationContext());

        TextView textTitle = findViewById(R.id.textTitle);
        textTitle.setText(String.format(
                "%s %s",
                preferenceManager.getString(Constants.KEY_FIRST_NAME),
                preferenceManager.getString(Constants.KEY_LAST_NAME)
        ));

        //go back
        ImageView imageBack = findViewById(R.id.imageBack);
        imageBack.setOnClickListener(v -> onBackPressed());

        inputFirstName = findViewById(R.id.inputFirstName);
        inputLastName = findViewById(R.id.inputLastName);
        inputIDNumber = findViewById(R.id.inputIDNumber);
        buttonEditUser = findViewById(R.id.buttonEditUser);
        editUserProgressBar = findViewById(R.id.editUserProgressBar);

        buttonEditUser.setOnClickListener(v -> {
            if (inputFirstName.getText().toString().trim().isEmpty()) {
                Toast.makeText(EditUserProfileActivity.this, "Enter First name", Toast.LENGTH_SHORT).show();
            } else if (inputLastName.getText().toString().trim().isEmpty()) {
                Toast.makeText(EditUserProfileActivity.this, "Enter Last name", Toast.LENGTH_SHORT).show();
            } else if (inputIDNumber.getText().toString().trim().isEmpty()) {
                Toast.makeText(EditUserProfileActivity.this, "Enter ID Number", Toast.LENGTH_SHORT).show();
            }else{
                addUser();
            }
        });

    }

    private void addUser() {

        buttonEditUser.setVisibility(View.INVISIBLE);
        editUserProgressBar.setVisibility(View.VISIBLE);

        Map<String,Object> userDetails = new HashMap<>();
        String fName = inputFirstName.getText().toString();
        String lName = inputLastName.getText().toString();
        String idNumber = inputIDNumber.getText().toString();
        userDetails.put(Constants.KEY_FIRST_NAME,fName);
        userDetails.put(Constants.KEY_LAST_NAME,lName);
        userDetails.put(Constants.KEY_ID_NUMBER,idNumber);

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .whereEqualTo(Constants.KEY_EMAIL, preferenceManager.getString(Constants.KEY_EMAIL))
                .get()
                .addOnCompleteListener(task2 -> {
                    if(task2.isSuccessful() && task2.getResult().getDocuments().size()>0) {
                        DocumentSnapshot documentSnapshot = task2.getResult().getDocuments().get(0);
                        String documentID = documentSnapshot.getId();
                        database.collection(Constants.KEY_COLLECTION_USERS)
                                .document(documentID)
                                .update(userDetails)
                                .addOnSuccessListener(unused -> database.collection(Constants.KEY_COLLECTION_USERS)
                                        .whereEqualTo(Constants.KEY_EMAIL, preferenceManager.getString(Constants.KEY_EMAIL))
                                        .get()
                                        .addOnCompleteListener(task4 -> {
                                            if (task4.isSuccessful() && task4.getResult().getDocuments().size() > 0) {
                                                DocumentSnapshot documentSnapshot1 = task4.getResult().getDocuments().get(0);
                                                preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                                                preferenceManager.putString(Constants.KEY_USER_ID, documentSnapshot1.getId());
                                                preferenceManager.putString(Constants.KEY_FIRST_NAME, documentSnapshot1.getString(Constants.KEY_FIRST_NAME));
                                                preferenceManager.putString(Constants.KEY_LAST_NAME, documentSnapshot1.getString(Constants.KEY_LAST_NAME));
                                                preferenceManager.putString(Constants.KEY_ID_NUMBER, documentSnapshot1.getString(Constants.KEY_ID_NUMBER));
                                                preferenceManager.putString(Constants.KEY_EMAIL, documentSnapshot1.getString(Constants.KEY_EMAIL));
                                                preferenceManager.putString(Constants.KEY_OLD_EMAIL, documentSnapshot1.getString(Constants.KEY_OLD_EMAIL));
                                                preferenceManager.putString(Constants.KEY_PROFILE_PICTURE, documentSnapshot1.getString(Constants.KEY_PROFILE_PICTURE));
                                                preferenceManager.putString(Constants.KEY_LOG_NUMBER, documentSnapshot1.getString(Constants.KEY_LOG_NUMBER));
                                                Toast.makeText(EditUserProfileActivity.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                            } else {
                                                Toast.makeText(EditUserProfileActivity.this, "Failed to Update", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(e -> Toast.makeText(EditUserProfileActivity.this, "Failed to Update", Toast.LENGTH_SHORT).show())
                                );
                    }else{
                        Toast.makeText(EditUserProfileActivity.this, "Failed to Update", Toast.LENGTH_SHORT).show();
                    }
                });
        buttonEditUser.setVisibility(View.VISIBLE);
        editUserProgressBar.setVisibility(View.INVISIBLE);
    }
}