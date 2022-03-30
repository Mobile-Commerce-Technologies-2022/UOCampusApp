package com.example.uocampus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uocampus.R;
import com.example.uocampus.model.FacilityModel;

import java.util.List;

public class FacilityViewAdapter extends RecyclerView.Adapter<FacilityViewAdapter.FacilityViewHolder>{
    private Context context;
    private final List<FacilityModel> facilityModelList;
    private OnFacilityListener onFacilityListener;

    public FacilityViewAdapter(Context context,
                               List<FacilityModel> facilityModelList,
                               OnFacilityListener onFacilityListener) {
        this.context = context;
        this.facilityModelList = facilityModelList;
        this.onFacilityListener = onFacilityListener;
    }

    @NonNull
    @Override
    public FacilityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_facility_item,
                                                         parent,
                                              false);
        return new FacilityViewHolder(view, onFacilityListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FacilityViewHolder holder, int position) {
        holder.tvFacility.setText(facilityModelList.get(position).getNAME());
        holder.tvTime.setText("Estimating time...");
        holder.tvDistance.setText("Estimating distance...");
        holder.btnLocate.setText("Locate Me");
    }

    @Override
    public int getItemCount() {
        return facilityModelList.size();
    }

    public class FacilityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvFacility;
        TextView tvTime;
        TextView tvDistance;
        Button btnLocate;
        OnFacilityListener onFacilityListener;
        public FacilityViewHolder(@NonNull View itemView, OnFacilityListener listener) {
            super(itemView);
            tvFacility = itemView.findViewById(R.id.tv_facility_item);
            tvTime = itemView.findViewById(R.id.tv_estimate_time);
            tvDistance = itemView.findViewById(R.id.tv_direct_distance);
            btnLocate = itemView.findViewById(R.id.btn_locate_me);
            onFacilityListener = listener;
            btnLocate.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            btnLocate.setText(btnLocate.getText().equals("Clear") ? "Locate Me" : "Clear");
            onFacilityListener.onFacilityClick(getAdapterPosition());
        }
    }

    public interface OnFacilityListener {
        void onFacilityClick(int position);
    }
}