package com.example.uocampus.model.Q_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uocampus.R;

import java.util.ArrayList;

public class MyAdaptor extends RecyclerView.Adapter<MyAdaptor.MyViewHolder> {
    private Context context;
    private ArrayList nameid, sid;

    public MyAdaptor(Context context, ArrayList nameid, ArrayList sid) {
        this.context = context;
        this.nameid = nameid;
        this.sid = sid;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.userentry,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdaptor.MyViewHolder holder, int position) {
        holder.nameid.setText(String.valueOf(nameid.get(position)));
        holder.sid.setText(String.valueOf(sid.get(position)));

    }

    @Override
    public int getItemCount() {
        return nameid.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameid,sid;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameid = itemView.findViewById(R.id.textname);
            sid = itemView.findViewById(R.id.textsid);
        }
    }
}
