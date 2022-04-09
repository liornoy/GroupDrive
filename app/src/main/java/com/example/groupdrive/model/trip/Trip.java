package com.example.groupdrive.model.trip;

import java.sql.Date;
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

    public Date getDateTime() {
        return dateTime;
    }

    public boolean isTripToday() {
        return isTripToday;
    }

    public List<String> getParticipants() {
        return participants;
    }

    private int tripID;
    private String creatorGID;
    private String title;
    private String description;
    private String meetingPoint;
    private String meetingPointWazeUrl;
    private Date dateTime;
    private boolean isTripToday;
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

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public void setTripToday(boolean tripToday) {
        isTripToday = tripToday;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public Trip(int tripID, String creatorGID, String title, String meetingPoint, String meetingPointWazeUrl, Date dateTime, String description) {
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


