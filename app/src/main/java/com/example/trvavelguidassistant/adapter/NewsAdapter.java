package com.example.trvavelguidassistant.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trvavelguidassistant.R;
import com.example.trvavelguidassistant.activities.DetailedNewsActivity;
import com.example.trvavelguidassistant.model.Articles;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.VH3> {

    Context context;
    List<Articles> articles;

    public NewsAdapter(Context context, List<Articles> articles) {
        this.context = context;
        this.articles = articles;
    }

    @NonNull
    @Override
    public VH3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_items,parent,false);
        return new VH3(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH3 holder, int position) {
        Articles a = articles.get(position);
        holder.tvTitle.setText(a.getTitle());
        holder.tvSource.setText(a.getSource().getName());
        holder.tvDate.setText(dateTime(a.getPublishedAt()));

        String imageURL = a.getUrlToImage();
        String url = a.getUrl();

        Picasso.get().load(imageURL).into(holder.newsImage);

        holder.newsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailedNewsActivity.class);
                intent.putExtra("title",a.getTitle());
                intent.putExtra("source",a.getSource().getName());
                intent.putExtra("desc",a.getDescription());
                intent.putExtra("time",dateTime(a.getPublishedAt()));
                intent.putExtra("imageUrl",a.getUrlToImage());
                intent.putExtra("url",a.getUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class VH3 extends RecyclerView.ViewHolder{

        TextView tvTitle,tvSource,tvDate;
        ImageView newsImage;
        CardView newsCard;

        public VH3(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvSource = itemView.findViewById(R.id.tvSource);
            tvDate = itemView.findViewById(R.id.tvDate);
            newsImage = itemView.findViewById(R.id.newsImage);
            newsCard = itemView.findViewById(R.id.newsCard);
        }
    }

    public String dateTime(String t){
        PrettyTime prettyTime = new PrettyTime(new Locale(getCountry()));
        String time =null;
        try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:",Locale.ENGLISH);
            Date date = simpleDateFormat.parse(t);
            time = prettyTime.format(date);
        } catch (ParseException exception) {
            exception.printStackTrace();
        }
        return time;
    }
    public String getCountry(){
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }
}
