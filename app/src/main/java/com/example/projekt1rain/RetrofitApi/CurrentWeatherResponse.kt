package com.example.projekt1rain.RetrofitApi

import androidx.room.Embedded
import com.example.projekt1rain.*
import com.example.projekt1rain.CurrentWeather.*
import com.google.gson.annotations.SerializedName

class CurrentWeatherResponse {
    var main: Main? = null
    var coord:Coord? = null
    var weather:List<Weather>? = null
    val hourly: List<Hourly>? = null
    val daily:List<Daily>? = null



}