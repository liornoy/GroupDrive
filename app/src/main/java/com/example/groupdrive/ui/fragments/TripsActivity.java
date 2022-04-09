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

import java.sql.Date;
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
        //TODO CALL GET TRIPS RETROFIT - INSERT TO TRIPS LIST
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
        trips.add(new Trip(1, "1", "New York Families Bus Trip",
                "Times Square", "https://www.waze.com/ul?ll=40.75889500%2C-73.98513100&navigate=yes&zoom=17",
                Date.valueOf("2022-04-09"), "This is a trip all over New York"));
    }
}