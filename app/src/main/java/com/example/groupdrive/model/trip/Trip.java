package com.example.groupdrive.model.trip;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class Trip {
    public String getId() {
        return _id;
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

    public int getMaxGroupSize(){return maxGroupSize;}
    public boolean getTripFull(){return tripFull;}


    @SerializedName("_id")
    @Expose
    private String _id;
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
    @SerializedName("maxGroupSize")
    @Expose
    private int maxGroupSize;
    @SerializedName("tripFull")
    @Expose
    private boolean tripFull;
    public void setId(String _id) {
        this._id = _id;
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

    public void setMaxGroupSize(int maxGroupSize){this.maxGroupSize=maxGroupSize;}
    public void setTripFull(boolean tripFull){this.tripFull=tripFull;}


    public void setMeetingPointWazeUrl(String meetingPointWazeUrl) {
        this.meetingPointWazeUrl = meetingPointWazeUrl;
    }

    public boolean isUserJoined(String username){
        for (int i=0; i<participants.size(); i++){
            if (participants.get(i).toString().equals(username)){
                return true;
            }
        }
        return false;
    }

    public void addUser(String username){
        if (!isUserJoined(username)){
            participants.add(username);
        }
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

    public Trip(String _id, String creator, String title, String description, String date, String meetingPoint, String meetingPointWazeUrl, List<String> participants, int maxGroupSize, boolean tripFull) {
        this._id = _id;
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
        this.maxGroupSize = maxGroupSize;
        this.tripFull = tripFull;
    }

    public Trip(String _id, String creator, String title, String description, String date, String meetingPoint, String meetingPointWazeUrl,int maxGroupSize,boolean tripFull) {
        this._id = _id;
        this.creator = creator;
        this.title = title;
        this.description = description;
        this.date = date;
        this.meetingPoint = meetingPoint;
        this.meetingPointWazeUrl = meetingPointWazeUrl;
        this.maxGroupSize = maxGroupSize;
        this.tripFull = tripFull;
    }
}


