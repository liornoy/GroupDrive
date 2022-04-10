package com.example.groupdrive.api;

import com.example.groupdrive.model.user.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("/api/users")
    Call<User> postUser(@Body User user);
}