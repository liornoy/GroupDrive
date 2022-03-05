package com.example.groupdrive.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groupdrive.R;
import com.example.groupdrive.model.trip.Trip;
import com.example.groupdrive.ui.adapters.recyclerAdapter;

import java.util.ArrayList;

public class TripsActivity extends AppCompatActivity {
    private ArrayList<Trip> trips;
    private RecyclerView recyclerView;
    private Button joinTripBtn;
    private Button join2TripBtn;
    private Button createNewTripBtn;
    public void addNewTrip(Trip trip){
        trips.add(trip);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.pending_trips);
        createNewTripBtn = findViewById(R.id.createNewTripBtn);
        createNewTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoCreateNewTripActivity();
            }
        });
        joinTripBtn = findViewById(R.id.JoinTripBtn);
        join2TripBtn = findViewById(R.id.joinBtn);
        joinTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.join_trip, null);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        trips = new ArrayList<>();
        setTripInfo();
        setAdapter();
    }
    private void gotoCreateNewTripActivity() {
        Intent switchActivityIntent = new Intent(this, MakerActivity.class);
        startActivity(switchActivityIntent);
    }
    private void setAdapter() {
        recyclerAdapter adapter = new recyclerAdapter(trips);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setTripInfo() {
        trips.add(new Trip("Jerusalem and Bethlehem: Full-Day Trip from Tel Aviv", "Full Day", "11/6/22", "Lorem ipsum dolor sit amet, id oratio definiebas vituperata vim, an brute atomorum nam. Per at quem clita reprehendunt. No meis vivendum vel, no duis mazim nostrud his. Nulla comprehensam sea eu, dolor legimus dissentiet qui eu, ei mei probo ignota vocent.", 3, "9:30"));
        trips.add(new Trip("Caesarea, Haifa & Akko Day Trip from Tel Aviv", "Full Day", "29/5/22", "Lorem ipsum dolor sit amet, id oratio definiebas vituperata vim, an brute atomorum nam. Per at quem clita reprehendunt. No meis vivendum vel, no duis mazim nostrud his. Nulla comprehensam sea eu, dolor legimus dissentiet qui eu, ei mei probo ignota vocent.", 2, "10:00"));
        trips.add(new Trip("Masada, Ein Gedi and Dead Sea Guided Tour", "Full Day", "14/4/22", "Lorem ipsum dolor sit amet, id oratio definiebas vituperata vim, an brute atomorum nam. Per at quem clita reprehendunt. No meis vivendum vel, no duis mazim nostrud his. Nulla comprehensam sea eu, dolor legimus dissentiet qui eu, ei mei probo ignota vocent.", 3, "8:30"));
        trips.add(new Trip("All the best of Tel Aviv walking tour", "~5 Hours", "10/4/22", "Lorem ipsum dolor sit amet, id oratio definiebas vituperata vim, an brute atomorum nam. Per at quem clita reprehendunt. No meis vivendum vel, no duis mazim nostrud his. Nulla comprehensam sea eu, dolor legimus dissentiet qui eu, ei mei probo ignota vocent.", 2, "11:00"));
    }
}