package com.example.projekt1rain.RetrofitApi

import androidx.room.Update
import com.example.projekt1rain.CurrentWeather
import com.example.projekt1rain.CurrentWeather.Main
import com.example.projekt1rain.Fbiragments.MapViewFragment
import com.example.projekt1rain.Room.City
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Query

interface CallWeatherApi {

    @GET("weather")
    fun getWeather(@retrofit2.http.Query("q") cityname: String?,
                   @retrofit2.http.Query("appid") apiKey: String?
    ):Call<CurrentWeatherResponse?>?


}


