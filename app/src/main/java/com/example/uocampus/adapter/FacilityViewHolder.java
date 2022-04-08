package com.example.uocampus.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uocampus.R;

public class FacilityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView tvFacility;
    TextView tvTime;
    TextView tvDistance;
    Button btnLocate;
    Button btnDirection;
    OnLocationListener onLocationListener;
    OnDirectionListener onDirectionListener;

    public FacilityViewHolder(@NonNull View itemView,
                              OnLocationListener l1,
                              OnDirectionListener l2) {
        super(itemView);
        tvFacility = itemView.findViewById(R.id.tv_facility_item);
        tvTime = itemView.findViewById(R.id.tv_estimate_time);
        tvDistance = itemView.findViewById(R.id.tv_direct_distance);
        btnLocate = itemView.findViewById(R.id.btn_locate_me);
        btnDirection = itemView.findViewById(R.id.btn_get_direction);
        onLocationListener = l1;
        onDirectionListener = l2;
        btnLocate.setOnClickListener(this);
        btnDirection.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_locate_me) {
            btnLocate.setText(btnLocate.getText().equals("Clear") ? "Locate Me" : "Clear");
            onLocationListener.onLocationClick(getAdapterPosition());
        } else if (view.getId() == R.id.btn_get_direction) {
            btnLocate.setText("Clear");
            onDirectionListener.onDirectionClick(getAdapterPosition(), true);
        }

    }
}
