package com.example.groupdrive.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groupdrive.R;
import com.example.groupdrive.api.ApiClient;
import com.example.groupdrive.api.ApiInterface;
import com.example.groupdrive.model.trip.Trip;
import com.example.groupdrive.ui.adapters.recyclerAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripsActivity extends AppCompatActivity {
    private ArrayList<Trip> trips;
    private RecyclerView recyclerView;
    private Button joinTripBtn;
    private Button join2TripBtn;
    private Button createNewTripBtn;
    private ProgressBar progressBar;
    private TextView noTripsTextView;

    public void addNewTrip(Trip trip) {
        trips.add(trip);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.pending_trips);

        noTripsTextView = findViewById(R.id.textView2);
        noTripsTextView.setVisibility(View.INVISIBLE);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.VISIBLE);
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
                // which view you pass in doesn                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         't matter, it is only used for the window tolken
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        trips = new ArrayList<>();
        setTripInfo();
    }
    private void gotoCreateNewTripActivity() {
        Intent switchActivityIntent = new Intent(this, MakerActivity.class);
        switchActivityIntent.putExtra("googleId", getIntent().getExtras().getString("googleId"));
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
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ArrayList<Trip>> call;
        call = apiInterface.getTrips();
        call.enqueue(new Callback<ArrayList<Trip>>() {
            @Override
            public void onResponse(Call<ArrayList<Trip>> call, Response<ArrayList<Trip>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    trips = response.body();
                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                    if (trips.size() == 0) {
                        noTripsTextView.setVisibility(View.VISIBLE);
                    }
                    setAdapter();
                } else {
                    Toast.makeText(getApplicationContext(), "Connection to server failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Trip>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Connection to server failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}