package com.example.projekt1rain.RetrofitApi

import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.projekt1rain.Fbiragments.MapViewFragment
import com.example.projekt1rain.ForYouConstruktor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.StringBuilder
import java.lang.reflect.Array.get
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "Retrofit Connection"


object RetrofitSetup {

    //var test = MapViewFragment.address
    var urlAll = "api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}"
    var url = "https://api.openweathermap.org/data/2.5/"
    val apiKey = "d459f98ffa705ad3f6c5e02f86d9fab9"

}

fun retrofitResponse(address:String){

    val retrofit = Retrofit.Builder()
        .baseUrl(RetrofitSetup.url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val weatherApi = retrofit.create(CallWeatherApi::class.java)
    val weatherResponseCall = weatherApi.getWeather(address,RetrofitSetup.apiKey)


    weatherResponseCall!!.enqueue(object : Callback<CurrentWeatherResponse?> {
        override fun onResponse(call: Call<CurrentWeatherResponse?>, response: Response<CurrentWeatherResponse?>
        ) {
            if (response.code() == 404) {
                Log.d(TAG,"Successfuly")
            } else if (!response.isSuccessful) {
                Log.d(TAG,"Error")
            }

            val mydata = response.body()
            val main = mydata!!.main
            val temp = main!!.temp
            val pres =main!!.pressure
            val temperature = (temp!! - 273.15).toInt()
            Log.d("TAG","City pressure :" + pres)
            Log.d("TAG","City Temp : " + temperature)


        }
        override fun onFailure(call: Call<CurrentWeatherResponse?>, t: Throwable) {}
    })
}

