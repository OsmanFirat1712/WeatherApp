package com.example.projekt1rain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon

private val klaxon = Klaxon()

@Entity
data class OneCall(
    @PrimaryKey(autoGenerate = true) var onecallid: Long,
    val lat: Double,
    val lon: Double,
    val timezone: String,


    @Json(name = "timezone_offset")
    val timezoneOffset: Long,

    val weather: CurrentWeather,
    val current: CurrentWeather,
    val daily: List<Daily>,
    val minutely: List<Minutely>,
    val hourly: List<Hourly>

) {
    public fun toJson() = klaxon.toJsonString(this)

    companion object {
        public fun fromJson(json: String) = klaxon.parse<OneCall>(json)
    }
}


data class Minutely(
    val dt: Long,
    val precipitation: Long
)

data class Hourly(
    val dt: Long,
    val precipitation: Long,
    val temp: Long,
    val pressure: Int,
    val humidity: Int,
    val dew_point: Double,
    val uvi: Double,
    val clouds: Int,
    val visibility: Int,
    val wind_speed: Double,
    val wind_deg: Int,
)


data class Daily(

    val dt: Long,
    val sunrise: Int,
    val sunset: Int,
    val temp: Long,
    val pressure: Int,
    val humidity: Int,
    val dew_point: Double,
    val wind_deg: Int,



    )

