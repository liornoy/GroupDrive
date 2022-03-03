package com.example.groupdrive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.groupdrive.ui.fragments.TripsActivity;

public class MainActivity extends AppCompatActivity {
private Button enterBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        enterBtn = findViewById(R.id.enterBtn);
        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoTripsActivity();
            }
        });
    }
    private void gotoTripsActivity() {
        Intent switchActivityIntent = new Intent(this, TripsActivity.class);
        startActivity(switchActivityIntent);
    }
}