package com.example.trvavelguidassistant.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.trvavelguidassistant.R;
import com.example.trvavelguidassistant.utilities.Constants;
import com.example.trvavelguidassistant.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    private MaterialButton buttonChangeEmailConfirm,buttonChangePasswordConfirm,buttonNewEmailConfirm;
    private ProgressBar changeEmailProgressBar,editUserDetailsProgressBar,uploadDPProgressBar,DeleteProgressBar;
    private PreferenceManager preferenceManager;
    private EditText inputPPassword,inputPasswordEmail,inputNewEmail;
    private CardView cardChangeEmail;
    private CardView cardChangePassword;
    private CardView cardDetails;
    private CardView cardNewEmail;
    private CardView cardUploadPP;
    private CardView cardEditUser;
    private CardView cardChangeE, Delete;
    CircleImageView profile_image;
    public Uri imageUri;

    private StorageReference storageReference;

    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        preferenceManager = new PreferenceManager(getApplicationContext());

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference().child("Profile_Picture");
        mAuth = FirebaseAuth.getInstance();

        //set header bar name
        TextView textTitle = findViewById(R.id.textTitle);
        textTitle.setText(String.format(
                "%s %s",
                preferenceManager.getString(Constants.KEY_FIRST_NAME),
                preferenceManager.getString(Constants.KEY_LAST_NAME)
        ));

        //go back
        ImageView imageBack = findViewById(R.id.imageBack);
        imageBack.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        //get user details
        TextView nameView = findViewById(R.id.nameView);
        nameView.setText(String.format(
                "%s %s",
                preferenceManager.getString(Constants.KEY_FIRST_NAME),
                preferenceManager.getString(Constants.KEY_LAST_NAME)
        ));
        TextView emailView = findViewById(R.id.emailView);
        emailView.setText(String.format("%s", preferenceManager.getString(Constants.KEY_EMAIL)));
        TextView IDView = findViewById(R.id.IDView);
        IDView.setText(String.format("%s", preferenceManager.getString(Constants.KEY_ID_NUMBER)));

        //upload Profile Picture
        cardUploadPP = findViewById(R.id.cardUploadPP);
        uploadDPProgressBar = findViewById(R.id.uploadDPProgressBar);
        profile_image = findViewById(R.id.profile_image);

        StorageReference storageReference2 = storage.getReference().child("Profile_Picture/" + preferenceManager.getString(Constants.KEY_PROFILE_PICTURE));


        try {
            File localFile = File.createTempFile("tempProfilePicture","jpg");
            storageReference2.getFile(localFile)
                    .addOnSuccessListener(taskSnapshot -> {
                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        profile_image.setImageBitmap(bitmap);
                    })
                    .addOnFailureListener(e -> Toast.makeText(SettingsActivity.this, "User has no Profile Picture", Toast.LENGTH_SHORT).show());
        } catch (IOException e) {
            e.printStackTrace();
        }

        cardUploadPP.setOnClickListener(v -> choosePicture());

        //Edit Profile
        cardEditUser = findViewById(R.id.cardEditUser);
        editUserDetailsProgressBar = findViewById(R.id.editUserDetailsProgressBar);

        cardEditUser.setOnClickListener(v -> {
            cardEditUser.setVisibility(View.INVISIBLE);
            editUserDetailsProgressBar.setVisibility(View.VISIBLE);
            Intent intent = new Intent(SettingsActivity.this, EditUserProfileActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            cardEditUser.setVisibility(View.VISIBLE);
            editUserDetailsProgressBar.setVisibility(View.INVISIBLE);
        });

        //change password
        cardDetails = findViewById(R.id.cardDetails);
        cardChangeEmail = findViewById(R.id.cardChangeEmail);
        cardChangePassword = findViewById(R.id.cardChangePassword);
        cardNewEmail = findViewById(R.id.cardNewEmail);

        CardView cardChangeP = findViewById(R.id.cardChangeP);

        String email = preferenceManager.getString(Constants.KEY_EMAIL);

        cardChangeP.setOnClickListener(v -> {
            cardDetails.setVisibility(View.INVISIBLE);
            cardChangeEmail.setVisibility(View.INVISIBLE);
            cardChangePassword.setVisibility(View.VISIBLE);

            inputPPassword = findViewById(R.id.inputPPassword);

            buttonChangePasswordConfirm = findViewById(R.id.buttonChangePasswordConfirm);

            buttonChangePasswordConfirm.setOnClickListener(v1 -> {
                String PPassword = inputPPassword.getText().toString();
                        mAuth.signInWithEmailAndPassword(email, PPassword)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                cardDetails.setVisibility(View.VISIBLE);
                                cardChangePassword.setVisibility(View.INVISIBLE);
                                inputPPassword.getText().clear();
                                changePassword();
                            } else {
                                Toast.makeText(SettingsActivity.this, "Error : Enter Correct Password", Toast.LENGTH_SHORT).show();
                                inputPPassword.getText().clear();
                            }
                        });
            });
        });

