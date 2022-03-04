package com.example.groupdrive.ui.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.groupdrive.R;

public class MakerActivity extends AppCompatActivity {
    private Button createBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_maker);
        getSupportActionBar().hide();
        createBtn = findViewById(R.id.createBtn);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gobackToTrips();

                //TODO: Adding new trip logic
            }
    });
    }
    private void gobackToTrips() {
        Toast.makeText(this, "Trip Created Successfully!", Toast.LENGTH_SHORT).show();
        Intent switchActivityIntent = new Intent(this, TripsActivity.class);
        startActivity(switchActivityIntent);
    }}