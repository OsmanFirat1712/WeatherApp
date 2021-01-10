package com.example.projekt1rain.Room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.projekt1rain.CurrentWeather


@Dao
interface WeatherDao{

    @Insert
    suspend fun insert (currentWeather:CurrentWeather)

    @Delete
    suspend fun delete (currentWeather: CurrentWeather)

    @Update
    suspend fun update ( currentWeather: CurrentWeather)

    @Query ("SELECT * FROM CurrentWeather WHERE id = :currentWeatherid")
    suspend fun getWeatherbyId(currentWeatherid:Long):CurrentWeather


    @Insert
     fun insert (city: City)

    @Delete
     fun delete (city: City)

    @Update
     fun update (city: City)

    @Query ("SELECT * FROM City WHERE name = :name")
    fun getCitybyId(name:String):City

    @Query("SELECT * FROM City")
    fun  getLiveDataCityList():LiveData<List<City>>

    @Query("SELECT * FROM City")
    fun  getDataCityList():List<City>

    @Insert
    (onConflict = OnConflictStrategy.REPLACE)
     fun insertfavorites (favorites: Favorites)

    @Delete
     fun delete (favorites: Favorites)

    @Update
     fun update (favorites: Favorites)

    @Query ("SELECT * FROM Favorites")
    fun getFavoritesList():List<Favorites>


    @Query ("SELECT * FROM Favorites WHERE address = :address")
    fun favoritbyadress(address:String):Favorites
}