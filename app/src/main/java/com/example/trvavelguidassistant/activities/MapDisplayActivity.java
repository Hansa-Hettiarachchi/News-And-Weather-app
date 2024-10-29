package com.example.trvavelguidassistant.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.trvavelguidassistant.R;
import com.example.trvavelguidassistant.utilities.Constants;
import com.example.trvavelguidassistant.utilities.PreferenceManager;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.libraries.places.api.Places;
//import com.google.android.libraries.places.api.model.Place;
//import com.google.android.libraries.places.widget.Autocomplete;
//import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MapDisplayActivity extends AppCompatActivity implements OnMapReadyCallback {

    MapView mapView;
    GoogleMap mGoogleMap;
    EditText textSearch;
    Button buttonSearch;
    TextView textDefaults,textSatellite,textTerrain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_display);

//        Places.initialize(getApplicationContext(),"AIzaSyDwmGBLaSkbbM2KXEruokjnV_RVQIXYOxI");

        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

        TextView textTitle = findViewById(R.id.textTitle);
        textTitle.setText(String.format(
                "%s %s",
                preferenceManager.getString(Constants.KEY_FIRST_NAME),
                preferenceManager.getString(Constants.KEY_LAST_NAME)
        ));

        ImageView imageBack = findViewById(R.id.imageBack);
        imageBack.setOnClickListener(v -> onBackPressed());
        mapView = findViewById(R.id.map_view);
        textSearch = findViewById(R.id.textSearch);
        textDefaults =findViewById(R.id.textDefaults);
        textSatellite = findViewById(R.id.textSatellite);
        textTerrain = findViewById(R.id.textTerrain);
        buttonSearch = findViewById(R.id.buttonSearch);

//        textSearch.setFocusable(false);

//        textSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                List<Place.Field> fieldList= Arrays.asList(Place.Field.ADDRESS
//                ,Place.Field.LAT_LNG,Place.Field.NAME);
//
//                Intent intent=new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY
//                ,fieldList).build(MapDisplayActivity.this);
//                startActivityForResult(intent,100);
//            }
//        });

        textDefaults.setOnClickListener(v -> mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL));

        textSatellite.setOnClickListener(v -> mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE));

        textTerrain.setOnClickListener(v -> mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN));

        boolean per = checkMyPermission();

        if (per) {
            mapView.getMapAsync(this);
            mapView.onCreate(savedInstanceState);
            buttonSearch.setOnClickListener(this::geoLocate);
        }else{
            requestMapPermissions();
        }
    }

    private void requestMapPermissions() {
        ActivityCompat.requestPermissions(MapDisplayActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        ActivityCompat.requestPermissions(MapDisplayActivity.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
    }

    private void geoLocate(View view) {
        String locationName = textSearch.getText().toString();

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> addressList = geocoder.getFromLocationName(locationName,1);
            if(addressList.size()>0){
                Address address = addressList.get(0);
                gotoLocation(address.getLatitude(),address.getLongitude());

                mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(address.getLatitude(), address.getLongitude())));
            }else{
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            }

        } catch (IOException e) {
            Toast.makeText(this, "No Such Location Found", Toast.LENGTH_SHORT).show();
        }
    }

    private void gotoLocation(double latitude, double longitude) {
        LatLng latLng =new LatLng(latitude,longitude);

        CameraUpdate cameraUpdate= CameraUpdateFactory.newLatLngZoom(latLng,18);
        mGoogleMap.moveCamera(cameraUpdate);
    }

    private Boolean checkMyPermission() {
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mGoogleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //check
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == 100 && resultCode == RESULT_OK){
//            Place place=Autocomplete.getPlaceFromIntent(data);
//            textSearch.setText(place.getAddress());
//        }
//    }
}