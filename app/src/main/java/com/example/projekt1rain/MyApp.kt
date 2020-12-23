package com.example.projekt1rain

import android.app.Application
import com.example.projekt1rain.DataStorag.DataClass
import com.example.projekt1rain.DataStorag.DataService



class MyApp: Application() {
    lateinit var dataService:DataService

    override fun onCreate() {
        super.onCreate()

       dataService = DataClass()


    }
}