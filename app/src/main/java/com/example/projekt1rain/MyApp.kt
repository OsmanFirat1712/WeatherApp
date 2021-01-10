
package com.example.projekt1rain

import android.app.Application
import com.example.projekt1rain.DataStorag.DataClass
import com.example.projekt1rain.DataStorag.DataService
import com.example.projekt1rain.Room.DatabaseProvider
import com.example.projekt1rain.Room.WeatherDatabase


class MyApp() : Application() {
    lateinit var dataService:DataService
    override fun onCreate() {
        super.onCreate()
        DatabaseProvider.initDatabase(this)
        dataService = DataClass()
    }
}
