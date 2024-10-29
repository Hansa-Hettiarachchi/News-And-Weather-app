package com.example.trvavelguidassistant.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trvavelguidassistant.R;
import com.example.trvavelguidassistant.adapter.NewsAdapter;
import com.example.trvavelguidassistant.model.Articles;
import com.example.trvavelguidassistant.model.Headlines;
import com.example.trvavelguidassistant.utilities.Constants;
import com.example.trvavelguidassistant.utilities.NewsApiClient;
import com.example.trvavelguidassistant.utilities.PreferenceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity {

    SwipeRefreshLayout SRNews;
    RecyclerView recyclerViewNews;
    final String API_NEWS = "dbbe27a861074c01bd457ab71641af12";
    NewsAdapter newsAdapter;
    List<Articles> articles = new ArrayList<>();
    EditText textSearch3;
    Button buttonSearch3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);



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


        SRNews = findViewById(R.id.SRNews);
        recyclerViewNews = findViewById(R.id.recyclerViewNews);

        textSearch3 = findViewById(R.id.textSearch3);
        buttonSearch3 = findViewById(R.id.buttonSearch3);

        recyclerViewNews.setLayoutManager(new LinearLayoutManager(this));
        String country = getCountry();

        SRNews.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveJson("",country,API_NEWS);
            }
        });

        retrieveJson("",country,API_NEWS);

        buttonSearch3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!textSearch3.getText().toString().equals("")){

                    SRNews.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            retrieveJson(textSearch3.getText().toString(),country,API_NEWS);
                        }
                    });

                    retrieveJson(textSearch3.getText().toString(),country,API_NEWS);

                }else{
                    SRNews.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            retrieveJson("",country,API_NEWS);
                        }
                    });

                    retrieveJson("",country,API_NEWS);
                }
            }
        });
    }

    public void retrieveJson(String query, String country , String apikey){
        SRNews.setRefreshing(true);
        Call<Headlines> call;
        if(!textSearch3.getText().toString().equals("")){
            call = NewsApiClient.getInstance().getApi().getSpecificData(query,apikey);
        }else{
            call = NewsApiClient.getInstance().getApi().getHeadlines(country,apikey);
        }


        call.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                if(response.isSuccessful() && response.body().getArticles() != null){
                    SRNews.setRefreshing(false);
                    articles.clear();
                    articles = response.body().getArticles();
                    newsAdapter = new NewsAdapter(NewsActivity.this,articles);
                    recyclerViewNews.setAdapter(newsAdapter);
                }
            }

            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {
                SRNews.setRefreshing(false);
                Toast.makeText(NewsActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getCountry(){
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }
}