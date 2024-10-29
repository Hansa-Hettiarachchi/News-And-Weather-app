package com.example.trvavelguidassistant.activities;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.yalantis.ucrop.UCrop;
import java.io.File;
import android.net.Uri;
import android.widget.Toast;

public class TextRecognitionActivity extends AppCompatActivity {

    TextView addImageText;
    ImageView addImagePic,mPreviewIv;
    EditText resultEt;
    LinearLayout copy;

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
        setContentView(R.layout.activity_text_recognition);

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

        addImageText = findViewById(R.id.addImageText);
        addImagePic = findViewById(R.id.addImagePic);
        mPreviewIv = findViewById(R.id.imageIv);
        resultEt = findViewById(R.id.resultEt);
        copy = findViewById(R.id.copy);

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard=(ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("resultEt", resultEt.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(TextRecognitionActivity.this, "Text copied", Toast.LENGTH_SHORT).show();
            }
        });
        cameraPermission = new String[]{Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE};

        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        addImagePic.setOnClickListener(v -> showImageImportDialog());
        addImageText.setOnClickListener(v -> showImageImportDialog());
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
            if (requestCode == UCrop.REQUEST_CROP && data != null) {
                Uri resultUri = UCrop.getOutput(data);

                if (resultUri != null) {
                    // Set cropped image to ImageView
                    mPreviewIv.setImageURI(resultUri);

                    // Convert ImageView to Bitmap for text recognition
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) mPreviewIv.getDrawable();
                    Bitmap bitmap = bitmapDrawable.getBitmap();

                    // Initialize TextRecognizer and perform OCR
                    TextRecognizer recognizer = new TextRecognizer.Builder(getApplicationContext()).build();

                    if (!recognizer.isOperational()) {
                        Toast.makeText(this, "Error: Text recognizer is not operational", Toast.LENGTH_SHORT).show();
                    } else {
                        Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                        SparseArray<TextBlock> items = recognizer.detect(frame);
                        StringBuilder sb = new StringBuilder();

                        for (int i = 0; i < items.size(); i++) {
                            TextBlock myItem = items.valueAt(i);
                            sb.append(myItem.getValue());
                            sb.append("\n");
                        }
                        resultEt.setText(sb.toString());
                    }
                }
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            Throwable cropError = UCrop.getError(data);
            Toast.makeText(this, "Crop error: " + cropError, Toast.LENGTH_SHORT).show();
        }
    }
}