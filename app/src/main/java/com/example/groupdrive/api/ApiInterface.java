package com.example.groupdrive.api;

import com.example.groupdrive.model.GPSLocation.GPSLocation;
import com.example.groupdrive.model.trip.Trip;
import com.example.groupdrive.model.trip.LiveMessage;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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

    @POST
    Call<String> joinTrip(@Url String url, @Header("username") String username);

    @DELETE
    Call<String> deleteTrip(@Url String url, @Header("username") String username);

    @GET("/api/trips")
    Call<ArrayList<Trip>> getTrips(@Header("creator") String creator);

    @GET
    Call<LiveMessage> getLastLiveMessage(@Url String url);

    @POST
    Call<String> sendLiveMessage(@Url String url, @Body LiveMessage LiveMessage);

    @GET
    Call<ArrayList<GPSLocation>> getLocations(@Url String url);

    @POST
    Call<String> updateGPS(@Url String url, @Header("username") String username, @Body GPSLocation gpsLocation);
}