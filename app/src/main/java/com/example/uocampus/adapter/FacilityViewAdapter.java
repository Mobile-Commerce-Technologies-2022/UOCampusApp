package com.example.uocampus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uocampus.R;
import com.example.uocampus.model.FacilityModel;

import java.util.List;

public class FacilityViewAdapter extends RecyclerView.Adapter<FacilityViewHolder>{
    private Context context;
    private final List<FacilityModel> facilityModelList;
    private OnLocationListener onLocationListener;
    private OnDirectionListener onDirectionListener;
    public FacilityViewAdapter(Context context,
                               List<FacilityModel> facilityModelList,
                               OnLocationListener onLocationListener,
                               OnDirectionListener onDirectionListener) {
        this.context = context;
        this.facilityModelList = facilityModelList;
        this.onLocationListener = onLocationListener;
        this.onDirectionListener = onDirectionListener;
    }

    @NonNull
    @Override
    public FacilityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_facility_item,
                                                         parent,
                                              false);
        return new FacilityViewHolder(view, onLocationListener, onDirectionListener);
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


}

