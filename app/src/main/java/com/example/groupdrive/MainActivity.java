package com.example.groupdrive;

import static com.example.groupdrive.ui.fragments.MapsActivity.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.groupdrive.api.ApiClient;
import com.example.groupdrive.api.ApiInterface;
import com.example.groupdrive.ui.fragments.TripsActivity;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Button sign_in_button;
    private EditText sign_in_username, sign_in_password;
    private static final int RC_SIGN_IN = 9001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        sign_in_button = findViewById(R.id.signInBtn);
        sign_in_username = findViewById(R.id.oldUsernameText);
        sign_in_password = findViewById(R.id.oldPasswordText);
        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSignIn(view);
            }
        });
    }
    public void onClick(View v) {
        Intent switchActivityIntent = new Intent(this, SignupActivity.class);
        startActivity(switchActivityIntent);
    }
    private void onSignIn(View view){
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        String username = sign_in_username.getText().toString();
        String password = sign_in_password.getText().toString();
        String requestURL = "/api/users/sign-in/"+username+"/"+password;
        Call<String> call;
        call = apiInterface.signIn(requestURL);
        Response<String> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,"call for /api/users/sign-in/ failed");
            return;
        }
        if (response.isSuccessful() && response.body() != null) {
            gotoTripsActivity(username);
        } else {
            Toast.makeText(getApplicationContext(), "Your username or password was incorrect", Toast.LENGTH_SHORT).show();
            System.out.println("Bad Response");
            Log.d(TAG,"call for /api/users/sign-in/ got bag response");
        }
    }

    private void gotoTripsActivity(String username) {
        Intent switchActivityIntent = new Intent(this, TripsActivity.class);
        switchActivityIntent.putExtra("username", username);
        startActivity(switchActivityIntent);
    }
}