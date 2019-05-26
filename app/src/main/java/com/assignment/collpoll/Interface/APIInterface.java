package com.assignment.collpoll.Interface;


import com.assignment.collpoll.model.WeatherMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {

    @GET(".")
    Call<WeatherMap> getDataFromServer(@Query("q") String cit, @Query("APPID") String appid);
}
