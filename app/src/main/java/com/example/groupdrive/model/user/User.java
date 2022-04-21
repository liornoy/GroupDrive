package com.example.groupdrive.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    public String getGoogleID() {
        return googleID;
    }

    public void setGoogleID(String googleID) {
        this.googleID = googleID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    @SerializedName("googleID")
    @Expose
    String googleID;
    @SerializedName("name")
    @Expose
    String name;
    @SerializedName("photoURL")
    @Expose
    String photoURL;

    public User(String googleID, String name, String photoURL) {
        this.googleID = googleID;
        this.name = name;
        this.photoURL = photoURL;
    }
}
