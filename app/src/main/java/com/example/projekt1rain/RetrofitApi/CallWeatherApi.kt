package com.example.projekt1rain.RetrofitApi

import retrofit2.http.GET
import retrofit2.Call

interface CallWeatherApi {

    @GET("weather")
    fun getWeather(@retrofit2.http.Query("q") cityname: String?,
                   @retrofit2.http.Query("appid") apiKey: String?
    ):Call<CurrentWeatherResponse?>?


}


