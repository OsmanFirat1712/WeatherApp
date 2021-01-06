package com.example.projekt1rain.RetrofitApi

import com.example.projekt1rain.Hourly
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Query

interface CallWeatherApi {

    @GET("weather")
    fun getWeather(@retrofit2.http.Query("q") cityname: String?,
                   @retrofit2.http.Query("appid") apiKey: String?
    ):Call<CurrentWeatherResponse?>?

    @GET("onecall")
    fun getDailyForecast(@retrofit2.http.Query("lat") lat: Double?,
                         @retrofit2.http.Query("lon") long: Double?,
                         @retrofit2.http.Query("exclude") hourly: Int?,
                         @retrofit2.http.Query("appid") apiKey: String?,
    ):Call<CurrentWeatherResponse?>?


}


