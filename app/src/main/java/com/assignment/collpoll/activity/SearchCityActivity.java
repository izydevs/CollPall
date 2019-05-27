package com.assignment.collpoll.activity;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.assignment.collpoll.GetResultDataService;
import com.assignment.collpoll.Interface.MainInterface;
import com.assignment.collpoll.R;
import com.assignment.collpoll.Utils.Utils;
import com.assignment.collpoll.WeatherDatabase;
import com.assignment.collpoll.model.WeatherDetails;
import com.assignment.collpoll.model.WeatherMap;
import com.assignment.collpoll.presenter.MainPresenter;

import butterknife.BindView;

public class SearchCityActivity extends AppCompatActivity implements View.OnClickListener, MainInterface.MainView {

    private MainInterface.presenter presenter;
    private EditText cityName;
    private ProgressBar progressBar;
    private ImageView searchCityBtn;
    private TextView saveBtn;
    private ConstraintLayout view;
    private TextView cityNameTv;
    private TextView maxMinTemp;
    private TextView weatherCondition;
    private TextView lastUpdate;
    private WeatherDetails weatherDetails = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);
        cityName = findViewById(R.id.search_et);
        progressBar = findViewById(R.id.progress_bar);
        searchCityBtn = findViewById(R.id.search_iv);
        saveBtn = findViewById(R.id.save_btn);
        view = findViewById(R.id.weather_view);
        cityNameTv = findViewById(R.id.city_name);
        maxMinTemp = findViewById(R.id.temp_diff);
        weatherCondition = findViewById(R.id.weather_desc);
        lastUpdate = findViewById(R.id.date);
        searchCityBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
        saveBtn.setEnabled(false);
        presenter = new MainPresenter(this, new GetResultDataService());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_iv:
                if (Utils.isNetworkConnected(getApplicationContext())) {
                    callApi();
                }else {
                    Toast.makeText(getApplicationContext(), "Check Internet connection", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.save_btn:
                saveWeatherData();
                break;
        }
    }

    private void callApi() {
        Log.d("asdf", "btn clicked");
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(cityName.getWindowToken(), 0);
        if (cityName.getText() != null && !cityName.getText().toString().equals("")) {
            Log.d("asdf", "btn clicked in if");
            presenter.requestDataFromServer(cityName.getText().toString());
        } else {
            Toast.makeText(getApplicationContext(), "Enter City name", Toast.LENGTH_LONG).show();
        }
    }

    private void saveWeatherData() {
        if (weatherDetails != null) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    WeatherDatabase weatherDatabase = WeatherDatabase.getInstance(getApplicationContext());
                    if (weatherDatabase.weatherDao().getWeatherDetails(weatherDetails.getCityName()) != null) {
                        weatherDatabase.weatherDao().updateWeatherDetails(weatherDetails);
                        toastData("update");
                    } else {
                        weatherDatabase.weatherDao().insertWeatherDetails(weatherDetails);
                        toastData("save");

                    }
                }
            };
            thread.start();
        }
    }

    private void toastData(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Weather data " + s, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        searchCityBtn.setEnabled(false);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        searchCityBtn.setEnabled(true);
    }

    @Override
    public void onResponseSuccess(WeatherMap weatherMap) {
        Log.d("asdf", "onResponseSuccess....");
        saveBtn.setEnabled(true);
        weatherDetails = new WeatherDetails(weatherMap.getName(), weatherMap.getWeather().get(0).getMain(), weatherMap.getMain().getTemp(),
                weatherMap.getMain().getTemp_min(), weatherMap.getMain().getTemp_max(), weatherMap.getDt(), weatherMap.getMain().getHumidity(),
                (int)weatherMap.getMain().getPressure(), weatherMap.getWind().getSpeed());

        view.setVisibility(View.VISIBLE);
        updateUI(weatherDetails);


    }

    private void updateUI(WeatherDetails weatherDetails) {
        cityNameTv.setText(String.format("%s°C in %s", (int) (weatherDetails.getTemp() - 273.15), weatherDetails.getCityName()));
        maxMinTemp.setText(String.format("%s°C/%s°C", (int) (weatherDetails.getMaxTemp() - 273.15), (int) (weatherDetails.getMinTemp() - 273.15)));
        weatherCondition.setText(weatherDetails.getWeatherCondition());
        lastUpdate.setText(Utils.getDate(weatherDetails.getDate() * 1000));
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Toast.makeText(getApplicationContext(), "onResponseFailure", Toast.LENGTH_LONG).show();


    }

    @Override
    public void onResponseCityNotFound(String message) {
        saveBtn.setEnabled(false);
        Toast.makeText(getApplicationContext(), "City not found", Toast.LENGTH_LONG).show();
    }
}
