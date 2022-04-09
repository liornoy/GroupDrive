package com.example.groupdrive.db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.groupdrive.model.trip.Trip;
import com.example.groupdrive.model.trip.TripDao;

import java.util.List;

public class AppRepository {
    private TripDao mTripDao;
    private LiveData<List<Trip>> mAllTrips;

    AppRepository(Context context) {
        mAllTrips = mTripDao.getAll();
    }

    LiveData<List<Trip>> getmAllTrips() {
        return mAllTripRs;
    }
}
