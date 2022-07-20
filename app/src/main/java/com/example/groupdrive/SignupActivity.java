package com.example.groupdrive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.groupdrive.api.ApiClient;
import com.example.groupdrive.api.ApiInterface;
import com.example.groupdrive.ui.fragments.TripsActivity;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    private Button sign_up_button;
    private EditText sign_up_username, sign_up_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        sign_up_button = findViewById(R.id.signup_btn);
        sign_up_username = findViewById(R.id.signup_username);
        sign_up_password = findViewById(R.id.signup_password);
        sign_up_button.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  onSignUp();
              }
          }
        );
    }

    public void onClick(View v) {
        Intent switchActivityIntent = new Intent(this, MainActivity.class);
        startActivity(switchActivityIntent);
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
            Toast.makeText(getApplicationContext(), "Your account has been created!", Toast.LENGTH_SHORT).show();
            gotoTripsActivity(username);
        } else {
            Toast.makeText(getApplicationContext(), "The username is already exist!", Toast.LENGTH_SHORT).show();
        }
    }

    private void gotoTripsActivity(String username) {
        Intent switchActivityIntent = new Intent(this, TripsActivity.class);
        switchActivityIntent.putExtra("username", username);
        startActivity(switchActivityIntent);
    }
}