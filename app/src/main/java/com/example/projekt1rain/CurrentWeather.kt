package com.example.projekt1rain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.beust.klaxon.Klaxon
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

private val klaxon = Klaxon()

@Entity
data class CurrentWeather(
    //PrimaryKey for Room
    @PrimaryKey(autoGenerate = true) var id:Long,
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind_deg: Int,
    val uvi: Double,
    val wind_speed: Double,
    val wind: Wind
) {
    data class Clouds(
        val all: Int
    )

    data class Coord(
        val lat: Double,
        val lon: Double
    )

    data class Main(
        @SerializedName("feels_like")
        @Expose
        val feels_like: Double,
        @SerializedName("humidity")
        @Expose
        val humidity: Int,
        @SerializedName("pressure")
        @Expose
        val pressure: Int,
        @SerializedName("temp")
        @Expose
        val temp: Double,
        @SerializedName("tempMax")
        @Expose
        val temp_max: Double,
        @SerializedName("tempMin")
        @Expose
        val temp_min: Double
    )

    data class Sys(
        val country: String,
        val id: Int,
        val message: Double,
        val sunrise: Int,
        val sunset: Int,
        val type: Int
    )

    data class Weather(
        val description: String,
        val icon: String,
        val id: Int,
        val main: String
    )

    data class Wind(
        val deg: Int,
        val speed: Double
    )
}