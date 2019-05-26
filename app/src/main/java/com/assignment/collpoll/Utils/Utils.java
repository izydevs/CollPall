package com.assignment.collpoll.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather/";
    public static final String APPID = "81c4613254723f9499942b219874fefb";

    public static String getDate(long time) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("EEE hh:mm a");
            return formatter.format(new Date(Long.parseLong(String.valueOf(time))));
        }catch (Exception e){
            e.printStackTrace();
            return "Today";
        }
    }

    public static String changekelvinToCelcius(double temp) {
        return ((int) (temp - 273.15)) + "°C";
    }
}
