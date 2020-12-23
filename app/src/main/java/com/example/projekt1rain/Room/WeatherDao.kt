package com.example.projekt1rain.Room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.projekt1rain.CurrentWeather
import com.example.projekt1rain.Minutely
import com.example.projekt1rain.OneCall


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

    @Query("SELECT * FROM CurrentWeather")
    suspend fun getWeatherList():List<CurrentWeather.Weather>


    @Insert
    suspend fun insert (oneCall:OneCall)

    @Delete
    suspend fun delete (oneCall: OneCall)

    @Update
    suspend fun update (oneCall: OneCall)

    @Query ("SELECT * FROM OneCall WHERE onecallid = :onecallid")
    suspend fun getOneCallbyId(onecallid:Long):OneCall

    @Query("SELECT * FROM OneCall")
    suspend fun getOneCallList():List<Minutely>



    @Insert
    suspend fun insert (city: City)

    @Delete
    suspend fun delete (city: City)

    @Update
    suspend fun update (city: City)

    @Query ("SELECT * FROM City WHERE cityid = :cityid")
    suspend fun getCitybyId(cityid:Long):City

    @Query("SELECT * FROM City")
    fun  getLiveDataCityList():LiveData<List<City>>

/*
    @Query("SELECT * FROM OneCall")
    suspend fun getCity():List<Minutely>*/

}