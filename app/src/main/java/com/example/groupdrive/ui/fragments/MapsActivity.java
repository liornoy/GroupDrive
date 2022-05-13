
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
import android.widget.Toast;

import com.example.groupdrive.R;
import com.example.groupdrive.databinding.ActivityMapsBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.groupdrive.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnSuccessListener;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Marker marker;
    private GoogleMap mMap;
    private Location mLocation;
    private LocationCallback locationCallback;
    private ActivityMapsBinding binding;
    private com.google.android.gms.location.LocationRequest locationRequest;
    private ActivityResultLauncher<String> requestPermissionLauncher;
    //private FusedLocationProviderClient fusedLocationClient;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        marker = null;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                Toast.makeText(MapsActivity.this, "Location updated by device! ", Toast.LENGTH_LONG).show();
                updateUserLocationOnMap(locationResult.getLastLocation());
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
        if (marker == null)
        {
            marker = mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("You"));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
        }
        else
        {
            double updatedBearing = bearing(marker.getPosition().latitude, marker.getPosition().latitude, location.getLatitude(), location.getLongitude());
            marker.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                    new LatLng(location.getLatitude(), location.getLongitude()), 20.0f, 0, (float)updatedBearing
            )));
        }
        //need to change maps camera BEARING! to keep aligned with the moving user

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
        locationRequest.setInterval(1000); //in milliseconds
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