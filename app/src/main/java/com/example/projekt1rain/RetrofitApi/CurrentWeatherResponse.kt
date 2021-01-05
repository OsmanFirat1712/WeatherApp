package com.example.projekt1rain.RetrofitApi

import androidx.room.Embedded
import com.example.projekt1rain.Coord
import com.example.projekt1rain.CurrentWeather
import com.example.projekt1rain.CurrentWeather.*
import com.example.projekt1rain.Main
import com.example.projekt1rain.Weather
import com.google.gson.annotations.SerializedName

class CurrentWeatherResponse {
    var main: Main? = null
    var weather:List<Weather>? = null
    var coord:Coord? = null


}