package com.example.uocampus.model;

public class FacilityModel {
    private final String NAME;
    private double directDistance;
    private long estimateTime;

    public FacilityModel(String NAME, double directDistance, long estimateTime) {
        this.NAME = NAME;
        this.directDistance = directDistance;
        this.estimateTime = estimateTime;
    }

    public FacilityModel(String NAME) {
        this.NAME = NAME;
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
