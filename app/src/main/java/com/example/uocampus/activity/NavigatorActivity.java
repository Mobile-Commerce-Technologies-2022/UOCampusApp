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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NavigatorActivity extends AppCompatActivity implements LocationListener,
                                                                    OnMapReadyCallback,
                                                                    FacilityViewAdapter.OnFacilityListener{
    private static final String TAG = NavigatorActivity.class.getSimpleName();
    private GoogleMap mMap;
    protected LocationManager locationManager;
    private final int TIME_INTERVAL = 2500;
    private final int MIN_DISTANCE_M = 0;
    private static final int DEFAULT_ZOOM = 15;
    private Location userLocation;
    private Marker userLocationMarker;
    private Map<FacilityModel,Marker> facilityModelMarkerMap;

    RecyclerView mRecyclerView;
    FacilityViewAdapter facilityViewAdapter;
    List<FacilityModel> mFacilityList = new ArrayList<>();

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitiy_navigator);
        facilityModelMarkerMap = new HashMap<>();
        // Facility Initialization
        facilityViewAdapter = new FacilityViewAdapter(NavigatorActivity.this,
                                                             mFacilityList,
                                                             this::onFacilityClick);
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
        // default location: University of Ottawa
        LatLng defaultLocation = new LatLng(45.424721,  -75.695000);
        addMarker(defaultLocation);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(defaultLocation));
        Log.d(TAG, "onMapReady");
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        userLocation = location;
        if(mMap != null) {
            removeMarker(userLocationMarker);
            addMarker(userLocation);
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

    @Nullable
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

    @NonNull
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
            Toast.makeText(this, "It Works", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add a marker to mMap.
     * If object is an instance of LatLng/Location, assigns location of the user.
     * If object is an instance of FacilityModel, stores location of the facility
     * @param object an instance of LatLng/ Location/ FacilityModel
     */
    private void addMarker(Object object) {
        if(object instanceof LatLng) {
            userLocationMarker = mMap.addMarker(new MarkerOptions()
                    .position((LatLng) object)
                    .title("Your Location"));
        } else if(object instanceof Location) {
            userLocationMarker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(((Location) object).getLatitude(),
                                        ((Location) object).getLongitude()))
                    .title("Your Location"));
        } else if(object instanceof FacilityModel) {
            Marker m = mMap.addMarker(new MarkerOptions()
                    .position(((FacilityModel) object).getLatLng())
                    .title(((FacilityModel) object).getNAME()));
            facilityModelMarkerMap.put((FacilityModel) object, m);
        } else {
            throw new IllegalArgumentException("Incorrect Object Type: " +
                    object.getClass().getSimpleName());
        }
    }

    /**
     * Remove a marker to mMap.
     * If object is an instance of Marker, removes location of the user.
     * If object is an instance of FacilityModel, removes location of the facility
     * @param object an instance of Marker/ FacilityModel
     */
    private void removeMarker(Object object) {
        if(object instanceof Marker) {
            ((Marker) object).remove();
        } else if(object instanceof FacilityModel){
            Objects.requireNonNull(facilityModelMarkerMap.get(object)).remove();
            facilityModelMarkerMap.remove(object);
        } else {
            throw new IllegalArgumentException("Incorrect Object Type: " +
                    object.getClass().getSimpleName());
        }
    }


    @Override
    public void onFacilityClick(int position) {
//        drawDirection(userLocationMarker.getPosition(), mFacilityList.get(position).getLatLng());
        FacilityModel facilityModel = mFacilityList.get(position);
        if(facilityModelMarkerMap.get(facilityModel) != null) {
            removeMarker(facilityModel);
        } else {
            addMarker(facilityModel);
        }
    }
}
