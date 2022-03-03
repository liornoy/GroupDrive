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
import com.example.groupdrive.ui.viewmodels.TripViewModel;

public class TripsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Button joinNewTripBtn;
    private Button createNewTripBtn;
    private TripViewModel tripViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        createNewTripBtn = findViewById(R.id.createNewTripBtn);
        createNewTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoCreateNewTripActivity();
            }
        });
        tripViewModel = new TripViewModel(this.getApplication());
        joinNewTripBtn = findViewById(R.id.JoinTripBtn);
        joinNewTripBtn.setOnClickListener(new View.OnClickListener() {
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
        setTripInfo();
        setAdapter();
    }
    private void gotoCreateNewTripActivity() {
        Intent switchActivityIntent = new Intent(this, MakerActivity.class);
        startActivity(switchActivityIntent);
    }
    private void setAdapter() {
        recyclerAdapter adapter = new recyclerAdapter(tripViewModel.getAllTrips());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setTripInfo() {
        tripViewModel.insert(new Trip("Jerusalem and Bethlehem: Full-Day Trip from Tel Aviv", "FULL DAY"));
        tripViewModel.insert(new Trip("Caesarea, Haifa & Akko Day Trip from Tel Aviv", "FULL DAY"));
        tripViewModel.insert(new Trip("Masada, Ein Gedi and Dead Sea Guided Tour", "HALF DAY"));
        tripViewModel.insert(new Trip("All the best of Tel Aviv walking tour", "~5 HOURS"));
    }
}