package com.example.projekt1rain.Room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.projekt1rain.CurrentWeather

@Entity
//Entity for CITYLIST.JSON ASSET
data class City(
    @PrimaryKey(autoGenerate = true) var cityid:Long,
    val name: String,
    val state: String,
    val country: String,
    val cord: CurrentWeather.Coord


)



