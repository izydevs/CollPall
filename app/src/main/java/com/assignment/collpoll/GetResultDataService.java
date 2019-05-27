package com.assignment.collpoll;

import android.util.Log;

import com.assignment.collpoll.Interface.APIInterface;
import com.assignment.collpoll.Interface.MainInterface;
import com.assignment.collpoll.Utils.Utils;
import com.assignment.collpoll.model.WeatherMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetResultDataService implements MainInterface.GetWeatherIntractor {
    @Override
    public void getWeatherData(final OnFinishedListener onFinishedListener,String city) {
        Call<WeatherMap> call = APIClient.getClient(Utils.BASE_URL).create(APIInterface.class).getDataFromServer(city,Utils.APPID);
        call.enqueue(new Callback<WeatherMap>() {
            @Override
            public void onResponse(Call<WeatherMap> call, Response<WeatherMap> response) {
                Log.d("asdf", "onResponse...."+response.body());
                if (response.body()!=null && response.body().getCod()==200) {
                    onFinishedListener.onFinished(response.body(), true);
                }else {
                    onFinishedListener.onFinished(response.body(), false);
                }
            }

            @Override
            public void onFailure(Call<WeatherMap> call, Throwable t) {
                onFinishedListener.onFailure(t);
                Log.d("asdf", "onResponse...."+t);

            }
        });
    }
}
