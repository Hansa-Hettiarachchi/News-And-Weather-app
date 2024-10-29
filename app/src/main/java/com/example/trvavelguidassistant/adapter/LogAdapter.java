package com.example.trvavelguidassistant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trvavelguidassistant.R;
import com.example.trvavelguidassistant.model.LogModel;

import java.util.List;

public class LogAdapter extends RecyclerView.Adapter<logVH> {

    private final Context context;
    private final List<LogModel> items;
    public logClickListener logClickListener;

    public interface logClickListener{
        void selectedLog(LogModel logModel);
    }

    public LogAdapter(Context context, List<LogModel> items,logClickListener logClickListener) {
        this.context = context;
        this.items = items;
        this.logClickListener = logClickListener;
    }

    @NonNull
    @Override
    public logVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_logs,parent,false);
        return new logVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull logVH holder, int position) {
        LogModel logModel = items.get(position);
        holder.dateView.setText(items.get(position).getDate());
        holder.logNumberView.setText(String.valueOf(items.get(position).getNum()));
        holder.nameView1.setText(String.valueOf(items.get(position).getName()));

        holder.itemView.setOnClickListener(v -> logClickListener.selectedLog(logModel));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

class logVH extends RecyclerView.ViewHolder{

    TextView logNumberView,dateView,nameView1;

    public logVH(@NonNull View itemView) {
        super(itemView);
        dateView = itemView.findViewById(R.id.dateView);
        logNumberView = itemView.findViewById(R.id.logNumberView);
        nameView1 = itemView.findViewById(R.id.nameView1);
    }
}