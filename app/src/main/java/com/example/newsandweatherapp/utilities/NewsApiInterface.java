package com.example.newsandweatherapp.utilities;

import com.example.newsandweatherapp.model.Headlines;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiInterface {
    @GET("top-headlines")
    Call<Headlines> getHeadlines(
       @Query("country") String country,
       @Query("apiKey") String apiKey
    );
    @GET("everything")
    Call<Headlines> getSpecificData(
            @Query("q") String q,
            @Query("apiKey") String apiKey
    );

}
