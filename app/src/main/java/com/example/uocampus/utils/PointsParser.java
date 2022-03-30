package com.example.uocampus.utils;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Credit: https://github.com/Vysh01/android-maps-directions
 */

public class PointsParser extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
    private static final String TAG = PointsParser.class.getSimpleName();
    private final TaskLoadedCallback taskCallback;
    private Result result;

    public PointsParser(Context mContext, Result result) {
        this.taskCallback = (TaskLoadedCallback) mContext;
        this.result = result;
    }

    // Parsing the data in non-ui thread
    @Override
    protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

        List<List<HashMap<String, String>>> routes = null;

        try {
            DataParser parser = new DataParser();
            parser.setResult(this.result);
            Log.d(TAG, parser.toString());

            // Starts parsing data
            routes = parser.parseJSON();
            Log.d(TAG, "Executing routes");
            Log.d(TAG, routes.toString());

        } catch (Exception e) {
            Log.d(TAG, e.toString());
            e.printStackTrace();
        }
        return routes;
    }

    // Executes in UI thread, after the parsing process
    @Override
    protected void onPostExecute(List<List<HashMap<String, String>>> result) {
        ArrayList<LatLng> points;
        PolylineOptions lineOptions = null;
        // Traversing through all the routes
        for (int i = 0; i < result.size(); i++) {
            points = new ArrayList<>();
            lineOptions = new PolylineOptions();
            // Fetching i-th route
            List<HashMap<String, String>> path = result.get(i);
            // Fetching all the points in i-th route
            for (int j = 0; j < path.size(); j++) {
                HashMap<String, String> point = path.get(j);
                double lat = Double.parseDouble(point.get("lat"));
                double lng = Double.parseDouble(point.get("lng"));
                LatLng position = new LatLng(lat, lng);
                points.add(position);
            }
            // Adding all the points in the route to LineOptions
            lineOptions.width(10);
            lineOptions.color(Color.BLUE);
            lineOptions.addAll(points);
            Log.d(TAG, "onPostExecute line options decoded");
        }

        // Drawing polyline in the Google Map for the i-th route
        if (lineOptions != null) {
            //mMap.addPolyline(lineOptions);
            taskCallback.onTaskDone(lineOptions);

        } else {
            Log.d(TAG, "without Polylines drawn");
        }
    }
}