//        // Delete Account
//        Delete = findViewById(R.id.Delete);
//        DeleteProgressBar = findViewById(R.id.DeleteProgressBar);
//
//        firebaseUser = mAuth.getCurrentUser();
//        Delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder dialog = new AlertDialog.Builder(SettingsActivity.this);
//                dialog.setTitle("Are you sure?");
//                dialog.setMessage("Deleting this account will result in completely removing your account from the system with all related data and you won,t able to access the app");
//                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        DeleteProgressBar.setVisibility(View.VISIBLE);
//                        Delete.setVisibility(View.INVISIBLE);
//                        //get id for realtime,fire store
//                        String id  = preferenceManager.getString(Constants.KEY_USER_ID);
//
//                        //get id for storage
//                        String storageID = mAuth.getCurrentUser().getUid();
//
//                        FirebaseFirestore.getInstance().collection("users_collection")
//                                .document(id)
//                                .delete()
//                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void unused) {
//                                        DatabaseReference db= FirebaseDatabase.getInstance().getReference().child("Event");
//                                        db.child(id).removeValue()
//                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void unused) {
//                                                DatabaseReference db2= FirebaseDatabase.getInstance().getReference().child("Logs");
//                                                db2.child(id).removeValue()
//                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                            @Override
//                                                            public void onSuccess(Void unused) {
//                                                                firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                                    @Override
//                                                                    public void onComplete(@NonNull Task<Void> task) {
//                                                                        if(task.isSuccessful()){
//                                                                            preferenceManager.clearPreferences();
//                                                                            Toast.makeText(SettingsActivity.this, "Account Deleted", Toast.LENGTH_SHORT).show();
//                                                                            Intent intent = new Intent(SettingsActivity.this,LoadingActivity.class);
//                                                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                                                            startActivity(intent);
//                                                                        }else{
//                                                                            Toast.makeText(SettingsActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                                                                        }
//                                                                    }
//                                                                });
//                                                                DeleteProgressBar.setVisibility(View.INVISIBLE);
//                                                                Delete.setVisibility(View.VISIBLE);
//                                                            }
//                                                        });
//                                            }
//                                        });
//                                    }
//                                });
//
//                        firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if(task.isSuccessful()){
//                                    preferenceManager.clearPreferences();
//                                    Toast.makeText(SettingsActivity.this, "Account Deleted", Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(SettingsActivity.this,LoadingActivity.class);
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                    startActivity(intent);
//                                }else{
//                                    Toast.makeText(SettingsActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//                        DeleteProgressBar.setVisibility(View.INVISIBLE);
//                        Delete.setVisibility(View.VISIBLE);
//                    }
//                });
//                dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                AlertDialog alertDialog = dialog.create();
//                alertDialog.show();
//            }
//        });

        //change email
        inputNewEmail = findViewById(R.id.inputNewEmail);
        buttonNewEmailConfirm = findViewById(R.id.buttonNewEmailConfirm);

        cardChangeE = findViewById(R.id.cardChangeE);
        changeEmailProgressBar = findViewById(R.id.changeEmailProgressBar);
        cardChangeE.setOnClickListener(v -> {
            cardDetails.setVisibility(View.INVISIBLE);
            cardChangeEmail.setVisibility(View.VISIBLE);
            cardChangePassword.setVisibility(View.INVISIBLE);

            inputPasswordEmail = findViewById(R.id.inputPasswordEmail);
            buttonChangeEmailConfirm = findViewById(R.id.buttonChangeEmailConfirm);

            buttonChangeEmailConfirm.setOnClickListener(v12 -> {
                String inputEPassword = inputPasswordEmail.getText().toString();
                mAuth.signInWithEmailAndPassword(email, inputEPassword)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                inputPasswordEmail.getText().clear();
                                inputNewEmail.getText().clear();
                                changeEmail(inputEPassword);
                            } else {
                                Toast.makeText(SettingsActivity.this, "Error : Enter Correct Password", Toast.LENGTH_SHORT).show();
                                inputPasswordEmail.getText().clear();
                            }
                        });
            });
        });
    }



    private void uploadPicture() {
        cardUploadPP.setVisibility(View.INVISIBLE);
        uploadDPProgressBar.setVisibility(View.VISIBLE);
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Profile Picture...");
        pd.show();

        StorageReference imageRef = storageReference.child( Objects.requireNonNull(mAuth.getCurrentUser()).getUid()+".jpg");

        imageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {

                    pd.dismiss();

                    Map<String,Object> userDetails = new HashMap<>();
                    String pPicture = mAuth.getCurrentUser().getUid()+".jpg";
                    userDetails.put(Constants.KEY_PROFILE_PICTURE,pPicture);

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
                                            .addOnSuccessListener(unused ->
                                                    database.collection(Constants.KEY_COLLECTION_USERS)
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
                                                                    Toast.makeText(SettingsActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                                                                } else {
                                                                    Toast.makeText(SettingsActivity.this, "Failed to Update", Toast.LENGTH_SHORT).show();
                                                                }
                                                            })
                                                            .addOnFailureListener(e -> Toast.makeText(SettingsActivity.this, "Failed to Update", Toast.LENGTH_SHORT).show())
                                            );
                                }else{
                                    Toast.makeText(SettingsActivity.this, "Failed to Update", Toast.LENGTH_SHORT).show();
                                }
                            });
                })
                .addOnFailureListener(e -> {
                    pd.dismiss();
                    Toast.makeText(SettingsActivity.this, "Failed to upload", Toast.LENGTH_SHORT).show();
                }).addOnProgressListener(snapshot -> {
                    double ProgressPercentage = (100.00 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                    pd.setMessage("Progress: "+ (int) ProgressPercentage+"%");
                });
        cardUploadPP.setVisibility(View.VISIBLE);
        uploadDPProgressBar.setVisibility(View.INVISIBLE);

    }

    private void choosePicture() {
        Intent intent =new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,33);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 33 && resultCode == RESULT_OK) {
            assert data != null;
            if (data.getData()!= null) {
                imageUri = data.getData();
                profile_image.setImageURI(imageUri);
                uploadPicture();
            }
        }
    }

    private void changeEmail(String password) {
        cardDetails.setVisibility(View.INVISIBLE);
        cardNewEmail.setVisibility(View.VISIBLE);

        buttonNewEmailConfirm.setOnClickListener(v -> {
            String newEmail = inputNewEmail.getText().toString();
            if(newEmail.isEmpty()){
                Toast.makeText(SettingsActivity.this, "Error : Input an Email address", Toast.LENGTH_SHORT).show();
                inputNewEmail.getText().clear();
            }else if(!Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()){
                Toast.makeText(SettingsActivity.this, "Error : Input valid type Email", Toast.LENGTH_SHORT).show();
                inputNewEmail.getText().clear();
            }else {
                cardChangeE.setVisibility(View.INVISIBLE);
                changeEmailProgressBar.setVisibility(View.VISIBLE);

                FirebaseUser user = mAuth.getCurrentUser();

                AuthCredential credential = EmailAuthProvider
                        .getCredential(preferenceManager.getString(Constants.KEY_EMAIL), password);

                assert user != null;
                user.reauthenticate(credential)
                        .addOnCompleteListener(task -> {
                            String newEmail2 = inputNewEmail.getText().toString();
                            FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();

                            assert user1 != null;
                            user1.updateEmail(newEmail2)
                                    .addOnCompleteListener(task1 -> {
                                        if(task1.isSuccessful())
                                    {
                                    Toast.makeText(SettingsActivity.this, "User re-authenticated.", Toast.LENGTH_SHORT).show();

                                    Map<String,Object> userDetails = new HashMap<>();
                                    String oldEm = preferenceManager.getString(Constants.KEY_EMAIL);
                                    userDetails.put(Constants.KEY_EMAIL,newEmail2);
                                    userDetails.put(Constants.KEY_OLD_EMAIL,oldEm);

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
                                                            .addOnSuccessListener(unused -> {
                                                                signOut();
                                                                Toast.makeText(SettingsActivity.this, "Email Successfully Updated", Toast.LENGTH_SHORT).show();
                                                            }).addOnFailureListener(e -> Toast.makeText(SettingsActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show());
                                                }else{
                                                    Toast.makeText(SettingsActivity.this, "Failed to Update", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        cardDetails.setVisibility(View.VISIBLE);
                                        cardChangeEmail.setVisibility(View.INVISIBLE);
                                    }else{
                                            Toast.makeText(this, "Error: Used Email", Toast.LENGTH_SHORT).show();
                                            inputNewEmail.getText().clear();
                                        }
                                    });
                        });
                cardChangeE.setVisibility(View.VISIBLE);
                changeEmailProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void signOut(){
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference =
                database.collection(Constants.KEY_COLLECTION_USERS).document(
                        preferenceManager.getString(Constants.KEY_USER_ID)
                );
        HashMap<String ,Object> updates = new HashMap<>();
        documentReference.update(updates)
                .addOnSuccessListener(unused -> {
                    preferenceManager.clearPreferences();
                    Toast.makeText(this, "Signing out", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), SelectSignActivity.class));
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(SettingsActivity.this, "Unable to sign out", Toast.LENGTH_SHORT).show());
    }

    private void changePassword() {

        mAuth.sendPasswordResetEmail(preferenceManager.getString(Constants.KEY_EMAIL)).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(SettingsActivity.this, "Check your email to change the password", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SettingsActivity.this, "Error Try Again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}