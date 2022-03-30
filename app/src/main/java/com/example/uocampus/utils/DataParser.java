package com.example.uocampus.utils;


import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Credit: https://github.com/Vysh01/android-maps-directions
 */

public class DataParser {
    private Result result;

    public void setResult(Result result) {
        this.result = result;
    }

    public List<List<HashMap<String, String>>> parseJSON() {
        if (this.result.getData().isEmpty()) {
            System.out.println("Data not available");
            return null;
        }
        List<List<HashMap<String, String>>> routes = new ArrayList<>();
        try {
            JSONObject jObject = new JSONObject(this.result.getData());
            JSONArray jRoutes = jObject.getJSONArray("routes");
            for (int i = 0; i < jRoutes.length(); i++) {
                JSONArray jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");

                String duration = (String) ((JSONObject) ((JSONObject) jLegs.get(0)).get("duration")).get("text");
                this.result.setDuration(duration);
                String distance = (String) ((JSONObject) ((JSONObject) jLegs.get(0)).get("distance")).get("text");
                this.result.setDistance(distance);
                List<HashMap<String, String>> path = new ArrayList<>();
                for (int j = 0; j < jLegs.length(); j++) {
                    JSONArray jSteps = ((JSONObject) jLegs.get(j)).getJSONArray("steps");

                    //Traversing all steps
                    for (int k = 0; k < jSteps.length(); k++) {
                        String polyline;
                        polyline = (String) ((JSONObject) ((JSONObject) jSteps.get(k)).get("polyline")).get("points");
                        List<LatLng> list = decodePoly(polyline);

                        // Traversing all points
                        for (int l = 0; l < list.size(); l++) {
                            HashMap<String, String> hm = new HashMap<>();
                            hm.put("lat", Double.toString((list.get(l)).latitude));
                            hm.put("lng", Double.toString((list.get(l)).longitude));
                            path.add(hm);
                        }
                    }
                    routes.add(path);
                }
            }
            Log.d("RouteParser", this.result.getDistance() + ":" + this.result.getDuration());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return routes;
    }

    /**
     * Method to decode polyline points
     * Courtesy : https://jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java
     */
    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }
}