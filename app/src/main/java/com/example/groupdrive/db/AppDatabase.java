package com.example.groupdrive.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.groupdrive.model.converters.Converters;
import com.example.groupdrive.model.trip.Trip;
import com.example.groupdrive.model.trip.TripDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Trip.class}, version = 7, exportSchema = false)
@TypeConverters({Converters.class})
abstract public class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract TripDao tripDao();

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class,
                        "trip_database")
                        .build();
            }
        }
        return INSTANCE;
    }
}
