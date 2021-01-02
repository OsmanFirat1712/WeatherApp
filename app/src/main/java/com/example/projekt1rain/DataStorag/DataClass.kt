

package com.example.projekt1rain.DataStorag

import androidx.lifecycle.LiveData
import com.example.projekt1rain.CurrentWeather
import com.example.projekt1rain.OneCall
import com.example.projekt1rain.Room.City
import com.example.projekt1rain.Room.DatabaseProvider
import com.example.projekt1rain.Room.WeatherDatabase
import javax.security.auth.callback.Callback


class DataClass(private val dataBase:WeatherDatabase = DatabaseProvider.getInstance()):DataService{

/*    override fun getOneCallFromRoom(): OneCall {
        TODO("Not yet implemented")
    }

    override fun saveOneCallFromRoom(oneCall: List<OneCall>) {
        TODO("Not yet implemented")
    }*/

    override fun getCurrentCallFromRoom(): CurrentWeather {
        TODO("Not yet implemented")
    }

    override fun saveCurrentCallFromRoom(currentWeather: List<CurrentWeather>) {
        TODO("Not yet implemented")

    }

    override fun saveCities(cities: List<City>) {
        val citiesList:LiveData<List<City>> = dataBase.currentWeatherDao().getLiveDataCityList()
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
}
