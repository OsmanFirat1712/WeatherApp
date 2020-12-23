/*
package com.example.projekt1rain.Room

import androidx.room.TypeConverter
import com.beust.klaxon.Klaxon
import com.beust.klaxon.Parser
import com.google.gson.Gson
import java.lang.reflect.Type

class TypeConverters {
    object Converters {
        @TypeConverter
        fun fromString(value: String?): ArrayList<String> {
            val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.gety()
            return Gson().fromJson(value, listType)
        }

        @TypeConverter
        fun fromArrayLisr(list: ArrayList<String?>?): String {
            val gson = Gson()
            return gson.toJson(list)
        }
    }

    fun parse(name: String):Any?{
        val cls = Parser::class.java
    }
    val Worddict = Klaxon().parse<List<City>>(assets.open("citylist.json"))

}*/
