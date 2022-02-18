package com.example.groupdrive.db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.groupdrive.db.AppDatabase;
import com.example.groupdrive.model.trip.Trip;
import com.example.groupdrive.model.trip.TripDao;

import java.util.List;

public class AppRepository {
    private TripDao mTripDao;
    private LiveData<List<Trip>> mAllTrips;

    AppRepository(Context context) {
        AppDatabase tripDatabase = AppDatabase.getDatabase(context);
        mTripDao = tripDatabase.tripDao();
        mAllTrips = mTripDao.getAll();
    }

    LiveData<List<Trip>> getmAllTrips() {
        return mAllTrips;
    }
}
