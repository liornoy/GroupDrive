package com.example.groupdrive.ui.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.groupdrive.R;
import com.example.groupdrive.api.ApiClient;
import com.example.groupdrive.api.ApiInterface;
import com.example.groupdrive.model.trip.Trip;

import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakerActivity extends AppCompatActivity {
    private Button createBtn;
    private TextView title, description, meetingPoint, wazeUrl, date,maxGroupSize;
    private CheckBox limitCheckBox;
    //private DatePickerFragment datePickerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_maker);
        getSupportActionBar().hide();
        maxGroupSize = findViewById(R.id.size_limit_edit_text);
        limitCheckBox = findViewById(R.id.trip_limit_check_box);
        limitCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Checkbox is checked
                if(((CompoundButton)v).isChecked()){
                    maxGroupSize.setEnabled(true);
                } else { // Checkbox is un-checked
                    maxGroupSize.setEnabled(false);

                }
            }
        });

        DatePickerFragment.dateTextView = findViewById(R.id.dateTextView);
        title = findViewById(R.id.tripTitleText);
        description = findViewById(R.id.tripDescText);
        date = findViewById(R.id.dateTextView);
        meetingPoint = findViewById(R.id.maker_meeting_point);
        wazeUrl = findViewById(R.id.maker_meeting_point_waze);

        createBtn = findViewById(R.id.createBtn);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateTrip(title.getText().toString(), description.getText().toString(),
                        date.getText().toString(), meetingPoint.getText().toString(),
                        wazeUrl.getText().toString());
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

    }

    private void onCreateTrip(String title, String description, String dateTime, String meetingPoint, String meetingPointWazeUrl) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        int groupSizeLimit= -1;
        String tripID = UUID.randomUUID().toString();
        String username = getIntent().getExtras().getString("username");
        if (limitCheckBox.isChecked()) {
            if (maxGroupSize.getText().toString().length() > 0) {
                groupSizeLimit = Integer.parseInt(maxGroupSize.getText().toString());
            } else {
                Toast.makeText(getApplicationContext(), "Create Trip failed. please enter group size limit!", Toast.LENGTH_LONG).show();
                return;
            }
        }
        if (title.equals("")) {
            title = username+"'s trip";
        }
        Trip newTrip = new Trip(tripID, username, title, description, dateTime, meetingPoint, meetingPointWazeUrl,groupSizeLimit,false);

        Call<Trip> call;
        call = apiInterface.postTrip(newTrip);
        call.enqueue(new Callback<Trip>() {
            @Override
            public void onResponse(Call<Trip> call, Response<Trip> response) {
                if (response.isSuccessful()) {
                    goBackToTrips();
                } else {
                    Toast.makeText(getApplicationContext(), "Connection to server failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Trip> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Connection to server failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goBackToTrips() {
        Toast.makeText(this, "Trip Created Successfully!", Toast.LENGTH_SHORT).show();
        Intent switchActivityIntent = new Intent(this, TripsActivity.class);
        switchActivityIntent.putExtra("username", getIntent().getExtras().getString("username"));
        startActivity(switchActivityIntent);
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        public static TextView dateTextView;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(requireContext(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            dateTextView.setText(year + "-" + (month + 1) + "-" + day);
        }
    }

}