package com.example.groupdrive.model.trip;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LiveMessage {
    @SerializedName("timeStamp")
    @Expose
    int timeStamp;
    @SerializedName("tripID")
    @Expose
    String tripID;
    @SerializedName("message")
    @Expose
    String message;


    public LiveMessage(int timeStamp, String tripID, String message) {
        this.timeStamp = timeStamp;
        this.tripID = tripID;
        this.message = message;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTripID() {
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
