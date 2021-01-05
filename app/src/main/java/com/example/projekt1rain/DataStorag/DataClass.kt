package com.example.projekt1rain.DataStorag

import androidx.lifecycle.LiveData
import com.example.projekt1rain.*
import com.example.projekt1rain.InterFaces.CallBack
import com.example.projekt1rain.InterFaces.GetName
import com.example.projekt1rain.Room.*
import java.util.*
import javax.security.auth.callback.Callback


class DataClass(private val dataBase: WeatherDatabase = DatabaseProvider.getInstance()) : DataService {
    private val taskRunner: TaskRunner = TaskRunner()

/*    override fun getOneCallFromRoom(): OneCall {
        TODO("Not yet implemented")
    }

    override fun saveOneCallFromRoom(oneCall: List<OneCall>) {
        TODO("Not yet implemented")
    }*/

/*    override suspend fun getCurrentCallFromRoom(
            id: Long,
            base: String,
            clouds: Clouds,
            cod: Int,
            coord: Coord,
            dt: Int,
            main: Main,
            name: String,
            sys: Sys,
            timeZone: Int?,
            visibility: Int?,
            weather: List<Weather>,
            windDeg: Int?,
            uvi: Double,
            windSpeed: Double,
            wind: Wind
    ) {
        val add = CurrentWeather(id, base, clouds, cod, coord, dt, main, name, sys, timeZone, visibility, weather, windDeg, uvi, windSpeed, wind)
        dataBase.currentWeatherDao().insert(add)
    }*/

/*    override fun saveCurrentCallFromRoom(currentWeather: List<CurrentWeather>) {
        dataBase.currentWeatherDao().insert(List<CurrentWeather>())
    }*/

    override fun saveCities(cities: List<City>) {

        cities.forEach { city ->
            dataBase.currentWeatherDao().insert(city)

        }
    }

    override fun getCitiesFindbyName(name: String,getName: GetName) {
        val runner = TaskRunner()
        runner.executeAsync({
            val getCitiesbyName = dataBase.currentWeatherDao().getCitybyId(name)
            getCitiesbyName
        }, { result -> getName.onFinish(result)})

    }

    /* override fun getOneCallFromApi(callback: Callback): OneCall {
     override fun getOneCallFromApi(callback: Callback): OneCall {

         TODO("Not yet implemented")
     }*/

    override fun saveCurrentCallFromApi(currentWeather: List<CurrentWeather>, callback: Callback) {

        TODO("Not yet implemented")
    }
/*

    override fun saveOneCallFromApi(oneCall: List<OneCall>, callback: Callback) {
        TODO("Not yet implemented")
    }
*/

    override fun getCurrentCallFromApi(callback: Callback): CurrentWeather {
        TODO("Not yet implemented")
    }

    override suspend fun saveFavorites(address: String, currentWeather: CurrentWeather) {
/*
        val favorites = Favorites(0L, address, currentWeather)
        dataBase.currentWeatherDao().insertfavorites(favorites)
*/

    }

    override fun getFavorites(callback: CallBack) {
        val runner = TaskRunner()
        runner.executeAsync({
            val getFavoriteslist: List<Favorites> = dataBase.currentWeatherDao().getFavoritesList()
            getFavoriteslist
        }, {result-> callback.onComplete(result) })
    }
}


