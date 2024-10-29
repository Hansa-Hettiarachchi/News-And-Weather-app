package com.example.trvavelguidassistant.activities;

import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trvavelguidassistant.R;
import com.example.trvavelguidassistant.model.weatherData;
import com.example.trvavelguidassistant.utilities.Constants;
import com.example.trvavelguidassistant.utilities.PreferenceManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

public class WeatherCityActivity extends AppCompatActivity {

    private EditText textSearch1;

    final String APP_ID = "88e2d41f1558827307e8942719daeeed";
    final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather";


    TextView nameOfCity, weatherState, Temperature,dataReceivedTime,humidity,pressure,windSpeed,visibility,sunRise,sunSet,countryCode,timeZone;
    ImageView weatherIcon;

    LocationManager mLocationManager;
    LocationListener mLocationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_city);
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
        timeZone= findViewById(R.id.timeZone);

        Button buttonSearch1 = findViewById(R.id.buttonSearch1);
        textSearch1 = findViewById(R.id.textSearch1);

        buttonSearch1.setOnClickListener(v -> {
            String city = textSearch1.getText().toString();
            getWeatherForNewCity(city);
        });

        TextView forecast = findViewById(R.id.forecast);
        forecast.setOnClickListener(v -> {
            String city = textSearch1.getText().toString();
            Intent intent = new Intent(WeatherCityActivity.this, ForcastCityWeatherActivity.class);
            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("city",city);
            startActivity(intent);
        });

    }

    private void getWeatherForNewCity(String city)
    {
        RequestParams params=new RequestParams();
        params.put("q",city);
        params.put("appid",APP_ID);
        letsdoSomeNetworking(params);

    }


    private  void letsdoSomeNetworking(RequestParams params)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(WEATHER_URL,params,new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                //super.onSuccess(statusCode, headers, response);
                Toast.makeText(WeatherCityActivity.this, "Data Get Success", Toast.LENGTH_SHORT).show();

                weatherData weatherD = weatherData.fromJson(response);
                assert weatherD != null;
                updateUI(weatherD);
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(WeatherCityActivity.this, "Error : No city found", Toast.LENGTH_SHORT).show();
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