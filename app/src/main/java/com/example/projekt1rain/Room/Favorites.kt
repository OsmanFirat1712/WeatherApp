package com.example.projekt1rain.Room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.projekt1rain.CurrentWeather

@Entity
data class Favorites (
    @PrimaryKey(autoGenerate = true) var id:Long,
      val city:City,
      val currentWeather:CurrentWeather


)