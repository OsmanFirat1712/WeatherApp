package com.example.projekt1rain.WeaterApi

import com.example.projekt1rain.WeaterApi.ApiOptions.Main
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IWeatherApi {
    @GET("weather")
    fun getWeather(
        @Query("q") cityname: String?,
        @Query("appid") apiKey: String?
    ): Call<Main?>?



}