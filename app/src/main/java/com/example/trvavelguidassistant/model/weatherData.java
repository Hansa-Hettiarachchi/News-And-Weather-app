package com.example.trvavelguidassistant.model;

import android.annotation.SuppressLint;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class weatherData {

    private String micon;
    private String nameOfCity, weatherState, Temperature,dataReceivedTime,humidity,pressure,windSpeed,visibility,sunRise,sunSet,countryCode,timeZone;
    private int mCondition;

    public static weatherData fromJson(JSONObject jsonObject)
    {
        try
        {
            weatherData weatherD=new weatherData();
            weatherD.nameOfCity=jsonObject.getString("name");

            weatherD.mCondition=jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");

            weatherD.weatherState=jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");

            weatherD.micon=updateWeatherIcon(weatherD.mCondition);

            double tempResult=jsonObject.getJSONObject("main").getDouble("temp")-273.15;
            int roundedValue=(int)Math.rint(tempResult);
            weatherD.Temperature=Integer.toString(roundedValue);

            weatherD.countryCode=jsonObject.getJSONObject("sys").getString("country");

            Double tz = jsonObject.getDouble("timezone")/3600.00;
            weatherD.timeZone = Double.toString(tz);

            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            long nowTime = jsonObject.getLong("dt");
            Date date1 = new java.util.Date(nowTime*1000L);
            weatherD.dataReceivedTime = sdf.format(date1);

            long srTime = jsonObject.getJSONObject("sys").getLong("sunrise");
            Date date2 = new java.util.Date(srTime*1000L);
            weatherD.sunRise = sdf.format(date2);

            long ssTime = jsonObject.getJSONObject("sys").getLong("sunset");
            Date date3 = new java.util.Date(ssTime*1000L);
            weatherD.sunSet = sdf.format(date3);

            double preResult=jsonObject.getJSONObject("main").getDouble("pressure");
            int roundedPreValue=(int)Math.rint(preResult);
            weatherD.pressure=Integer.toString(roundedPreValue);

            double humResult=jsonObject.getJSONObject("main").getDouble("humidity");
            int roundedHumValue=(int)Math.rint(humResult);
            weatherD.humidity=Integer.toString(roundedHumValue);

            weatherD.visibility=jsonObject.getString("visibility");

            weatherD.windSpeed = jsonObject.getJSONObject("wind").getString("speed");


            return weatherD;
        }


        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }


    private static String updateWeatherIcon(int condition)
    {
        if(condition>=0 && condition<=300)
        {
            return "thunderstrom1";
        }
        else if(condition>=300 && condition<=500)
        {
            return "lightrain";
        }
        else if(condition>=500 && condition<=600)
        {
            return "shower";
        }
        else  if(condition>=600 && condition<=700)
        {
            return "snow2";
        }
        else if(condition>=701 && condition<=771)
        {
            return "fog";
        }
        else if(condition>=772 && condition<800)
        {
            return "overcast";
        }
        else if(condition==800)
        {
            return "sunny";
        }
        else if(condition>=801 && condition<=804)
        {
            return "cloudy";
        }
        else  if(condition>=900 && condition<=902)
        {
            return "thunderstrom1";
        }
        if(condition==903)
        {
            return "snow1";
        }
        if(condition==904)
        {
            return "sunny";
        }
        if(condition>=905 && condition<=1000)
        {
            return "thunderstrom2";
        }

        return "dunno";
    }

    public String getNameOfCity() {
        return nameOfCity;
    }

    public String getWeatherState() {
        return weatherState;
    }

    public String getTemperature() {
        return Temperature+" °C";
    }

    public String getDataReceivedTime() {
        return dataReceivedTime;
    }

    public String getHumidity() {
        return humidity+" %";
    }

    public String getPressure() {
        return pressure+" hPa";
    }

    public String getWindSpeed() {
        return windSpeed+" m/sec";
    }

    public String getVisibility() {
        return visibility+" m";
    }

    public String getSunRise() {
        return sunRise;
    }

    public String getSunSet() {
        return sunSet;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getMicon() {
        return micon;
    }

    public String getTimeZone() {
        return timeZone+"h GMT";
    }
}
