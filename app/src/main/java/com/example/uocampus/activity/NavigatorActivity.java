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
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.NavigatorRecordModel;
import com.amplifyframework.datastore.generated.model.TestModel;
import com.example.uocampus.R;
import com.example.uocampus.adapter.ButtonCallback;
import com.example.uocampus.adapter.FacilityAdapter;
import com.example.uocampus.dao.NavigationDao;
import com.example.uocampus.dao.impl.NavigationDaoImpl;
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
import java.util.concurrent.ExecutionException;

public class NavigatorActivity extends AppCompatActivity implements LocationListener,
                                                                    OnMapReadyCallback,
                                                                    ButtonCallback,
                                                                    TaskLoadedCallback {
    private static final String TAG = NavigatorActivity.class.getSimpleName();
    private final int TIME_INTERVAL = 5000;
    private final int MIN_DISTANCE_M = 0;
    private static final int DEFAULT_ZOOM = 15;

    private GoogleMap mMap;
    private LocationManager locationManager;
    private Location userLocation;
    private Map<FacilityModel,Marker> facilityModelMarkerMap;
    private Polyline currentPolyline;
    private FacilityModel onTrackingFacility;
    List<FacilityModel> mFacilityList = new ArrayList<>();

    NavigationDao navigationDao = new NavigationDaoImpl(NavigatorActivity.this);

    ListView listView;
    FacilityAdapter facilityAdapter;

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
                loadFacility();
                facilityModelMarkerMap = new HashMap<>();

                listView = findViewById(R.id.lv_facility);
                facilityAdapter = new FacilityAdapter(this,
                        R.layout.fragment_facility_list_row,
                        (ArrayList<FacilityModel>) mFacilityList);
                listView.setAdapter(facilityAdapter);
            }
            else {
                Toast.makeText(this, "Not Granted", Toast.LENGTH_SHORT).show();
            }
        });
        configAmplify();
        multiplePermissionLauncher.launch(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                                        Manifest.permission.INTERNET});
    }

    private void configAmplify() {
        try {
            Amplify.addPlugin(new AWSApiPlugin()); // UNCOMMENT this line once backend is deployed
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.configure(getApplicationContext());
            Log.i("Amplify", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("Amplify", "Could not initialize Amplify", error);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        // default location: University of Ottawa
        LatLng defaultLocation = getLocationFromAddress("75 Laurier Ave. E, Ottawa, ON K1N 6N5");
        mMap.moveCamera(CameraUpdateFactory.newLatLng(defaultLocation));
        mMap.setMinZoomPreference(DEFAULT_ZOOM);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        userLocation = location;
        if(mMap != null) {
            mMap.setMinZoomPreference(DEFAULT_ZOOM);
            // TODO: request all facility information again
            if (onTrackingFacility != null) {
                if(currentPolyline != null)
                    currentPolyline.remove();
                updateDirectionInfo(onTrackingFacility, true);
            }
            for(FacilityModel facility : mFacilityList) {
                updateDirectionInfo(facility, false);
            }
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
        this.mFacilityList = navigationDao.queryAll();

        if(this.mFacilityList.isEmpty()) {
            String[] tempFacility = getResources().getStringArray(R.array.facility_name);

            for (String s : tempFacility) {
                LatLng latLng = getLocationFromAddress(s);
                FacilityModel model = new FacilityModel(s, latLng);
                navigationDao.addFacilityModel(model);
                mFacilityList.add(model);
                if (latLng != null) {
                    Log.d(TAG, latLng.toString());
                }
            }
        } else {
            for(FacilityModel model : mFacilityList) {
                Log.d(TAG, model.toString());
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
        if(object instanceof FacilityModel) {
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
                this.onTrackingFacility = null;
            }
        } else {
            addMarker(facilityModel);
        }
    }

    @Override
    public void onDirectionClick(int position, boolean drawPolyline) {
        FacilityModel facilityModel = mFacilityList.get(position);
        this.onTrackingFacility = facilityModel;
        if(facilityModelMarkerMap.get(facilityModel) == null) {
            addMarker(facilityModel);
        }

        updateDirectionInfo(facilityModel, true);
    }

    public void updateDirectionInfo(FacilityModel facility, boolean drawPolyline) {
        FetchURL worker = new FetchURL(NavigatorActivity.this,
                new LatLng(userLocation.getLatitude(), userLocation.getLongitude()),
                facility.getLatLng(),
                "driving", drawPolyline);
        try {
            String result = worker.execute().get();
            int position = mFacilityList.indexOf(facility);
            if(!result.isEmpty()) {
                String[] rsl = result.split(":");
                String duration = rsl[0];
                String distance = rsl[1];
                Log.d(TAG, String.format("[%s]-----------[%s]", duration, distance));

                FacilityAdapter adapter = (FacilityAdapter) listView.getAdapter();
                View view = adapter.getView(position,null, listView);
                FacilityModel facilityModel = mFacilityList.get(position);
                facilityModel.setEstimateTime(duration);
                facilityModel.setDirectDistance(distance);
                mFacilityList.set(position, facilityModel);
                adapter.notifyDataSetChanged();
                if(drawPolyline) {
                    create(this.userLocation, facilityModel);
                }
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void create(Location userLocation, FacilityModel facilityModel) {
        NavigatorRecordModel item = NavigatorRecordModel.builder()
                .origin(new LatLng(userLocation.getLatitude(), userLocation.getLongitude()).toString())
                .dest(facilityModel.getLatLng().toString())
                .estimateTime(facilityModel.getEstimateTime())
                .estimateDistance(facilityModel.getDirectDistance())
                .build();
        Amplify.DataStore.save(
                item,
                success -> Log.i("Amplify", "Saved item: " + success.item().getId()),
                error -> Log.e("Amplify", "Could not save item to DataStore", error)
        );
    }
}
