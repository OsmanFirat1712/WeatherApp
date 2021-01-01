package com.example.projekt1rain.RetrofitApi

import com.example.projekt1rain.CurrentWeather
import com.example.projekt1rain.CurrentWeather.*
import com.google.gson.annotations.SerializedName

class CurrentWeatherResponse {
    @SerializedName("main")
    var main: Main? = null

}