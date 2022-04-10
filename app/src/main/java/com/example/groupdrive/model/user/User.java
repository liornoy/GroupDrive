package com.example.groupdrive.model.user;
public class User {

    public String getGoogleID() {
        return googleID;
    }

    public void setGoogleID(String googleID) {
        this.googleID = googleID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    String googleID;
    String name;
    String photoURL;

    public User(String id, String name, String photo) {
        googleID = id;
        this.name = name;
        photoURL = photo;
    }
}
