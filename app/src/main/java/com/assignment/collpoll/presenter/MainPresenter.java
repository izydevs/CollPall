package com.assignment.collpoll.presenter;

import com.assignment.collpoll.Interface.MainInterface;
import com.assignment.collpoll.model.WeatherDetails;
import com.assignment.collpoll.model.WeatherMap;

public class MainPresenter implements MainInterface.presenter, MainInterface.GetWeatherIntractor.OnFinishedListener {

    private MainInterface.MainView mainView;
    private MainInterface.GetWeatherIntractor weatherIntractor;

    public MainPresenter(MainInterface.MainView mainView, MainInterface.GetWeatherIntractor weatherIntractor) {
        this.mainView = mainView;
        this.weatherIntractor = weatherIntractor;
    }


    @Override
    public void onFinished(WeatherMap weatherMaps,boolean cityFound) {
        if (mainView!=null){
            if (cityFound) {
                mainView.onResponseSuccess(weatherMaps);
            }else {
                mainView.onResponseCityNotFound("city not found");
            }
            mainView.hideProgress();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        if (mainView!=null){
            mainView.onResponseFailure(t);
            mainView.hideProgress();
        }
    }

    @Override
    public void requestDataFromServer(String city) {
        mainView.showProgress();
        weatherIntractor.getWeatherData(this,city);
    }
}
