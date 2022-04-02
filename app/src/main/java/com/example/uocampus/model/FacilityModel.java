package com.example.uocampus.model;
import com.google.android.gms.maps.model.LatLng;

public class FacilityModel {
    private final String NAME;
    private double directDistance;
    private long estimateTime;
    private final LatLng latLng;

    public LatLng getLatLng() {
        return latLng;
    }

    public FacilityModel(String NAME, LatLng latLng) {
        this.NAME = NAME;
        this.latLng = latLng;
    }

    public String getNAME() {
        return NAME;
    }

    public double getDirectDistance() {
        return directDistance;
    }

    public void setDirectDistance(double directDistance) {
        this.directDistance = directDistance;
    }

    public long getEstimateTime() {
        return estimateTime;
    }

    public void setEstimateTime(long estimateTime) {
        this.estimateTime = estimateTime;
    }

    @Override
    public String toString() {
        return "FacilityModel{" +
                "NAME='" + NAME + '\'' +
                '}';
    }
}
