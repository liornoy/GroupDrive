package com.example.groupdrive.model.user;

import android.net.Uri;

import androidx.room.Entity;

@Entity(tableName = "user_table")
public class User {
    String personName;
    String personId;
    Uri personPhoto;

    public User(String id, String name, Uri photo) {
        personId = id;
        personName = name;
        personPhoto = photo;
    }
}
