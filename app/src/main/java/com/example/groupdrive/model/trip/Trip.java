package com.example.groupdrive.model.trip;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class Trip {
    public int getTripID() {
        return tripID;
    }

    public String getCreatorGID() {
        return creatorGID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getMeetingPoint() {
        return meetingPoint;
    }

    public String getMeetingPointWazeUrl() {
        return meetingPointWazeUrl;
    }

    public String getDateTime() {
        return dateTime;
    }

    public boolean isTripToday() {
        return isTripToday;
    }

    public List<String> getParticipants() {
        return participants;
    }

    @SerializedName("tripID")
    @Expose
    private int tripID;
    @SerializedName("creatorGID")
    @Expose
    private String creatorGID;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("meetingPoint")
    @Expose
    private String meetingPoint;
    @SerializedName("meetingPointWazeUrl")
    @Expose
    private String meetingPointWazeUrl;
    @SerializedName("dateTime")
    @Expose
    private String dateTime;
    @SerializedName("isTripToday")
    @Expose
    private boolean isTripToday;
    @SerializedName("participants")
    @Expose
    private List<String> participants;

    public void setTripID(int tripID) {
        this.tripID = tripID;
    }

    public void setCreatorGID(String creatorGID) {
        this.creatorGID = creatorGID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMeetingPoint(String meetingPoint) {
        this.meetingPoint = meetingPoint;
    }

    public void setMeetingPointWazeUrl(String meetingPointWazeUrl) {
        this.meetingPointWazeUrl = meetingPointWazeUrl;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setTripToday(boolean tripToday) {
        isTripToday = tripToday;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public Trip(int tripID, String creatorGID, String title, String meetingPoint, String meetingPointWazeUrl, String dateTime, String description) {
        this.tripID = tripID;
        this.creatorGID = creatorGID;
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
        this.meetingPoint = meetingPoint;
        this.meetingPointWazeUrl = meetingPointWazeUrl;
        this.participants = new ArrayList<>();
        this.participants.add(creatorGID);
    }
}


