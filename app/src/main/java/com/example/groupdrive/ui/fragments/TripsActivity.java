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
    private Button createNewTripBtn;
    private ProgressBar progressBar;
    private TextView noTripsTextView;
    private String creator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.pending_trips);
        creator = getIntent().getExtras().getString("creator");
        if (creator != null){
            TextView title = findViewById(R.id.trips_title);
            title.setText(creator+"'s Trips");
        } else{
            TextView title = findViewById(R.id.trips_title);
            title.setText("Pending Trips");
        }
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
        recyclerView = findViewById(R.id.recyclerView);
        trips = new ArrayList<>();
        setTripInfo();
    }
    private void gotoCreateNewTripActivity() {
        Intent switchActivityIntent = new Intent(this, MakerActivity.class);
        switchActivityIntent.putExtra("username", getIntent().getExtras().getString("username"));
        startActivity(switchActivityIntent);
    }
    private void setAdapter() {
        recyclerAdapter adapter = new recyclerAdapter(trips, getIntent().getExtras().getString("username"),getIntent().getExtras().getString("creator"));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setTripInfo() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ArrayList<Trip>> call;
        call = apiInterface.getTrips(creator);
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