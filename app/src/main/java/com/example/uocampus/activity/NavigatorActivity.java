package com.example.uocampus.activity;

import android.Manifest;
import android.annotation.SuppressLint;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

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

        // Google Map Initialization
        UtilLoader.getInstance().requestPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION);
        loadLocationTracker();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this::onMapReady);

        loadFacility();
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
        for(int i = 0; i < 5; i++) {
            FacilityModel model = new FacilityModel("Facility " + i);
            mFacilityList.add(model);
        }
    }
}
