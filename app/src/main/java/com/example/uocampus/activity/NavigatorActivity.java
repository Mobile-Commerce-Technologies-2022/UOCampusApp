package com.example.uocampus.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uocampus.R;
import com.example.uocampus.adapter.FacilityViewAdapter;
import com.example.uocampus.model.FacilityModel;
import com.example.uocampus.singleton.UtilLoader;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NavigatorActivity extends AppCompatActivity implements LocationListener,
                                                                    OnMapReadyCallback{
    private static final String TAG = NavigatorActivity.class.getSimpleName();
    private GoogleMap mMap;
    protected LocationManager locationManager;
    private final int TIME_INTERVAL = 2500;
    private final int MIN_DISTANCE_M = 0;
    private static final int DEFAULT_ZOOM = 15;
    private Location loc;
    private Marker userMarker;

    RecyclerView mRecyclerView;
    FacilityViewAdapter facilityViewAdapter;
    List<FacilityModel> mFacilityList = new ArrayList<>();

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitiy_navigator);
        // Facility Initialization
        facilityViewAdapter = new FacilityViewAdapter(NavigatorActivity.this, mFacilityList);
        mRecyclerView = findViewById(R.id.rv_facility);
        mRecyclerView.setAdapter(facilityViewAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NavigatorActivity.this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        loadFacility();

        // Google Map Initialization
        UtilLoader.getInstance().requestPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION);
        loadLocationTracker();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this::onMapReady);
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        userMarker = mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Your Location"));

        for(FacilityModel facilityModel : mFacilityList) {
            mMap.addMarker(new MarkerOptions()
                    .position(facilityModel.getLatLng())
                    .title(facilityModel.getNAME()));
        }
        Log.d(TAG, "onMapReady");
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        this.loc = location;
        if(mMap != null) {
            userMarker.remove();
            LatLng latLng = new LatLng(loc.getLatitude(), loc.getLongitude());
            userMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Your Location"));
            mMap.setMinZoomPreference(DEFAULT_ZOOM);
        }
        Log.d(TAG, location.toString());
    }

    /**
     * Initialize Location Manager
     */
    @SuppressLint("MissingPermission")
    public void loadLocationTracker() {
        try {
            this.locationManager = (LocationManager) getApplicationContext()
                    .getSystemService(LOCATION_SERVICE);
            this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    TIME_INTERVAL,
                    MIN_DISTANCE_M,
                    NavigatorActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadFacility() {
        String[] tempFacility = new String[] {
                "1 Nicholas StOttawa, ON K1N 7B7",
                "100 Laurier Ave. EOttawa, ON K1N 6N7",
                "100 Marie-Curie PrivateOttawa, ON K1N 9N3",
                "90 University PrivateOttawa, ON K1N 6N5"
        };
        for (String s : tempFacility) {
            LatLng latLng = getLocationFromAddress(s);
            FacilityModel model = new FacilityModel(s, latLng);
            mFacilityList.add(model);
            assert latLng != null;
            Log.d(TAG, latLng.toString());
        }
    }

    private LatLng getLocationFromAddress(String strAddress) {
        Geocoder coder = new Geocoder(this);
        List<Address> address;
        LatLng p1 = null;
        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return p1;
    }

    private String getDirectionUrl(LatLng origin, LatLng dest) {
        return String.format("https://maps.googleapis.com/maps/api/directions/json?origin=%s,%s" +
                "&destination=%s,%s&key=%s",
                origin.latitude, origin.longitude,
                dest.latitude, dest.longitude,
                UtilLoader.getInstance().getGoogleAPIKey());
    }

    // TODO: invoke this function when user click one of the facilities
    private void drawDirection(LatLng origin, LatLng dest) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(getDirectionUrl(origin, dest))
                .method("GET", null)
                .build();
        try {
            Response response = client.newCall(request).execute();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
