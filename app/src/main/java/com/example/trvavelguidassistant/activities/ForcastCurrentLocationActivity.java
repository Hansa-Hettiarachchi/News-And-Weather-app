package com.example.trvavelguidassistant.activities;

import android.Manifest;
import android.content.Context;
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
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.example.trvavelguidassistant.R;
import com.example.trvavelguidassistant.model.forecastWeather;
import com.example.trvavelguidassistant.utilities.Constants;
import com.example.trvavelguidassistant.utilities.PreferenceManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

public class ForcastCurrentLocationActivity extends AppCompatActivity {

    final String APP_ID = "88e2d41f1558827307e8942719daeeed";
    final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/forecast";
    final long MIN_TIME = 5000;
    final float MIN_DISTANCE = 1000;
    final int REQUEST_CODE = 101;

    LocationManager mLocationManager;
    LocationListener mLocationListener;

    String Location_Provider = LocationManager.GPS_PROVIDER;

    TextView one111,one112,one113,one121,one122,one123,one131,one132,one133,one141,one142,one143,one151,one152,one153,one161,one162,one163,one171,one172,one173,one181,one182,one183;
    TextView one211,one212,one213,one221,one222,one223,one231,one232,one233,one241,one242,one243,one251,one252,one253,one261,one262,one263,one271,one272,one273,one281,one282,one283;
    TextView one311,one312,one313,one321,one322,one323,one331,one332,one333,one341,one342,one343,one351,one352,one353,one361,one362,one363,one371,one372,one373,one381,one382,one383;
    TextView one411,one412,one413,one421,one422,one423,one431,one432,one433,one441,one442,one443,one451,one452,one453,one461,one462,one463,one471,one472,one473,one481,one482,one483;
    TextView one511,one512,one513,one521,one522,one523,one531,one532,one533,one541,one542,one543,one551,one552,one553,one561,one562,one563,one571,one572,one573,one581,one582,one583;

