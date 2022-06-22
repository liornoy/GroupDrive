package com.example.groupdrive.ui.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.groupdrive.R;

public class ViewTripDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trip_details);
        TextView title_tv = findViewById(R.id.details_trip_title);
        TextView description_tv = findViewById(R.id.details_trip_desc);
        TextView date_tv = findViewById(R.id.details_trip_date);
        TextView creator_tv = findViewById(R.id.details_trip_creator);
        TextView location_tv = findViewById(R.id.details_meeting_point);
        title_tv.setText(getIntent().getExtras().getString("tripTitle"));
        description_tv.setText(getIntent().getExtras().getString("tripDesc"));
        date_tv.setText(getIntent().getExtras().getString("tripDate"));
        location_tv.setText(getIntent().getExtras().getString("tripLocation"));
        creator_tv.setText(getIntent().getExtras().getString("tripCreator"));
        Button creator_page_btn = findViewById(R.id.creator_page_btn);
        creator_page_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchActivityIntent = new Intent(v.getContext(), TripsActivity.class);
                switchActivityIntent.putExtra("creator", creator_tv.getText());
                switchActivityIntent.putExtra("username", getIntent().getExtras().getString("username"));
                v.getContext().startActivity(switchActivityIntent);
            }
        });
    }
}