package com.example.trvavelguidassistant.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trvavelguidassistant.R;
import com.example.trvavelguidassistant.utilities.Constants;
import com.example.trvavelguidassistant.utilities.PreferenceManager;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddSocialMediaActivity extends AppCompatActivity {

    ImageView homeImg,addImg,previewImage,accountImg;
    private PreferenceManager preferenceManager;
    CardView addSocialMedia;
    Button postSocialMediaBtn;
    EditText inputLocation;
    private Boolean checkImage = false;

    SweetAlertDialog pDialog;

    DatabaseReference postSocialMediaDatabase;

    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 400;
    private static final int IMAGE_PICK_GALLERY_CODE = 1000;
    private static final int IMAGE_PICK_CAMERA_CODE = 1001;

    String cameraPermission[];
    String storagePermission[];

    Uri uri1;
    Uri image_uri;
    FirebaseAuth mAuth;

    private FirebaseStorage storage1;
    private StorageReference storageReference1,storageReference2;

    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_social_media);

        mAuth = FirebaseAuth.getInstance();
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
                Intent intent = new Intent(getApplicationContext(), SocialMediaActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        setUpProgressDialog();

        homeImg = findViewById(R.id.homeImg);
        //addImg = findViewById(R.id.addImg);
        accountImg = findViewById(R.id.accountImg);
        //addText = findViewById(R.id.addText);

        homeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddSocialMediaActivity.this, SocialMediaActivity.class);
                startActivity(intent);
            }
        });

        accountImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddSocialMediaActivity.this, SocialMediaAccountActivity.class);
                startActivity(intent);
            }
        });

        postSocialMediaDatabase = FirebaseDatabase.getInstance().getReference().child("SocialMedia");
        String key = postSocialMediaDatabase.push().getKey();

        storage1 = FirebaseStorage.getInstance();
        storageReference1 = storage1.getReference().child("Social_Media");

        storageReference2 = storage1.getReference().child("Profile_Picture");

        postSocialMediaBtn = findViewById(R.id.postSocialMediaBtn);
        previewImage = findViewById(R.id.previewImage);

        addSocialMedia = findViewById(R.id.addSocialMedia);

        cameraPermission = new String[]{Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};

        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        addSocialMedia.setOnClickListener(v -> showImageImportDialog());

        inputLocation = findViewById(R.id.inputLocation);

        postSocialMediaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputLocation.getText().toString().trim().isEmpty()) {
                    Toast.makeText(AddSocialMediaActivity.this, "Enter Location", Toast.LENGTH_SHORT).show();
                }else if(!checkImage){
                    Toast.makeText(AddSocialMediaActivity.this, "Input Photo", Toast.LENGTH_SHORT).show();
                }else{

                    postSocialMediaDatabase = postSocialMediaDatabase.child(key);
                    String lc = inputLocation.getText().toString();
                    String name = textTitle.getText().toString();
                    postSocialMediaDatabase.child("location").setValue(lc);
                    postSocialMediaDatabase.child("userName").setValue(name);
                    postSocialMediaDatabase.child("likeCount").setValue(0);
                    postSocialMediaDatabase.child("key5").setValue(key);

                    String userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

                    StorageReference imageRef2 = storageReference2.child(userID+".jpg");

                    imageRef2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            postSocialMediaDatabase.child("userPic").setValue(uri.toString());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                        }
                    });

                    pDialog.setTitleText("Uploading");
                    pDialog.show();

                    StorageReference imageRef1 = storageReference1.child(key+".jpg");

                    imageRef1.putFile(uri1)
                            .addOnSuccessListener(taskSnapshot -> {
                                pDialog.dismissWithAnimation();

                                imageRef1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        postSocialMediaDatabase.child("photo").setValue(uri.toString());
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                    }
                                });

                                Toast.makeText(AddSocialMediaActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> {
                                pDialog.dismissWithAnimation();
                                Toast.makeText(AddSocialMediaActivity.this, "Failed to upload", Toast.LENGTH_SHORT).show();
                            }).addOnProgressListener(snapshot -> {
                                pDialog.setTitleText("Uploading");
                                pDialog.show();
                    });
                            Intent intent =new Intent(AddSocialMediaActivity.this,SocialMediaActivity.class);
                            startActivity(intent);
                            finish();
                }
            }
        });

    }

    private void setUpProgressDialog() {
        pDialog = new SweetAlertDialog(AddSocialMediaActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), SocialMediaActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
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
                        .withAspectRatio(1, 1) // Set the desired aspect ratio
                        .withMaxResultSize(450, 450) // Set the max result size if needed
                        .start(this);
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
                    uri1 = resultUri;
                    previewImage.setImageURI(resultUri);
                    checkImage = true;

                    // Convert the image to a Bitmap if needed
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) previewImage.getDrawable();
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                }
            } else if (resultCode == UCrop.RESULT_ERROR) {
                Throwable error = UCrop.getError(data);
                Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();
            }
        }
    }
}