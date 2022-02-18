package com.example.groupdrive.model.trip;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;


@Entity(tableName = "trip_table")
public class Trip {
    @PrimaryKey(autoGenerate = true)
    int id;
    String title;
    String duration;
    Date date;

    public Trip(String title, String duration, Date date) {
        this.title = title;
        this.duration = duration;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public void setDate(Date newDate) {
        this.date = newDate;
    }

    public void setDuration(String newDuration) {
        this.duration = newDuration;
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


