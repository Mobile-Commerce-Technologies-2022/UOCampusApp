package com.example.uocampus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uocampus.R;
import com.example.uocampus.model.FacilityModel;

import java.util.List;

public class FacilityViewAdapter extends RecyclerView.Adapter<FacilityViewHolder>{
    private Context context;
    private final List<FacilityModel> facilityModelList;

    public FacilityViewAdapter(Context context, List<FacilityModel> facilityModelList) {
        this.context = context;
        this.facilityModelList = facilityModelList;
    }

    @NonNull
    @Override
    public FacilityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_facility_item,
                                                         parent,
                                              false);
        return new FacilityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacilityViewHolder holder, int position) {
        holder.tvFacility.setText(facilityModelList.get(position).getNAME());
    }

    @Override
    public int getItemCount() {
        return facilityModelList.size();
    }
}

class FacilityViewHolder extends RecyclerView.ViewHolder {
    TextView tvFacility;

    public FacilityViewHolder(@NonNull View itemView) {
        super(itemView);
        tvFacility = itemView.findViewById(R.id.tv_facility_item);
    }
}


