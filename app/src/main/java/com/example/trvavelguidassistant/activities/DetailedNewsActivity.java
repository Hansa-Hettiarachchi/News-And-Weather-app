package com.example.trvavelguidassistant.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.trvavelguidassistant.R;
import com.example.trvavelguidassistant.utilities.Constants;
import com.example.trvavelguidassistant.utilities.PreferenceManager;
import com.squareup.picasso.Picasso;

public class DetailedNewsActivity extends AppCompatActivity {

    TextView tvTitle,tvSource,tvTime,tvDesc;
    ImageView newsImage2;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_news);

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

        tvTitle= findViewById(R.id.tvTitle);
        //tvSource= findViewById(R.id.tvSource);
        //tvTime= findViewById(R.id.tvDate);
        tvDesc= findViewById(R.id.tvDesc);
        webView= findViewById(R.id.webView);
        //newsImage2 = findViewById(R.id.newsImage2);


        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String source = intent.getStringExtra("source");
        String time = intent.getStringExtra("time");
        String desc = intent.getStringExtra("desc");
        String imageUrl = intent.getStringExtra("imageUrl");
        String url = intent.getStringExtra("url");

        tvTitle.setText(title);
        //tvSource.setText(source);
        //tvTime.setText(time);
        tvDesc.setText(desc);

       // Picasso.get().load(imageUrl).into(newsImage2);

        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }
}