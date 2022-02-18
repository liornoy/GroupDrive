package com.example.groupdrive;
import android.location.Location;

import java.time.*;


public class Checkpoint {
    java.time.LocalDate time;
    String description;
    Location location;

    public Checkpoint(String description, java.time.LocalDate time,String location ){
        this.description = description;
        this.time = time;
        this.location = new Location(location);
    }
}

