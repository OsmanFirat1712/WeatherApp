package com.example.projekt1rain.Repository

import android.app.Application
import android.webkit.CookieSyncManager.createInstance
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.projekt1rain.Room.City
import com.example.projekt1rain.Room.WeatherDao
import com.example.projekt1rain.Room.WeatherDatabase
import com.example.projekt1rain.Room.WeatherDatabase.Companion.createInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class WeatherRepository(application: Application) {

    private val weatherDao: WeatherDao

    init {
        val db = WeatherDatabase.createInstance(application)
        weatherDao = db.weatherDao
    }

    //implement all Methods

    suspend fun insert(city: City) {

        withContext(Dispatchers.IO)
        {
            weatherDao.insert(city)
        }
    }

    suspend fun update(city: City) {
        withContext(Dispatchers.IO) {
            weatherDao.update(city)
        }
    }

    suspend fun delete(city: City) {
        withContext(Dispatchers.IO) {
            weatherDao.delete(city)
        }
    }

    suspend fun getCitybyId(cityid: Long): City? {
        var city: City? = null
        withContext(Dispatchers.IO)
        {
            city = weatherDao.getCitybyId(cityid)
        }
        return city
    }

    fun getLiveDataCity(): LiveData<List<City>> = weatherDao.getLiveDataCityList()
}