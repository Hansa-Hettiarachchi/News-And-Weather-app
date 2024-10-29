package com.example.trvavelguidassistant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trvavelguidassistant.R;
import com.example.trvavelguidassistant.model.EventModel;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<eventVH>{

    private final Context context;
    private final List<EventModel> items;


    public EventAdapter(Context context, List<EventModel> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public eventVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_event,parent,false);
        return new eventVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull eventVH holder, int position) {
        EventModel eventModel = items.get(position);
        holder.titleView.setText(eventModel.getTitle());
        holder.descriptionView.setText(eventModel.getDescription());
        holder.startTimeView.setText(eventModel.getStart());
        holder.endTimeView.setText(eventModel.getEnd());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

class eventVH extends RecyclerView.ViewHolder{

    TextView titleView,descriptionView,startTimeView,endTimeView;

    public eventVH(@NonNull View itemView) {
        super(itemView);
        titleView = itemView.findViewById(R.id.titleView);
        descriptionView = itemView.findViewById(R.id.descriptionView);
        startTimeView = itemView.findViewById(R.id.startTimeView);
        endTimeView = itemView.findViewById(R.id.endTimeView);
    }
}
