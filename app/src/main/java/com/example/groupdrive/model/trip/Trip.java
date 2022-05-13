package com.example.groupdrive.model.trip;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class Trip {
    public String getTripID() {
        return tripID;
    }

    public String getCreator() {
        return creator;
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

    public String getDate() {
        return date;
    }

    public boolean isTripToday() {
        return isTripToday;
    }

    public List<String> getParticipants() {
        return participants;
    }

    @SerializedName("tripID")
    @Expose
    private String tripID;
    @SerializedName("creator")
    @Expose
    private String creator;
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
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("isTripToday")
    @Expose
    private boolean isTripToday;
    @SerializedName("participants")
    @Expose
    private List<String> participants;

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }

    public void setCreator(String creator) {
        this.creator = creator;
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

    public void setDateTime(String date) {
        this.date = date;
    }

    public void setTripToday(boolean tripToday) {
        isTripToday = tripToday;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public Trip(String tripID, String creator, String title, String description, String date, String meetingPoint, String meetingPointWazeUrl, List<String> participants) {
        this.tripID = tripID;
        this.creator = creator;
        this.title = title;
        this.description = description;
        this.date = date;
        this.meetingPoint = meetingPoint;
        this.meetingPointWazeUrl = meetingPointWazeUrl;
        this.participants = new ArrayList<>();
        for (int i = 0; i < participants.size(); i++) {
            this.participants.add(participants.get(i));
        }
    }

    public Trip(String tripID, String creator, String title, String description, String date, String meetingPoint, String meetingPointWazeUrl) {
        this.tripID = tripID;
        this.creator = creator;
        this.title = title;
        this.description = description;
        this.date = date;
        this.meetingPoint = meetingPoint;
        this.meetingPointWazeUrl = meetingPointWazeUrl;
    }
}


