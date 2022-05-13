package com.example.groupdrive.api;

import com.example.groupdrive.model.trip.Trip;
import com.example.groupdrive.model.user.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ApiInterface {
    @POST
    Call<String> signIn(@Url String url);

    @POST
    Call<String> signUp(@Url String url);

    @POST("/api/trips")
    Call<Trip> postTrip(@Body Trip trip);

    @GET("/api/trips")
    Call<ArrayList<Trip>> getTrips();

    @GET
    Call<User> getUser(@Url String url);
}