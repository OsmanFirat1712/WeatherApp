package com.example.projekt1rain.DataStorag

import com.example.projekt1rain.*
import com.example.projekt1rain.InterFaces.CallBack
import com.example.projekt1rain.InterFaces.GetName
import com.example.projekt1rain.InterFaces.RemoveCallBack
import com.example.projekt1rain.Room.*
import javax.security.auth.callback.Callback


class DataClass(private val dataBase: WeatherDatabase = DatabaseProvider.getInstance()) : DataService {
    private val taskRunner: TaskRunner = TaskRunner()

/*    override fun getOneCallFromRoom(): OneCall {
        TODO("Not yet implemented")
    }

    override fun saveOneCallFromRoom(oneCall: List<OneCall>) {
        TODO("Not yet implemented")
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

    override fun deleteFavorites(favorites: Favorites, removeCallBack: RemoveCallBack){
        val data = dataBase.currentWeatherDao().delete(favorites)
    }


/*
    override fun deleteFavorites(favorites: Favorites, removeCallBack: RemoveCallBack){
        val runner = TaskRunner()
        runner.executeAsync({
                          val data =  dataBase.currentWeatherDao().delete(favorites)
            data
        }, {result-> removeCallBack.onFinish(result) })
    }
*/


    override fun getFavorites(callback: CallBack) {
        val runner = TaskRunner()
        runner.executeAsync({
            val getFavoriteslist: List<Favorites> = dataBase.currentWeatherDao().getFavoritesList()
            getFavoriteslist
        }, {result-> callback.onComplete(result) })
    }
}


