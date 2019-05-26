package com.assignment.collpoll.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.assignment.collpoll.R;
import com.assignment.collpoll.Utils.Utils;
import com.assignment.collpoll.model.WeatherDetails;

public class WeatherDetailsActivity extends AppCompatActivity {

    private WeatherDetails weatherDetails = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_deatils);
        if (getIntent() != null) {
            weatherDetails = (WeatherDetails) getIntent().getSerializableExtra("weather_details");
            setData(weatherDetails);
        }
    }

    private void setData(WeatherDetails weatherDetails) {
        String details = "City Name : " + weatherDetails.getCityName() + "\n\n" + "Weather condition : " + weatherDetails.getWeatherCondition() +
                "\n\nTemperature : " + Utils.changekelvinToCelcius(weatherDetails.getTemp()) + "\n\nMinimum Temperature : " +
                Utils.changekelvinToCelcius(weatherDetails.getMinTemp()) + "\n\nMaximum Temperature : " + Utils.changekelvinToCelcius(weatherDetails.getMaxTemp()) +
                "\n\nHumidity : " + weatherDetails.getHumidity() + "\n\nPressure : " + weatherDetails.getPressure() + "\n\nWind Speed : " +
                weatherDetails.getWindSpeed() + "\n\nLast Updated : " + weatherDetails.getDate();
        TextView textView = findViewById(R.id.weather_details);
        textView.setText(details);
    }
}
