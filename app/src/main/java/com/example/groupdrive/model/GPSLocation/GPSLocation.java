package com.example.groupdrive.model.GPSLocation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GPSLocation {
    @SerializedName("longitude")
    @Expose
    double longitude;
    @SerializedName("latitude")
    @Expose
    double latitude;
    @SerializedName("user")
    @Expose
    String user;
    @SerializedName("tripID")
    @Expose
    String tripID;
    @SerializedName("_id")
    @Expose
    String _id;
    public GPSLocation(double longitude, double latitude){
        this.longitude = longitude;
        this.latitude=latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTripID() {
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public GPSLocation(String _id, String user, String tripID, double longitude, double latitude){
        this._id=_id;
        this.user=user;
        this.tripID=tripID;
        this.longitude = longitude;
        this.latitude=latitude;
    }
}
