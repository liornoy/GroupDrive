package com.example.groupdrive.ui.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.groupdrive.db.AppRepository;
import com.example.groupdrive.model.trip.Trip;

import java.util.List;

public class TripViewModel extends AndroidViewModel {
    private AppRepository repository;
    private LiveData<List<Trip>> allTrips;

    public TripViewModel(Application application) {
        super(application);
        repository = AppRepository.getRepository(getApplication());
        allTrips = repository.getTripList();
    }

    public LiveData<List<Trip>> getAllTrips() {
        return allTrips;
    }

    public Trip getTrip(int id) {
        for (Trip meal : allTrips.getValue()) {
            if (meal.getId() == id) {
                return meal;
            }
        }
        return null;
    }

    public void insert(Trip trip) {
        repository.insert(trip);
    }

    public void delete(Trip trip) {
        repository.delete(trip);
    }

    public void delete(int pos) {
        repository.delete(allTrips.getValue().get(pos));
    }
}
