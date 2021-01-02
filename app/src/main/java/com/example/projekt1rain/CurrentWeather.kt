package com.example.projekt1rain

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.beust.klaxon.Klaxon
import com.google.gson.annotations.SerializedName

private val klaxon = Klaxon()


@Entity
    (tableName = "CurrentWeather")
data class CurrentWeather @JvmOverloads constructor(
    //PrimaryKey for Room
    @PrimaryKey(autoGenerate = false) var id: Long,
    val base: String? = null,
    val clouds: Clouds? = null,
    val cod: Int? = null,
    val coord: Coord? = null,
    val dt: Int? = null,
    val main: Main? = null,
    val name: String? = null,
    val sys: Sys? = null,
    val timezone: Int? = null,
    val visibility: Int? = null,
    val weather: List<Weather>? = null,
    @SerializedName("wind_deg")
    val windDeg: Int? = null,
    val uvi: Double? = null,
    @SerializedName("wind_speed")
    val windSpeed: Double? = null,
    val wind: Wind? = null
)


data class Clouds(
    val all: Int? = null
)
data class Coord(
    val lat: Double? = null,
    val lon: Double? = null
)

data class Main(
    val feels_like: Double? = null,
    val humidity: Int? = null,
    val pressure: Int? = null,
    val temp: Double? = null,
    @SerializedName("temp_max")
    val tempMax: Double? = null,
    @SerializedName("temp_min")
    val tempMin: Double? = null,
)

data class Sys(
    val country: String? = null,
    val id: Int? = null,
    val message: Double? = null,
    val sunrise: Int? = null,
    val sunset: Int? = null,
    val type: Int? = null,
)

data class Weather(
    val description: String? = null,
    val icon: String? = null,
    val id: Int? = null,
    val main: String? = null
)

data class Wind(
    val deg: Int? = null,
    val speed: Double? = null
)

