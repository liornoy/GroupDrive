package com.example.groupdrive.model.trip;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.groupdrive.model.trip.Trip;

import java.util.List;

@Dao
public interface TripDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addTrip(Trip trip);

    @Delete
    void delete(Trip trip);

    @Query("SELECT * FROM trip_table ORDER BY id ASC")
    LiveData<List<Trip>> getAll();
}
