package com.example.projekt1rain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.beust.klaxon.Klaxon

private val klaxon = Klaxon()

@Entity
data class CurrentWeather (
    @PrimaryKey(autoGenerate = true) var id:Long,
    open val coord: Coord,
    open val weather: List<Weather>,
    open val base: String,
    open val main: Main,
    open val visibility: Long,
    open val wind: Wind,
    open val clouds: Clouds,
    open val dt: Long,
    open val sys: Sys,
    open val timezone: Long,
    open val name: String,
    open val cod: Long
) {
    public fun toJson() = klaxon.toJsonString(this)

    companion object {
        public fun fromJson(json: String) = klaxon.parse<CurrentWeather>(json)
    }
}

data class Clouds (
    val all: Long
)

data class Coord (
    val lon: Double,
    val lat: Double
)

data class Main (
    val temp: Double,

    @Json(name = "feels_like")
    val feelsLike: Double,

    @Json(name = "temp_min")
    val tempMin: Double,

    @Json(name = "temp_max")
    val tempMax: Double,

    val pressure: Long,
    val humidity: Long
)

annotation class Json(val name: String)

data class Sys (
    val type: Long,
    val id: Long,
    val message: Double,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)

data class Weather (
    val id: Long,
    val main: String,
    val description: String,
    val icon: String
)

data class Wind (
    val speed: Double,
    val deg: Long
)

