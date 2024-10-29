package com.example.trvavelguidassistant.activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.trvavelguidassistant.R;
import com.example.trvavelguidassistant.utilities.Constants;
import com.example.trvavelguidassistant.utilities.PreferenceManager;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.yalantis.ucrop.UCrop;
import java.io.File;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class TravelLogUploadNewActivity extends AppCompatActivity {

    final Calendar myCalendar= Calendar.getInstance();
    EditText inputDate,inputDescription,inputLogName;
    ImageView addImage1,addImage2,addImage3,addImagePic;
    MaterialButton buttonUploadLog;
    ProgressBar uploadLogProgressBar;
   //VideoView addVideo;
    TextView LogNumberText;
    private int logNumber= 0;

    private PreferenceManager preferenceManager;

    private FirebaseStorage storage;
    private StorageReference storageReference;
    DatabaseReference LogDatabase,DateLogDatabase;

    FirebaseAuth mAuth;

    private int imageNumber;

    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 400;
    private static final int IMAGE_PICK_GALLERY_CODE = 1000;
    private static final int IMAGE_PICK_CAMERA_CODE = 1001;

    String cameraPermission[];
    String storagePermission[];

    Uri image_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_log_upload_new);

        preferenceManager = new PreferenceManager(getApplicationContext());

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference().child("Travel_Log");
        mAuth = FirebaseAuth.getInstance();
        String userID = preferenceManager.getString(Constants.KEY_USER_ID);
        LogDatabase = FirebaseDatabase.getInstance().getReference().child("Logs/"+userID);

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

        //get Date
        inputDate = findViewById(R.id.inputDate);
        DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH,month);
            myCalendar.set(Calendar.DAY_OF_MONTH,day);
            updateLabel();
        };
        inputDate.setOnClickListener(view -> new DatePickerDialog(TravelLogUploadNewActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show());

        //code
        inputDescription =findViewById(R.id.inputDescription);
        addImage1 = findViewById(R.id.addImage1);
        addImage2 =findViewById(R.id.addImage2);
        addImage3 = findViewById(R.id.addImage3);
        addImagePic = findViewById(R.id.addImagePic);
        buttonUploadLog = findViewById(R.id.buttonUploadLog);
        uploadLogProgressBar = findViewById(R.id.uploadLogProgressBar);
        inputLogName = findViewById(R.id.inputLogName);
        //addVideo = findViewById(R.id.addVideo);
        LogNumberText = findViewById(R.id.LogNumberText);

        logNumber = Integer.parseInt(preferenceManager.getString(Constants.KEY_LOG_NUMBER))+1;

        LogNumberText.setText(String.valueOf(logNumber));

        cameraPermission = new String[]{Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};

        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        addImage1.setOnClickListener(v -> {
            imageNumber = 1;
            showImageImportDialog();
        });
        addImage2.setOnClickListener(v -> {
            imageNumber = 2;
            showImageImportDialog();
        });
        addImage3.setOnClickListener(v -> {
            imageNumber = 3;
            showImageImportDialog();
        });

        buttonUploadLog.setOnClickListener(v -> {
                if (inputDate.getText().toString().trim().isEmpty()) {
                    Toast.makeText(TravelLogUploadNewActivity.this, "Enter Date", Toast.LENGTH_SHORT).show();
                }
                else{
                    buttonUploadLog.setVisibility(View.INVISIBLE);
                    uploadLogProgressBar.setVisibility(View.VISIBLE);
                    String date1 = inputDate.getText().toString();
                    date1 = date1.replaceAll("/","");
                    DateLogDatabase  = LogDatabase.child(date1).child(String.valueOf(logNumber));
                    String logNameValue = inputLogName.getText().toString();
                    String description = inputDescription.getText().toString();
                    DateLogDatabase.child("name").setValue(logNameValue);
                    DateLogDatabase.child("description").setValue(description);
                    uploadToDataBase();
                }
        });
    }

    private void uploadToDataBase() {
                    Map<String,Object> userDetails = new HashMap<>();
                    String ln = String.valueOf(logNumber);
                    userDetails.put(Constants.KEY_LOG_NUMBER,ln);
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
                                                                    Toast.makeText(TravelLogUploadNewActivity.this, "Log Number Updated", Toast.LENGTH_SHORT).show();
                                                                } else {
                                                                    Toast.makeText(TravelLogUploadNewActivity.this, "Error Updating Log Number", Toast.LENGTH_SHORT).show();
                                                                }
                                                            })
                                                            .addOnFailureListener(e -> Toast.makeText(TravelLogUploadNewActivity.this, "Error Updating Log Number", Toast.LENGTH_SHORT).show())
                                            );
                                }else{
                                    Toast.makeText(TravelLogUploadNewActivity.this, "Error Updating Log Number", Toast.LENGTH_SHORT).show();
                                }
                            });
                    Toast.makeText(TravelLogUploadNewActivity.this, "All files Uploaded", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), TravelLogMenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

        buttonUploadLog.setVisibility(View.VISIBLE);
        uploadLogProgressBar.setVisibility(View.INVISIBLE);
    }

    private void showImageImportDialog() {
        String[] items = {"Camera", "Gallery"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Select Image");
        dialog.setItems(items, (dialog1, which) -> {
            if(which == 0){
                if(!checkCameraPermission()){
                    requestCameraPermission();
                }else{
                    pickCamera();
                }
            }
            if(which == 1){
                if(!checkStoragePermission()){
                    requestStoragePermission();
                }else{
                    pickGallery();
                }
            }
        });
        dialog.create().show();
    }

    private void updateLabel() {
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        inputDate.setText(dateFormat.format(myCalendar.getTime()));
    }

    private void pickGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_GALLERY_CODE);
    }

    private void pickCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"NewPic");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Image to text");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
        startActivityForResult(cameraIntent,IMAGE_PICK_CAMERA_CODE);
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this,storagePermission,STORAGE_REQUEST_CODE);
    }

    private boolean checkStoragePermission() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this,cameraPermission,CAMERA_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result && result1;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && writeStorageAccepted) {
                        pickCamera();
                    } else {
                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case STORAGE_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean writeStorageAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    if (writeStorageAccepted) {
                        pickGallery();
                    } else {
                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                assert data != null;
                Uri sourceUri = data.getData();
                Uri destinationUri = Uri.fromFile(new File(getCacheDir(), "cropped_image.jpg"));

                UCrop.of(sourceUri, destinationUri)
                        .withAspectRatio(1, 1) // Optional: set aspect ratio to 1:1 (or customize)
                        .withMaxResultSize(450, 450) // Optional: set max result size
                        .start(this); // Start the UCrop activity
            }

            if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                Uri destinationUri = Uri.fromFile(new File(getCacheDir(), "cropped_image.jpg"));

                UCrop.of(image_uri, destinationUri)
                        .withAspectRatio(1, 1)
                        .withMaxResultSize(450, 450)
                        .start(this);
            }
        }

        // Handle UCrop result
        if (requestCode == UCrop.REQUEST_CROP) {
            if (resultCode == RESULT_OK && data != null) {
                Uri resultUri = UCrop.getOutput(data);

                if (resultUri != null) {
                    if (imageNumber == 1) {
                        addImage1.setImageURI(resultUri);
                        UploadImage(1, resultUri);
                    } else if (imageNumber == 2) {
                        addImage2.setImageURI(resultUri);
                        UploadImage(2, resultUri);
                    } else if (imageNumber == 3) {
                        addImage3.setImageURI(resultUri);
                        UploadImage(3, resultUri);
                    }
                }
            } else if (resultCode == UCrop.RESULT_ERROR) {
                Throwable cropError = UCrop.getError(data);
                Toast.makeText(this, "Crop error: " + cropError, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void UploadImage(int imageNumber,Uri imageUri) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading");
        pd.show();
        String date = inputDate.getText().toString();
        date = date.replaceAll("/","");
        StorageReference imageRef = storageReference.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()+"/"+date+"/log"+logNumber+"/image"+imageNumber+".jpg");
        imageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    pd.dismiss();
                    Toast.makeText(TravelLogUploadNewActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    pd.dismiss();
                    Toast.makeText(TravelLogUploadNewActivity.this, "Failed to upload", Toast.LENGTH_SHORT).show();
                }).addOnProgressListener(snapshot -> {
            double ProgressPercentage = (100.00 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
            pd.setMessage("Progress: "+ (int) ProgressPercentage+"%");
        });
    }
}