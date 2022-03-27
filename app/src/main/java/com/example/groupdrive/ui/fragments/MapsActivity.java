package com.example.groupdrive.ui.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.groupdrive.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback
{


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getSupportActionBar().hide();

        // Get the SupportMapFragment and request notification when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        //the object that will be called when the map is ready. doesn't have to be this.
        //for now just for simplicity purposes
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        //coordinates are of the academic college, tel aviv
        LatLng MTA = new LatLng(32.047565, 34.760741);
        googleMap.addMarker(new MarkerOptions()
                .position(MTA)
                .title("Marker in MTA"));
    }

}