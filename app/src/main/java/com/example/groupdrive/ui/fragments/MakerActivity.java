package com.example.groupdrive.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.groupdrive.R;
import com.example.groupdrive.model.trip.Trip;
import com.example.groupdrive.ui.viewmodels.TripViewModel;

public class MakerActivity extends AppCompatActivity {
    private Button createBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_maker);
        TripViewModel tripViewModel = new TripViewModel(this.getApplication());
        createBtn = findViewById(R.id.createBtn);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTrip(tripViewModel);
                gobackToTrips();
                //TODO: Adding new trip logic
            }
        });
    }

    private void addTrip(TripViewModel tripViewModel) {
        TextView name = findViewById(R.id.editTextTextPersonName3);
        // TextView desc = findViewById(R.id.editTextTextMultiLine);
        // TextView date = findViewById(R.id.tripdate);
        TextView duration = findViewById(R.id.editTextTextPersonName5);
        Trip newTrip = new Trip(name.toString(), duration.toString());
        tripViewModel.insert(newTrip);
    }

    private void gobackToTrips() {
        Intent switchActivityIntent = new Intent(this, TripsActivity.class);
        startActivity(switchActivityIntent);
    }
}