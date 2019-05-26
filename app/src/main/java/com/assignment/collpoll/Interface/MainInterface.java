package com.assignment.collpoll.Interface;

import com.assignment.collpoll.model.WeatherMap;

public class MainInterface {

    public interface presenter{
        void requestDataFromServer(String city);
    }
    public interface MainView{
        void showProgress();
        void hideProgress();
        void onResponseSuccess(WeatherMap weatherMap);
        void onResponseFailure(Throwable throwable);
        void onResponseCityNotFound(String message);
    }

    public  interface GetWeatherIntractor {

        interface OnFinishedListener {
            void onFinished(WeatherMap weatherMaps,boolean cityFound);
            void onFailure(Throwable t);
        }

        void getWeatherData(OnFinishedListener onFinishedListener,String city);
    }

}
