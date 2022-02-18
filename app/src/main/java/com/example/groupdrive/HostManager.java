package com.example.groupdrive;

import java.util.HashMap;
import java.util.UUID;


interface Host{
    void createInvitation(int tripID);
    void joinTrip(String uuid, int usedID);
}

public class HostManager implements Host{
    HashMap<String,Integer>pendingInvites;
    @Override
    public void createInvitation(int tripID) {
        String uuid = UUID.randomUUID().toString();
        pendingInvites.put(uuid, tripID);
    }

    @Override
    public void joinTrip(String uuid, int usedID){
        int tripID = pendingInvites.get(uuid);
        // ADD USERID TO THE TRIP
        // IF SUCCESS - DELETE THE UUID FROM THE MAP
    }
}
