package com.example.groupdrive;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.groupdrive.api.ApiClient;
import com.example.groupdrive.api.ApiInterface;
import com.example.groupdrive.model.trip.Trip;
import com.example.groupdrive.model.user.User;
import com.example.groupdrive.ui.fragments.TripsActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Button sign_in_button, sign_up_button;
    private EditText sign_in_username, sign_in_password,
                    sign_up_username, sign_up_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        sign_in_button = findViewById(R.id.signInBtn);
        sign_up_button = findViewById(R.id.signUpBtn);
        sign_in_username = findViewById(R.id.oldUsernameText);
        sign_up_username = findViewById(R.id.newUsernameText);
        sign_in_password = findViewById(R.id.oldPasswordText);
        sign_up_password = findViewById(R.id.newPasswordText);

        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSignIn();
            }
        });
        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSignUp();
            }
        });
    }
    private void onSignUp(){
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        String username = sign_up_username.getText().toString();
        String password = sign_up_password.getText().toString();
        String requestURL = "/api/users/sign-up/"+username+"/"+password;
        Call<String> call;
        call = apiInterface.signIn(requestURL);
        Response<String> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("call.execute failed");
            return;
        }
        if (response.isSuccessful() && response.body() != null) {
            gotoTripsActivity(username);
        } else {
            System.out.println("Bad Response");
        }
    }

    private void onSignIn(){
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
            System.out.println("call.execute failed");
            return;
        }
        if (response.isSuccessful() && response.body() != null) {
            gotoTripsActivity(username);
        } else {
            System.out.println("Bad Response");
        }
    }

    private void gotoTripsActivity(String username) {
        Intent switchActivityIntent = new Intent(this, TripsActivity.class);
        switchActivityIntent.putExtra("username", username);
        startActivity(switchActivityIntent);
    }
}