    TextView dataReceivedTime,cityName,countryCode;
    CardView card1,card2,card3,card4,card5;
    CardView cardShow1,cardShow2,cardShow3,cardShow4,cardShow5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forcast_current_location);

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

        dataReceivedTime =findViewById(R.id.dataReceivedTime);
        cityName=findViewById(R.id.cityName);
        countryCode = findViewById(R.id.countryCode);

        one111 = findViewById(R.id.one111);
        one112 = findViewById(R.id.one112);
        one113 = findViewById(R.id.one113);
        one121 = findViewById(R.id.one121);
        one122 = findViewById(R.id.one122);
        one123 = findViewById(R.id.one123);
        one131 = findViewById(R.id.one131);
        one132 = findViewById(R.id.one132);
        one133 = findViewById(R.id.one133);
        one141 = findViewById(R.id.one141);
        one142 = findViewById(R.id.one142);
        one143 = findViewById(R.id.one143);
        one151 = findViewById(R.id.one151);
        one152 = findViewById(R.id.one152);
        one153 = findViewById(R.id.one153);
        one161 = findViewById(R.id.one161);
        one162 = findViewById(R.id.one162);
        one163 = findViewById(R.id.one163);
        one171 = findViewById(R.id.one171);
        one172 = findViewById(R.id.one172);
        one173 = findViewById(R.id.one173);
        one181 = findViewById(R.id.one181);
        one182 = findViewById(R.id.one182);
        one183 = findViewById(R.id.one183);

        one211 = findViewById(R.id.one211);
        one212 = findViewById(R.id.one212);
        one213 = findViewById(R.id.one213);
        one221 = findViewById(R.id.one221);
        one222 = findViewById(R.id.one222);
        one223 = findViewById(R.id.one223);
        one231 = findViewById(R.id.one231);
        one232 = findViewById(R.id.one232);
        one233 = findViewById(R.id.one233);
        one241 = findViewById(R.id.one241);
        one242 = findViewById(R.id.one242);
        one243 = findViewById(R.id.one243);
        one251 = findViewById(R.id.one251);
        one252 = findViewById(R.id.one252);
        one253 = findViewById(R.id.one253);
        one261 = findViewById(R.id.one261);
        one262 = findViewById(R.id.one262);
        one263 = findViewById(R.id.one263);
        one271 = findViewById(R.id.one271);
        one272 = findViewById(R.id.one272);
        one273 = findViewById(R.id.one273);
        one281 = findViewById(R.id.one281);
        one282 = findViewById(R.id.one282);
        one283 = findViewById(R.id.one283);

        one311 = findViewById(R.id.one311);
        one312 = findViewById(R.id.one312);
        one313 = findViewById(R.id.one313);
        one321 = findViewById(R.id.one321);
        one322 = findViewById(R.id.one322);
        one323 = findViewById(R.id.one323);
        one331 = findViewById(R.id.one331);
        one332 = findViewById(R.id.one332);
        one333 = findViewById(R.id.one333);
        one341 = findViewById(R.id.one341);
        one342 = findViewById(R.id.one342);
        one343 = findViewById(R.id.one343);
        one351 = findViewById(R.id.one351);
        one352 = findViewById(R.id.one352);
        one353 = findViewById(R.id.one353);
        one361 = findViewById(R.id.one361);
        one362 = findViewById(R.id.one362);
        one363 = findViewById(R.id.one363);
        one371 = findViewById(R.id.one371);
        one372 = findViewById(R.id.one372);
        one373 = findViewById(R.id.one373);
        one381 = findViewById(R.id.one381);
        one382 = findViewById(R.id.one382);
        one383 = findViewById(R.id.one383);

        one411 = findViewById(R.id.one411);
        one412 = findViewById(R.id.one412);
        one413 = findViewById(R.id.one413);
        one421 = findViewById(R.id.one421);
        one422 = findViewById(R.id.one422);
        one423 = findViewById(R.id.one423);
        one431 = findViewById(R.id.one431);
        one432 = findViewById(R.id.one432);
        one433 = findViewById(R.id.one433);
        one441 = findViewById(R.id.one441);
        one442 = findViewById(R.id.one442);
        one443 = findViewById(R.id.one443);
        one451 = findViewById(R.id.one451);
        one452 = findViewById(R.id.one452);
        one453 = findViewById(R.id.one453);
        one461 = findViewById(R.id.one461);
        one462 = findViewById(R.id.one462);
        one463 = findViewById(R.id.one463);
        one471 = findViewById(R.id.one471);
        one472 = findViewById(R.id.one472);
        one473 = findViewById(R.id.one473);
        one481 = findViewById(R.id.one481);
        one482 = findViewById(R.id.one482);
        one483 = findViewById(R.id.one483);

        one511 = findViewById(R.id.one511);
        one512 = findViewById(R.id.one512);
        one513 = findViewById(R.id.one513);
        one521 = findViewById(R.id.one521);
        one522 = findViewById(R.id.one522);
        one523 = findViewById(R.id.one523);
        one531 = findViewById(R.id.one531);
        one532 = findViewById(R.id.one532);
        one533 = findViewById(R.id.one533);
        one541 = findViewById(R.id.one541);
        one542 = findViewById(R.id.one542);
        one543 = findViewById(R.id.one543);
        one551 = findViewById(R.id.one551);
        one552 = findViewById(R.id.one552);
        one553 = findViewById(R.id.one553);
        one561 = findViewById(R.id.one561);
        one562 = findViewById(R.id.one562);
        one563 = findViewById(R.id.one563);
        one571 = findViewById(R.id.one571);
        one572 = findViewById(R.id.one572);
        one573 = findViewById(R.id.one573);
        one581 = findViewById(R.id.one581);
        one582 = findViewById(R.id.one582);
        one583 = findViewById(R.id.one583);


        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        card4 = findViewById(R.id.card4);
        card5 = findViewById(R.id.card5);
        cardShow1 = findViewById(R.id.cardShow1);
        cardShow2 = findViewById(R.id.cardShow2);
        cardShow3 = findViewById(R.id.cardShow3);
        cardShow4 = findViewById(R.id.cardShow4);
        cardShow5 = findViewById(R.id.cardShow5);

        card1.setOnClickListener(v -> {
            cardShow1.setVisibility(View.VISIBLE);
            cardShow2.setVisibility(View.INVISIBLE);
            cardShow3.setVisibility(View.INVISIBLE);
            cardShow4.setVisibility(View.INVISIBLE);
            cardShow5.setVisibility(View.INVISIBLE);
        });
        card2.setOnClickListener(v -> {
            cardShow1.setVisibility(View.INVISIBLE);
            cardShow2.setVisibility(View.VISIBLE);
            cardShow3.setVisibility(View.INVISIBLE);
            cardShow4.setVisibility(View.INVISIBLE);
            cardShow5.setVisibility(View.INVISIBLE);
        });
        card3.setOnClickListener(v -> {
            cardShow1.setVisibility(View.INVISIBLE);
            cardShow2.setVisibility(View.INVISIBLE);
            cardShow3.setVisibility(View.VISIBLE);
            cardShow4.setVisibility(View.INVISIBLE);
            cardShow5.setVisibility(View.INVISIBLE);
        });
        card4.setOnClickListener(v -> {
            cardShow1.setVisibility(View.INVISIBLE);
            cardShow2.setVisibility(View.INVISIBLE);
            cardShow3.setVisibility(View.INVISIBLE);
            cardShow4.setVisibility(View.VISIBLE);
            cardShow5.setVisibility(View.INVISIBLE);
        });
        card5.setOnClickListener(v -> {
            cardShow1.setVisibility(View.INVISIBLE);
            cardShow2.setVisibility(View.INVISIBLE);
            cardShow3.setVisibility(View.INVISIBLE);
            cardShow4.setVisibility(View.INVISIBLE);
            cardShow5.setVisibility(View.VISIBLE);
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
                Toast.makeText(ForcastCurrentLocationActivity.this, "Location Disabled", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(ForcastCurrentLocationActivity.this,"Location get Successfully",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(ForcastCurrentLocationActivity.this, "Data Get Success", Toast.LENGTH_SHORT).show();

                forecastWeather weatherD =forecastWeather.fromJson(response);
                assert weatherD != null;
                updateUI(weatherD);
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(ForcastCurrentLocationActivity.this, "Get data Failure", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private  void updateUI(forecastWeather weather){

        dataReceivedTime.setText(weather.getDataReceivedTime());
        cityName.setText(weather.getCityName());
        countryCode.setText(weather.getCountryCode());

        one111.setText(weather.getOne111());
        one112.setText(weather.getOne112());
        one113.setText(weather.getOne113());
        one121.setText(weather.getOne121());
        one122.setText(weather.getOne122());
        one123.setText(weather.getOne123());
        one131.setText(weather.getOne131());
        one132.setText(weather.getOne132());
        one133.setText(weather.getOne133());
        one141.setText(weather.getOne141());
        one142.setText(weather.getOne142());
        one143.setText(weather.getOne143());
        one151.setText(weather.getOne151());
        one152.setText(weather.getOne152());
        one153.setText(weather.getOne153());
        one161.setText(weather.getOne161());
        one162.setText(weather.getOne162());
        one163.setText(weather.getOne163());
        one171.setText(weather.getOne171());
        one172.setText(weather.getOne172());
        one173.setText(weather.getOne173());
        one181.setText(weather.getOne181());
        one182.setText(weather.getOne182());
        one183.setText(weather.getOne183());

        one211.setText(weather.getOne211());
        one212.setText(weather.getOne212());
        one213.setText(weather.getOne213());
        one221.setText(weather.getOne221());
        one222.setText(weather.getOne222());
        one223.setText(weather.getOne223());
        one231.setText(weather.getOne231());
        one232.setText(weather.getOne232());
        one233.setText(weather.getOne233());
        one241.setText(weather.getOne241());
        one242.setText(weather.getOne242());
        one243.setText(weather.getOne243());
        one251.setText(weather.getOne251());
        one252.setText(weather.getOne252());
        one253.setText(weather.getOne253());
        one261.setText(weather.getOne261());
        one262.setText(weather.getOne262());
        one263.setText(weather.getOne263());
        one271.setText(weather.getOne271());
        one272.setText(weather.getOne272());
        one273.setText(weather.getOne273());
        one281.setText(weather.getOne281());
        one282.setText(weather.getOne282());
        one283.setText(weather.getOne283());

        one311.setText(weather.getOne311());
        one312.setText(weather.getOne312());
        one313.setText(weather.getOne313());
        one321.setText(weather.getOne321());
        one322.setText(weather.getOne322());
        one323.setText(weather.getOne323());
        one331.setText(weather.getOne331());
        one332.setText(weather.getOne332());
        one333.setText(weather.getOne333());
        one341.setText(weather.getOne341());
        one342.setText(weather.getOne342());
        one343.setText(weather.getOne343());
        one351.setText(weather.getOne351());
        one352.setText(weather.getOne352());
        one353.setText(weather.getOne353());
        one361.setText(weather.getOne361());
        one362.setText(weather.getOne362());
        one363.setText(weather.getOne363());
        one371.setText(weather.getOne371());
        one372.setText(weather.getOne372());
        one373.setText(weather.getOne373());
        one381.setText(weather.getOne381());
        one382.setText(weather.getOne382());
        one383.setText(weather.getOne383());

        one411.setText(weather.getOne411());
        one412.setText(weather.getOne412());
        one413.setText(weather.getOne413());
        one421.setText(weather.getOne421());
        one422.setText(weather.getOne422());
        one423.setText(weather.getOne423());
        one431.setText(weather.getOne431());
        one432.setText(weather.getOne432());
        one433.setText(weather.getOne433());
        one441.setText(weather.getOne441());
        one442.setText(weather.getOne442());
        one443.setText(weather.getOne443());
        one451.setText(weather.getOne451());
        one452.setText(weather.getOne452());
        one453.setText(weather.getOne453());
        one461.setText(weather.getOne461());
        one462.setText(weather.getOne462());
        one463.setText(weather.getOne463());
        one471.setText(weather.getOne471());
        one472.setText(weather.getOne472());
        one473.setText(weather.getOne473());
        one481.setText(weather.getOne481());
        one482.setText(weather.getOne482());
        one483.setText(weather.getOne483());

        one511.setText(weather.getOne511());
        one512.setText(weather.getOne512());
        one513.setText(weather.getOne513());
        one521.setText(weather.getOne521());
        one522.setText(weather.getOne522());
        one523.setText(weather.getOne523());
        one531.setText(weather.getOne531());
        one532.setText(weather.getOne532());
        one533.setText(weather.getOne533());
        one541.setText(weather.getOne541());
        one542.setText(weather.getOne542());
        one543.setText(weather.getOne543());
        one551.setText(weather.getOne551());
        one552.setText(weather.getOne552());
        one553.setText(weather.getOne553());
        one561.setText(weather.getOne561());
        one562.setText(weather.getOne562());
        one563.setText(weather.getOne563());
        one571.setText(weather.getOne571());
        one572.setText(weather.getOne572());
        one573.setText(weather.getOne573());
        one581.setText(weather.getOne581());
        one582.setText(weather.getOne582());
        one583.setText(weather.getOne583());

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