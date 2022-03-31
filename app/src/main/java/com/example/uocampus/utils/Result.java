package com.example.uocampus.utils;

import com.google.android.gms.maps.model.LatLng;

public class Result {
    LatLng origin;
    LatLng dest;
    String mode;
    String data = "";
    String duration = "";
    String distance = "";

    public Result(LatLng origin, LatLng dest, String mode) {
        this.origin = origin;
        this.dest = dest;
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public LatLng getOrigin() {
        return origin;
    }

    public void setOrigin(LatLng origin) {
        this.origin = origin;
    }

    public LatLng getDest() {
        return dest;
    }

    public void setDest(LatLng dest) {
        this.dest = dest;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
