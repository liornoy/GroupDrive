package com.example.groupdrive.model.trip;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.groupdrive.Itinerary;

@Entity(tableName = "trip_table")
public class Trip {
    @PrimaryKey(autoGenerate = true)
    int id;
    String title;
    Itinerary itinerary;
    String duration;
    int[] participents;

    public Trip(String title, String duration) {
        this.title = title;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public String getDuration() {
        return duration;
    }

    public void setTitle(String newTitle) {
        this.title = newTitle;
    }
}


