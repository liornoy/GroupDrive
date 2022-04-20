package com.example.groupdrive.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.groupdrive.R;
import com.example.groupdrive.api.ApiClient;
import com.example.groupdrive.api.ApiInterface;
import com.example.groupdrive.model.trip.Trip;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakerActivity extends AppCompatActivity {
    private Button createBtn;
    private TextView title, description, meetingPoint, wazeUrl, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_maker);
        getSupportActionBar().hide();
        title = findViewById(R.id.tripTitleText);
        description = findViewById(R.id.tripDescText);
        date = findViewById(R.id.tripdate);
        meetingPoint = findViewById(R.id.meetingPointText);
        wazeUrl = findViewById(R.id.wazeUrlText);
        createBtn = findViewById(R.id.createBtn);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateTrip(title.getText().toString(), description.getText().toString(),
                        date.getText().toString(), meetingPoint.getText().toString(),
                        wazeUrl.getText().toString());
            }
        });
    }

    private void onCreateTrip(String title, String description, String dateTime, String meetingPoint, String meetingPointWazeUrl) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        String tripID = UUID.randomUUID().toString();
        String creatorID = getIntent().getExtras().getString("googleId");
        Trip newTrip = new Trip(tripID, creatorID, title, description, dateTime, meetingPoint, meetingPointWazeUrl);

        Call<Trip> call;
        call = apiInterface.postTrip(newTrip, creatorID);
        call.enqueue(new Callback<Trip>() {
            @Override
            public void onResponse(Call<Trip> call, Response<Trip> response) {
                if (response.isSuccessful()) {
                    goBackToTrips();
                } else {
                    // TODO Null response
                }
            }

            @Override
            public void onFailure(Call<Trip> call, Throwable t) {
                // TODO Failure on API Call
                System.out.println("onCreateTrip API call failed!");
                System.out.println(t.toString());
            }
        });
    }

    private void goBackToTrips() {
        Toast.makeText(this, "Trip Created Successfully!", Toast.LENGTH_SHORT).show();
        Intent switchActivityIntent = new Intent(this, TripsActivity.class);
        switchActivityIntent.putExtra("googleId", getIntent().getExtras().getString("googleId"));
        startActivity(switchActivityIntent);
    }
}