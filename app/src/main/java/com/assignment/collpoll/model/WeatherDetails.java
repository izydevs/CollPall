package com.assignment.collpoll.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;

@Entity(tableName = "weatherdetails")
public class WeatherDetails implements Serializable {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "city")
    private String cityName;

    @ColumnInfo(name = "weather_condition")
    private String weatherCondition;

    @ColumnInfo(name = "temp")
    private double temp;

    @ColumnInfo(name = "min_temp")
    private double minTemp;

    @ColumnInfo(name = "max_temp")
    private double maxTemp;

    @ColumnInfo(name = "date")
    private long date;

    @ColumnInfo(name = "humidity")
    private int humidity;

    @ColumnInfo(name = "pressure")
    private int pressure;

    @ColumnInfo(name = "wind_speed")
    private double windSpeed;

    public WeatherDetails(String cityName, String weatherCondition, double temp, double minTemp, double maxTemp, long date, int humidity, int pressure, double windSpeed) {
        this.cityName = cityName;
        this.weatherCondition = weatherCondition;
        this.temp = temp;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.date = date;
        this.humidity = humidity;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }
}
