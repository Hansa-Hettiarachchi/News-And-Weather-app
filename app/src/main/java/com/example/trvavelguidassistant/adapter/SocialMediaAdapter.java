package com.example.trvavelguidassistant.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trvavelguidassistant.R;
import com.example.trvavelguidassistant.model.SocialMediaModel;
import com.example.trvavelguidassistant.utilities.LikeSocialMediaSelectItem;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SocialMediaAdapter extends RecyclerView.Adapter<SocialMediaAdapter.socialMediaVH> {

    Context context1;
    ArrayList<SocialMediaModel> items1;
    private LikeSocialMediaSelectItem likeSocialMediaSelectItem;

    public SocialMediaAdapter(Context context1, ArrayList<SocialMediaModel> items1, LikeSocialMediaSelectItem likeSocialMediaSelectItem) {
        this.context1 = context1;
        this.items1 = items1;
        this.likeSocialMediaSelectItem = likeSocialMediaSelectItem;
    }

    @NonNull
    @Override
    public socialMediaVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context1)
                .inflate(R.layout.social_media_item,parent,false);
        return new socialMediaVH(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull socialMediaVH holder, @SuppressLint("RecyclerView") int position) {

        SocialMediaModel socialMediaModel = items1.get(position);
        holder.userName.setText(socialMediaModel.getUserName());
        holder.likeCount.setText(Long.toString(socialMediaModel.getLikeCount()));
        holder.location.setText(socialMediaModel.getLocation());

        String imageURL1 = socialMediaModel.getUserPic();
        Picasso.get().load(imageURL1).into(holder.userPic);

        String imageURL2 = socialMediaModel.getPhoto();
        Picasso.get().load(imageURL2).into(holder.photo);

        holder.beforeClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeSocialMediaSelectItem.onItemClicked(items1.get(position));
//                holder.afterClick.setVisibility(View.VISIBLE);
//                holder.beforeClick.setVisibility(View.INVISIBLE);
            }
        });

//        holder.afterClick.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                likeSocialMediaSelectItem.onItemClicked(items1.get(position));
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return items1.size();
    }

    public static class socialMediaVH extends RecyclerView.ViewHolder {

        TextView userName,location,likeCount;
        ImageView userPic,photo,afterClick,beforeClick;

        public socialMediaVH(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName);
            location = itemView.findViewById(R.id.location);
            userPic = itemView.findViewById(R.id.userPic);
            photo = itemView.findViewById(R.id.photo);
            likeCount = itemView.findViewById(R.id.likeCount);
            afterClick = itemView.findViewById(R.id.afterClick);
            beforeClick = itemView.findViewById(R.id.beforeClick);
        }
    }
}