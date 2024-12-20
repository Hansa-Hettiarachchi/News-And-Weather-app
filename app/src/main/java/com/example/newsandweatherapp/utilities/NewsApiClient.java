package com.example.newsandweatherapp.utilities;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsApiClient {
    private static final String BASE_URL = "https://newsapi.org/v2/";
    private static NewsApiClient newsApiClient;
    private static Retrofit retrofit;

    private NewsApiClient(){
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static synchronized NewsApiClient getInstance(){
        if(newsApiClient == null){
            newsApiClient = new NewsApiClient();
        }
        return newsApiClient;
    }

    public NewsApiInterface getApi(){
        return retrofit.create(NewsApiInterface.class);
    }
}
