package com.example.projekt1rain.DataStorag

import com.example.projekt1rain.CurrentWeather
import com.example.projekt1rain.OneCall
import com.example.projekt1rain.Room.City
import javax.security.auth.callback.Callback

interface DataService {

    fun getOneCallFromRoom(): OneCall

    fun saveOneCallFromRoom(oneCall: List<OneCall>)

    fun getCurrentCallFromRoom(): CurrentWeather

    fun saveCurrentCallFromRoom(currentWeather: List<CurrentWeather>)

    fun saveCities(cities: List<City>)

    fun getOneCallFromApi(callback: Callback): OneCall

    fun saveCurrentCallFromApi(currentWeather: List<CurrentWeather>,callback: Callback)

    fun saveOneCallFromApi(oneCall: List<OneCall>,callback: Callback)

    fun getCurrentCallFromApi(callback: Callback): CurrentWeather



}





