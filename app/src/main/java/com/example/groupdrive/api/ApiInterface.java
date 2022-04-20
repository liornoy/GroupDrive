package com.example.groupdrive.api;

import com.example.groupdrive.model.trip.Trip;
import com.example.groupdrive.model.user.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("/api/users")
    Call<User> postUser(@Body User user);

    @POST("/api/trips")
    Call<Trip> postTrip(@Body Trip trip, @Header("google-id") String googleId);

    @GET("/api/trips")
    Call<ArrayList<Trip>> getTrips();
}