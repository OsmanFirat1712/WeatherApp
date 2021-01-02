package com.example.projekt1rain
import com.example.projekt1rain.CurrentWeather


import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import com.google.gson.annotations.SerializedName

private val klaxon = Klaxon()

@Entity
data class OneCall @JvmOverloads constructor(
    @PrimaryKey(autoGenerate = true) var onecallid: Long,
    val lat: Double,
    val lon: Double,
    val timezone: String,


    @SerializedName("timezone_offset")
    val timezoneOffset: Long,

    val weather: CurrentWeather,
    val current: CurrentWeather,
    @Embedded(prefix = "daily_")
    val daily: List<Daily>,
    @Embedded(prefix = "minutely")
    val minutely: List<Minutely>,
    @Embedded(prefix = "hourly")
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
    @SerializedName("wind_speed")
    val windSpeed: Double,
    @SerializedName("wind_deg")
    val windDeg: Int,
)


data class Daily(

    val dt: Long,
    val sunrise: Int,
    val sunset: Int,
    val temp: Long,
    val pressure: Int,
    val humidity: Int,
    @SerializedName("dew_point")
    val dewPoint: Double,
    @SerializedName("wind_deg")
    val windDeg: Int,



    )

