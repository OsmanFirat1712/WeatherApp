package com.example.projekt1rain.Room

import android.app.Application
import androidx.loader.content.AsyncTaskLoader
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.projekt1rain.CurrentWeather
import com.example.projekt1rain.OneCall

@Database(entities = [CurrentWeather::class, OneCall::class, City::class, Favorites::class], version = 1, exportSchema = false)
abstract class WeatherDatabase() : RoomDatabase() {
    abstract val weatherDao: WeatherDao

    companion object {

        @Volatile
        private var INSTANCE: WeatherDatabase? = null

        fun createInstance(application: Application): WeatherDatabase {
            synchronized(this)
            {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        application.applicationContext,
                        WeatherDatabase::class.java,
                        "weatherdatabase"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return instance
            }
        }
    }

}