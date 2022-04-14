package com.example.uocampus.model;
import com.google.android.gms.maps.model.LatLng;

public class FacilityModel {
    private final String NAME;
    private String directDistance;
    private String estimateTime;
    private final LatLng latLng;

    public LatLng getLatLng() {
        return latLng;
    }

    public FacilityModel(String NAME, LatLng latLng) {
        this.NAME = NAME;
        this.latLng = latLng;
        this.directDistance = "pending";
        this.estimateTime = "pending";
    }

    public String getNAME() {
        return NAME;
    }

    public String getDirectDistance() {
        return directDistance;
    }

    public void setDirectDistance(String directDistance) {
        this.directDistance = directDistance;
    }

    public String getEstimateTime() {
        return estimateTime;
    }

    public void setEstimateTime(String estimateTime) {
        this.estimateTime = estimateTime;
    }

    @Override
    public String toString() {
        return "FacilityModel{" +
                "NAME='" + NAME + '\'' +
                '}';
    }
}
