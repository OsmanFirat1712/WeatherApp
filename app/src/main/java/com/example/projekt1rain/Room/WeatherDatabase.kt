package com.example.projekt1rain.Room

import android.content.Context
import androidx.room.*
import com.example.projekt1rain.CurrentWeather

@Database(
        entities = [City::class, CurrentWeather::class, Favorites::class],
        exportSchema = false, version = 1
)
@TypeConverters(ObjectConverter::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun currentWeatherDao(): WeatherDao

    companion object {
        @Volatile
        private var instance: WeatherDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        public fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        WeatherDatabase::class.java, "futureWeatherEntries.db")
                        .fallbackToDestructiveMigration()
                        .build()
    }
}