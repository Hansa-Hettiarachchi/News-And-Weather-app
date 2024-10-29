package com.example.trvavelguidassistant.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.trvavelguidassistant.R;
import com.example.trvavelguidassistant.model.weatherData;
import com.example.trvavelguidassistant.utilities.Constants;
import com.example.trvavelguidassistant.utilities.PreferenceManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

public class WeatherCurrentLocationActivity extends AppCompatActivity {

    final String APP_ID = "88e2d41f1558827307e8942719daeeed";
    final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather";

    final long MIN_TIME = 5000;
    final float MIN_DISTANCE = 1000;
    final int REQUEST_CODE = 101;


    String Location_Provider = LocationManager.GPS_PROVIDER;

    TextView nameOfCity, weatherState, Temperature,dataReceivedTime,humidity,pressure,windSpeed,visibility,sunRise,sunSet,countryCode,timeZone;
    ImageView weatherIcon;

    LocationManager mLocationManager;
    LocationListener mLocationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_current_location);

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

        weatherState = findViewById(R.id.weatherCondition);
        Temperature = findViewById(R.id.temperature);
        weatherIcon = findViewById(R.id.weatherIcon);
        nameOfCity = findViewById(R.id.cityName);
        dataReceivedTime = findViewById(R.id.dataReceivedTime);
        humidity = findViewById(R.id.humidity);
        pressure = findViewById(R.id.pressure);
        windSpeed = findViewById(R.id.windSpeed);
        visibility = findViewById(R.id.visibility);
        sunRise = findViewById(R.id.sunRise);
        sunSet = findViewById(R.id.sunSet);
        countryCode= findViewById(R.id.countryCode);
        timeZone = findViewById(R.id.timeZone);

        TextView forecast = findViewById(R.id.forecast);
        forecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeatherCurrentLocationActivity.this, ForcastCurrentLocationActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getWeatherForCurrentLocation();
    }

    private void getWeatherForCurrentLocation() {

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                String Latitude = String.valueOf(location.getLatitude());
                String Longitude = String.valueOf(location.getLongitude());

                RequestParams params =new RequestParams();
                params.put("lat" ,Latitude);
                params.put("lon",Longitude);
                params.put("appid",APP_ID);
                letsdoSomeNetworking(params);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(WeatherCurrentLocationActivity.this, "Location Disabled", Toast.LENGTH_SHORT).show();
            }
        };


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }
        mLocationManager.requestLocationUpdates(Location_Provider, MIN_TIME, MIN_DISTANCE, mLocationListener);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==REQUEST_CODE)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(WeatherCurrentLocationActivity.this,"Location get Successfully",Toast.LENGTH_SHORT).show();
                getWeatherForCurrentLocation();
            }
            else
            {
                Toast.makeText(this, "User Denied Permission", Toast.LENGTH_SHORT).show();
            }
        }
    }



    private  void letsdoSomeNetworking(RequestParams params)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(WEATHER_URL,params,new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                //super.onSuccess(statusCode, headers, response);
                Toast.makeText(WeatherCurrentLocationActivity.this, "Data Get Success", Toast.LENGTH_SHORT).show();

                weatherData weatherD = weatherData.fromJson(response);
                assert weatherD != null;
                updateUI(weatherD);
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(WeatherCurrentLocationActivity.this, "Get data Failure", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private  void updateUI(weatherData weather){

        Temperature.setText(weather.getTemperature());
        nameOfCity.setText(weather.getNameOfCity());
        weatherState.setText(weather.getWeatherState());
        int resourceID=getResources().getIdentifier(weather.getMicon(),"drawable",getPackageName());
        weatherIcon.setImageResource(resourceID);

        dataReceivedTime.setText(weather.getDataReceivedTime());
        humidity.setText(weather.getHumidity());
        pressure.setText(weather.getPressure());
        windSpeed.setText(weather.getWindSpeed());
        visibility.setText(weather.getVisibility());
        sunRise.setText(weather.getSunRise());
        sunSet.setText(weather.getSunSet());
        countryCode.setText(weather.getCountryCode());
        timeZone.setText(weather.getTimeZone());
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mLocationManager!=null)
        {
            mLocationManager.removeUpdates(mLocationListener);
        }
    }
}