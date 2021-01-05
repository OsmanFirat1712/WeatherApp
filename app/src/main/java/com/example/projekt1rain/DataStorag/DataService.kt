package com.example.projekt1rain.DataStorag

import android.app.Application
import com.example.projekt1rain.*
import com.example.projekt1rain.InterFaces.CallBack
import com.example.projekt1rain.InterFaces.GetName
import com.example.projekt1rain.Room.City
import com.example.projekt1rain.Room.Favorites
import javax.security.auth.callback.Callback

interface DataService {


    /* fun getOneCallFromRoom(): OneCall

     fun saveOneCallFromRoom(oneCall: List<OneCall>)*/

/*
    suspend fun saveCurrentCallFromRoom(id: Long, base: String, clouds: Clouds, cod: Int, coord: Coord, dt: Int, main: Main, name: String, sys: Sys, timeZone: Int?, visibility: Int?, weather: List<Weather>, windDeg: Int?, uvi: Double, windSpeed: Double, wind: Wind)

    fun getCurrentCallFromRoom(currentWeather:CurrentWeather)
*/

     fun saveCities(cities: List<City>)

    fun getCitiesFindbyName(name:String,getName: GetName)


/*    fun getOneCallFromApi(callback: Callback): OneCall*/

    fun saveCurrentCallFromApi(currentWeather: List<CurrentWeather>, callback: Callback)
/*
    fun saveOneCallFromApi(oneCall: List<OneCall>,callback: Callback)*/

    fun getCurrentCallFromApi(callback: Callback): CurrentWeather

    suspend fun saveFavorites( address:String, currentWeather: CurrentWeather)


    fun getFavorites(callback: CallBack)





}





