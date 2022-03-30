package com.example.uocampus.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Credit: https://github.com/Vysh01/android-maps-directions
 */

public class FetchURL extends AsyncTask<String, Void, String> {
    private static final String TAG = FetchURL.class.getSimpleName();
    private final Context mContext;
    private Result result;
    public FetchURL(Context mContext, LatLng origin, LatLng dest, String mode) {
        this.mContext = mContext;
        this.result = new Result(origin, dest, mode);
    }

    @Override
    protected String doInBackground(String... strings) {
        // For storing data from web service
        String data = "";
        try {
            // Fetching the data from web service
            data = downloadUrl(getDirectionUrl(this.result.origin, this.result.dest, this.result.mode));
            Log.d(TAG, "Background task data " + data);
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
        return data;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        PointsParser parserTask = new PointsParser(mContext, this.result);
        // Invokes the thread for parsing the JSON data
        parserTask.execute(s);
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
            // Connecting to url
            urlConnection.connect();
            // Reading data from url
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            Log.d(TAG, "Downloaded URL: " + data);
            br.close();
        } catch (Exception e) {
            Log.d(TAG, "Exception downloading URL: " + e);
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        this.result.setData(data);
        return data;
    }

    private String getDirectionUrl(LatLng origin, LatLng dest, String mode) {
        return String.format("https://maps.googleapis.com/maps/api/directions/json?origin=%s,%s" +
                        "&destination=%s,%s" +
                        "&mode=%s"+
                        "&key=%s",
                origin.latitude, origin.longitude,
                dest.latitude, dest.longitude,
                mode,
                "AIzaSyCduND9uK7D0MLbyNKzqjJe50wR53nWvcE");
    }
}

class Result {
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