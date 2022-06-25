
package com.example.groupdrive.ui.fragments;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Location;
import android.location.LocationRequest;
import android.os.Bundle;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.groupdrive.R;
import com.example.groupdrive.api.ApiClient;
import com.example.groupdrive.api.ApiInterface;
import com.example.groupdrive.databinding.ActivityMapsBinding;
import com.example.groupdrive.model.GPSLocation.GPSLocation;
import com.example.groupdrive.model.trip.Trip;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.groupdrive.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private String username, tripID;
    private ArrayList<Marker> markers;
    private Marker userMarker;
    private GoogleMap mMap;
    private Location mLocation;
    private LocationCallback locationCallback;
    private ActivityMapsBinding binding;
    private com.google.android.gms.location.LocationRequest locationRequest;
    private ActivityResultLauncher<String> requestPermissionLauncher;
    //private FusedLocationProviderClient fusedLocationClient;
    private FusedLocationProviderClient fusedLocationClient;
    public static final String TAG = "DEBUG"; //tag for logcat

    private void updateBEUSerLocation(GPSLocation gpsLocation){
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<String> call;
        String url = "/api/trips/"+ tripID+"/update-coordinates";
        call = apiInterface.updateGPS(url,username, gpsLocation);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Failed to update user location", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed to update user location", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private ArrayList<GPSLocation> getTripGPSLocations(){
        Log.d(TAG, "getTripsGPSLocations called...");
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ArrayList<GPSLocation>> call;
        Response<ArrayList<GPSLocation>> response;
        String url = "/api/trips/"+tripID+"/get-coordinates";
        call = apiInterface.getLocations(url);
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("call.execute failed");
            Log.d(TAG, "call.execute failed...");
            Log.d(TAG, "getTripsGPSLocations finished...");
            return null;
        }
        if (response.isSuccessful() && response.body() != null) {
            Log.d(TAG, "getTripsGPSLocations got the following locations from server:");
            for(int i = 0; i < response.body().size(); i++)
                Log.d(TAG, response.body().get(i).getUser() + ", latitude: " + response.body().get(i).getLatitude() + ",longitude: " + response.body().get(i).getLongitude());


            Log.d(TAG, "getTripsGPSLocations finished...");
            return response.body();
        } else {
            Log.d(TAG, "Bad Response...");
            System.out.println("Bad Response");
        }
        Log.d(TAG, "getTripsGPSLocations finished unsuccessfully...");
        return null;
    }

    // Stop the FE from sending API requests when the user exit the live trip window.
    @Override
    public void onPause() {
        super.onPause();
        if (fusedLocationClient != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (fusedLocationClient != null) {
            startLocationUpdates();
        }
    }

    private void updateMarkers(ArrayList<GPSLocation>locations){
        for (GPSLocation l : locations){
            if (l.getUser().equals(username)){
                continue;
            }
            updateUserLocationOnMap(l);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        markers = new ArrayList<>();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        username = getIntent().getExtras().getString("username");
        tripID = getIntent().getExtras().getString("tripID");
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                Toast.makeText(MapsActivity.this, "Location updated by device!", Toast.LENGTH_LONG).show();
                Location location = locationResult.getLastLocation();
                updateUserLocationOnMap(locationResult.getLastLocation());
                //updateUserLocationOnMap(location);
                updateBEUSerLocation(new GPSLocation(location.getLongitude(),location.getLatitude()));
                ArrayList<GPSLocation> locations = getTripGPSLocations();
                if (locations != null){
                updateMarkers(locations);
                }
            }
        };
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Register the permissions callback, which handles the user's response to the
        // system permissions dialog. Save the return value, an instance of
        // ActivityResultLauncher, as an instance variable.
        requestPermissionLauncher =
                registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if (isGranted) {
                        // Permission is granted. Continue the action or workflow in your
                        // app.
                        Toast.makeText(this, "Thanks! i have location permissions now!", Toast.LENGTH_LONG);

                    } else {
                        Toast.makeText(this, "oh no! i don't have location permissions now!", Toast.LENGTH_LONG);
                        // Explain to the user that the feature is unavailable because the
                        // features requires a permission that the user has denied. At the
                        // same time, respect the user's decision. Don't link to system
                        // settings in an effort to convince the user to change their
                        // decision.
                    }
                });
    }

    public void updateUserLocationOnMap(Location location) {
        if (userMarker == null)
        {
            userMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("You").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(userMarker.getPosition()));
        }
        else
        {
            double updatedBearing = bearing(userMarker.getPosition().latitude, userMarker.getPosition().longitude, location.getLatitude(), location.getLongitude());
            userMarker.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                    new LatLng(location.getLatitude(), location.getLongitude()), 20.0f, 0, (float)updatedBearing
            )));
        }
        Log.d(TAG, "Updated user location on map.");
        //need to change maps camera BEARING! to keep aligned with the moving user
    }


    public void updateUserLocationOnMap(GPSLocation location) {
        for (Marker m : markers) {
            if (m.getTitle().equals(location.getUser())) {
                m.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
                return;
            }
            }
        Marker newMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title(location.getUser()));
        markers.add(newMarker);
    }

    public void getLastKnownLocation()
    {
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location)
            {
                if(location != null)
                    mLocation = location;
                else
                    Toast.makeText(MapsActivity.this, "oh no! failed to get your location for some reason!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        checkIfDeviceSupportsGPS();
        requestPermissionLauncher.launch("android.permission.ACCESS_FINE_LOCATION");

        if (isPermissionGranted("android.permission.ACCESS_FINE_LOCATION"))
        {
            Toast.makeText(this, "ACCESS_FINE_LOCATION is granted!", Toast.LENGTH_LONG).show();
            setLocationSettings();
            getLastKnownLocation();
            mMap.moveCamera(CameraUpdateFactory.zoomTo(15.0f));
            startLocationUpdates();
        }
    }

    private void startLocationUpdates()
    {
        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }

    public void setLocationSettings()
    {
        locationRequest = com.google.android.gms.location.LocationRequest.create();
        locationRequest.setInterval(700); //in milliseconds
        locationRequest.setFastestInterval(200);
        locationRequest.setPriority(com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY);

        //optional - get users location settings and if needed prompt him to change them.
    }


    public boolean isPermissionGranted(String permission)
    {
        return ContextCompat.checkSelfPermission(this, permission) == getPackageManager().PERMISSION_GRANTED;
    }

    public void checkIfDeviceSupportsGPS()
    {
        if (!getApplicationContext().getPackageManager().hasSystemFeature(
                getPackageManager().FEATURE_LOCATION)) {
            Toast.makeText(this, "Your device does not support GPS! You cannot use the LiveTrip feature", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this, "Your device supports GPS!", Toast.LENGTH_LONG).show();
        }
    }

    public static double bearing(double lat1, double lon1, double lat2, double lon2)
    {
        double longitude1 = lon1;
        double longitude2 = lon2;
        double latitude1 = Math.toRadians(lat1);
        double latitude2 = Math.toRadians(lat2);
        double longDiff = Math.toRadians(longitude2 - longitude1);
        double y = Math.sin(longDiff) * Math.cos(latitude2);
        double x = Math.cos(latitude1) * Math.sin(latitude2) - Math.sin(latitude1) * Math.cos(latitude2) * Math.cos(longDiff);

        return (Math.toDegrees(Math.atan2(y, x)) + 360) % 360;
    }
}