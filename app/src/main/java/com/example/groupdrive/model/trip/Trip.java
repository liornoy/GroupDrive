package com.example.groupdrive.model.trip;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "trip_table")
public class Trip {
    @PrimaryKey(autoGenerate = true)
    int id;
    String title;
    String date;
    String duration;
    String description;
    int physicality;
    String departureTime;

    public Trip(String title, String duration, String date, String description, int physicality, String departureTime) {
        this.title = title;
        this.duration = duration;
        this.description = description;
        this.date = date;
        this.physicality = physicality;
        this.departureTime = departureTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getPhysicality() {
        return physicality + "/5";
    }

    public String getTitle() {
        return title;
    }

    public String getDuration() {
        return duration;
    }

    public String getDate() {
        return date;
    }

    public String getDesc() {
        return description;
    }

    public void setTitle(String newTitle) {
        this.title = newTitle;
    }
}


