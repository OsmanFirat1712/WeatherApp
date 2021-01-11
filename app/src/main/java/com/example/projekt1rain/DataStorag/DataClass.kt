package com.example.projekt1rain.DataStorag

import android.util.Log
import androidx.appcompat.widget.SearchView
import com.example.projekt1rain.*
import com.example.projekt1rain.InterFaces.CallBack
import com.example.projekt1rain.InterFaces.GetName
import com.example.projekt1rain.InterFaces.RemoveCallBack
import com.example.projekt1rain.Room.*
import javax.security.auth.callback.Callback


class DataClass(private val dataBase: WeatherDatabase = DatabaseProvider.getInstance()) :
    DataService {
    private val taskRunner: TaskRunner = TaskRunner()

    override fun saveCities(cities: List<City>) {
        try {
            dataBase.currentWeatherDao().insertList(cities)

        }catch (e:Exception){
            Log.e("DataClass","",e)
        }

    }

    override fun getCitiesFindbyName(name: String, getName: GetName) {
        val runner = TaskRunner()
        runner.executeAsync({
            val city = dataBase.currentWeatherDao().getCityByName(name)
            city
        }, { result -> getName.onFinish(result) })

    }

    override fun saveCurrentCallFromApi(currentWeather: List<CurrentWeather>, callback: Callback) {

        TODO("Not yet implemented")
    }

    override fun getCurrentCallFromApi(callback: Callback): CurrentWeather {
        TODO("Not yet implemented")
    }

    override fun savefavorites(favorites: Favorites) {
        val fav = dataBase.currentWeatherDao().insertfavorites(favorites)
    }

    override fun deleteFavorites(favorites: Favorites, removeCallBack: RemoveCallBack) {
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
        }, { result -> callback.onComplete(result) })
    }
}


