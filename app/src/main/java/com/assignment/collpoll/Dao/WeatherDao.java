package com.assignment.collpoll.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.assignment.collpoll.model.WeatherDetails;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface WeatherDao {

    @Query("Select * from weatherdetails")
    List<WeatherDetails> getWeatherDetailsList();

    @Query("Select * from weatherdetails where city=:cityName")
    WeatherDetails getWeatherDetails(String cityName);

    @Insert
    void insertWeatherDetails(WeatherDetails weatherDetails);

    @Update(onConflict = REPLACE)
    void updateWeatherDetails(WeatherDetails weatherDetails);

    @Delete
    void deleteWeatherDetails(WeatherDetails weatherDetails);
}
