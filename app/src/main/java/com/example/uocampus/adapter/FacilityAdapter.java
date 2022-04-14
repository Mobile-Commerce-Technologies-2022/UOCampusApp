package com.example.uocampus.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.uocampus.R;
import com.example.uocampus.activity.NavigatorActivity;
import com.example.uocampus.model.FacilityModel;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FacilityAdapter extends ArrayAdapter<FacilityModel>{

    private Context mContext;
    private int mResource;

    public FacilityAdapter(@NonNull Context context, int resource, @NonNull ArrayList<FacilityModel> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        convertView = layoutInflater.inflate(mResource, parent, false);

        TextView tvName = convertView.findViewById(R.id.tv_facility_name);
        TextView tvTime = convertView.findViewById(R.id.tv_arriving_time);
        TextView tvDistance = convertView.findViewById(R.id.tv_estimate_distance);
        Button btnLocate = convertView.findViewById(R.id.btn_locate);
        Button btnDirection = convertView.findViewById(R.id.btn_direction);

        tvName.setText(getItem(position).getNAME());
        tvDistance.setText("pending");
        tvTime.setText("pending");

        btnLocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavigatorActivity) mContext).onLocationClick(position);
            }
        });

        btnDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavigatorActivity) mContext).onDirectionClick(position, true);
            }
        });


        return convertView;
    }
}
