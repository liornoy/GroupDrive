package com.example.groupdrive.model.user;

import android.net.Uri;

import androidx.room.Entity;

@Entity(tableName = "user_table")
public class User {
    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public Uri getPersonPhoto() {
        return personPhoto;
    }

    public void setPersonPhoto(Uri personPhoto) {
        this.personPhoto = personPhoto;
    }

    String personName;
    String personId;
    Uri personPhoto;

    public User(String id, String name, Uri photo) {
        personId = id;
        personName = name;
        personPhoto = photo;
    }
}
