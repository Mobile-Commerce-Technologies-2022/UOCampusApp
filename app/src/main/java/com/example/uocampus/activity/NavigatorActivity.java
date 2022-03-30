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

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uocampus.R;
import com.example.uocampus.adapter.FacilityViewAdapter;
import com.example.uocampus.adapter.OnDirectionListener;
import com.example.uocampus.adapter.OnLocationListener;
import com.example.uocampus.model.FacilityModel;
import com.example.uocampus.utils.FetchURL;
import com.example.uocampus.utils.TaskLoadedCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class NavigatorActivity extends AppCompatActivity implements LocationListener,
                                                                    OnMapReadyCallback,
                                                                    OnLocationListener,
                                                                    OnDirectionListener,
                                                                    TaskLoadedCallback {
    private static final String TAG = NavigatorActivity.class.getSimpleName();
    private final int TIME_INTERVAL = 2500;
    private final int MIN_DISTANCE_M = 0;
    private static final int DEFAULT_ZOOM = 15;

    private GoogleMap mMap;
    private LocationManager locationManager;
    private Location userLocation;
    private Marker userLocationMarker;
    private Map<FacilityModel,Marker> facilityModelMarkerMap;
    private Polyline currentPolyline;

    RecyclerView mRecyclerView;
    FacilityViewAdapter facilityViewAdapter;
    List<FacilityModel> mFacilityList = new ArrayList<>();
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitiy_navigator);
        // Google Map Initialization
        ActivityResultLauncher<String[]> multiplePermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), (Map<String, Boolean> isGranted) -> {
            boolean granted = true;
            for (Map.Entry<String, Boolean> x : isGranted.entrySet())
                if (!x.getValue()) granted = false;
            if (granted) {
                loadLocationTracker();
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                assert mapFragment != null;
                mapFragment.getMapAsync(this::onMapReady);

                // Facility Initialization
                facilityModelMarkerMap = new HashMap<>();
                facilityViewAdapter = new FacilityViewAdapter(NavigatorActivity.this,
                        mFacilityList,
                        this::onLocationClick,
                        this::onDirectionClick);
                mRecyclerView = findViewById(R.id.rv_facility);
                mRecyclerView.setAdapter(facilityViewAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NavigatorActivity.this);
                mRecyclerView.setLayoutManager(linearLayoutManager);
                loadFacility();
            }
            else {
                Toast.makeText(this, "Not Granted", Toast.LENGTH_SHORT).show();
            }
        });

        multiplePermissionLauncher.launch(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                                        Manifest.permission.INTERNET});
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
            if (latLng != null) {
                Log.d(TAG, latLng.toString());
            }
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
            p1 = new LatLng(location.getLatitude(), location.getLongitude());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return p1;
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
    public void onTaskDone(Object... values) {
        if (currentPolyline != null) {
            currentPolyline.remove();
        }
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }

    @Override
    public void onLocationClick(int position) {
        FacilityModel facilityModel = mFacilityList.get(position);
        if(facilityModelMarkerMap.get(facilityModel) != null) {
            removeMarker(facilityModel);
            if (currentPolyline != null) {
                currentPolyline.remove();
            }
        } else {
            addMarker(facilityModel);
        }
    }

    @Override
    public void onDirectionClick(int position) {
        FacilityModel facilityModel = mFacilityList.get(position);
        if(facilityModelMarkerMap.get(facilityModel) == null) {
            addMarker(facilityModel);
        }
//        RouteParser routeParser = new RouteParser(this, userLocationMarker.getPosition(),
//                                                    facilityModel.getLatLng(),
//                                                    mMap);
//            RecyclerView.ViewHolder viewHolder = mRecyclerView.findViewHolderForAdapterPosition(position);
//            TextView tvDistance = viewHolder.itemView.findViewById(R.id.tv_direct_distance);
//            TextView tvTime = viewHolder.itemView.findViewById(R.id.tv_estimate_time);
//
//            tvDistance.setText(routeParser.getDistance());
//            tvTime.setText(routeParser.getDuration());

        FetchURL worker = (FetchURL) new FetchURL(NavigatorActivity.this,
                                                    userLocationMarker.getPosition(),
                                                    facilityModel.getLatLng(),
                                                    "driving");
        worker.execute();

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
