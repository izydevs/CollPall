package com.assignment.collpoll;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.assignment.collpoll.Dao.WeatherDao;
import com.assignment.collpoll.model.WeatherDetails;

@Database(entities = WeatherDetails.class,exportSchema = false,version = 1)
public abstract class WeatherDatabase extends RoomDatabase {

    private static final String DB_Name="weather_db";
    private static WeatherDatabase instance;

    public static synchronized WeatherDatabase getInstance(Context context){
        if (instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),WeatherDatabase.class,DB_Name)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract WeatherDao weatherDao();

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }

}
