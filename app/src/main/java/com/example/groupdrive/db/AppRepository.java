package com.example.groupdrive.db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.groupdrive.model.trip.Trip;
import com.example.groupdrive.model.trip.TripDao;

import java.util.List;

public class AppRepository {
    private TripDao mTripDao;
    private static AppRepository INSTANCE;

    public static AppRepository getRepository(Context context) {
        if (INSTANCE == null) {
            synchronized (AppRepository.class) {
                INSTANCE = new AppRepository(context);
            }
        }
        return INSTANCE;
    }
    
    private AppRepository(Context context) {
        AppDatabase tripDatabase = AppDatabase.getDatabase(context);
        mTripDao = tripDatabase.tripDao();
    }

    public LiveData<List<Trip>> getTripList() {
        return mTripDao.getAll();
    }

    public void insert(Trip trip) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mTripDao.insert(trip);
        });
    }

    public void delete(Trip trip) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mTripDao.delete(trip);
        });
    }
}
