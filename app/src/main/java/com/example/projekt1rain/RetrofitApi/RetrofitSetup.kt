package com.example.projekt1rain.RetrofitApi

import android.util.Log
import com.example.projekt1rain.Fragments.MapViewFragment
import com.example.projekt1rain.Room.DatabaseProvider
import com.example.projekt1rain.Room.Favorites
import com.example.projekt1rain.Room.WeatherDatabase
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors
import java.util.function.DoubleBinaryOperator

private const val TAG = "Retrofit Connection"
private lateinit var favorites:Favorites


object RetrofitSetup {

    var urlAll = "api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}"
    var url = "https://api.openweathermap.org/data/2.5/"
    val apiKey = "d459f98ffa705ad3f6c5e02f86d9fab9"
    
}

fun retrofitResponse(address:String, dataBase: WeatherDatabase = DatabaseProvider.getInstance()){

    val retrofit = Retrofit.Builder()
        .baseUrl(RetrofitSetup.url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val weatherApi = retrofit.create(CallWeatherApi::class.java)
    val weatherResponseCall = weatherApi.getWeather(address,RetrofitSetup.apiKey)

        weatherResponseCall!!.enqueue(object : Callback<CurrentWeatherResponse?> {
        override fun onResponse(call: Call<CurrentWeatherResponse?>, response: Response<CurrentWeatherResponse?>
        ) {
            if (response.code() == 200) {
                Log.d(TAG,"Successfuly")
            } else if (!response.isSuccessful) {
                Log.d(TAG,"Error")
            }

            Executors.newSingleThreadExecutor().execute {val favorites = Favorites(0L, address, response.body())
                dataBase.currentWeatherDao().insertfavorites(favorites)}


            val mydata = response.body()
            val main = mydata!!.main
            val temp = main!!.temp
            val pres =main!!.pressure
            val temperature = (temp!! - 273.15).toInt()
            Log.d("TAG","City pressure :" +   pres)
            Log.d("TAG","City Temp : " + temperature)

        }
        override fun onFailure(call: Call<CurrentWeatherResponse?>, t: Throwable) {
            Log.d(TAG,"Error im log.de${t.toString()}")

        }
    })
}
fun retrofitResponse2(lat:Double,long:Double){
    val retrofit = Retrofit.Builder()
        .baseUrl(RetrofitSetup.url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val weatherApi = retrofit.create(CallWeatherApi::class.java)

    val weatherOneCallResponse =weatherApi.getHourlyForecast(lat,long,RetrofitSetup.apiKey)
    weatherOneCallResponse!!.enqueue(object :Callback<CurrentWeatherResponse?>{
        override fun onResponse(
            call: Call<CurrentWeatherResponse?>,
            response: Response<CurrentWeatherResponse?>
        ) {
            if (response.code() == 200) {
                Log.d(TAG,"Successfuly")
            } else if (!response.isSuccessful) {
                Log.d(TAG,"Error")
            }
            val myOneCallData = response.body()
            val hourly = myOneCallData!!.hourly
            val hourlyTemperature = hourly?.get(2)?.temp
            val tempp =(hourlyTemperature!! - 273.15).toInt()
            val hourlyClouds = hourly?.get(4)?.clouds
            val hourlyPressure = hourly?.get(3)?.pressure

            Log.d("TAG","hourlyClouds : " + hourlyClouds)
            Log.d("TAG","hourlyPressure : " + hourlyPressure)
            Log.d("TAG","temppppp : " + tempp)

            val daily = myOneCallData!!.daily
            val dailyTemperature = daily?.get(3)?.temp
            val dailytemp = (dailyTemperature!! - 273.15).toInt()

            Log.d("TAG","dailyTemp" + dailytemp)

        }

        override fun onFailure(call: Call<CurrentWeatherResponse?>, t: Throwable) {
            Log.d(TAG,"Error im log.de${t.toString()}")
        }

    })
}




