package com.example.projekt1rain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.beust.klaxon.Klaxon

// To parse the JSON, install Klaxon and do:
//
//   val welcome10 = Welcome10.fromJson(jsonString)


private val klaxon = Klaxon()

@Entity
data class OneCall (
    @PrimaryKey (autoGenerate = true) var onecallid: Long,
    val lat: Double,
    val lon: Double,
    val timezone: String,

    @Json(name = "timezone_offset")
    val timezoneOffset: Long,

    val current: Current,
    val minutely: List<Minutely>
) {
    public fun toJson() = klaxon.toJsonString(this)

    companion object {
        public fun fromJson(json: String) = klaxon.parse<OneCall>(json)
    }
}

data class Current (
    val dt: Long,
    val sunrise: Long,
    val sunset: Long,
    val temp: Double,

    @Json(name = "feels_like")
    val feelsLike: Double,

    val pressure: Long,
    val humidity: Long,

    @Json(name = "dew_point")
    val dewPoint: Double,

    val uvi: Double,
    val clouds: Long,
    val visibility: Long,

    @Json(name = "wind_speed")
    val windSpeed: Double,

    @Json(name = "wind_deg")
    val windDeg: Long,

    val weather: List<Weather>
)

  class Currentweather(
     override val coord: Coord,
     override val weather: List<Weather>,
     override val base: String,
     override val main: Main,
     override val visibility: Long,
     override val wind: Wind,
     override val clouds: Clouds,
     override val name: String,
     override val cod: Long,
     override val dt: Long,
     override val sys: Sys,
     override val timezone: Long
 ) : CurrentWeather(
     id ,
     coord,
     weather,
     base,
     main,
     visibility,
     wind,
     clouds,
     dt,
     sys,
     timezone,
     name,
     cod
 )

data class Minutely (
    val dt: Long,
    val precipitation: Long
)
