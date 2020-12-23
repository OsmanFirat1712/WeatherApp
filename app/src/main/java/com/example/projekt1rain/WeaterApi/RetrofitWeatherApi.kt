package com.example.projekt1rain.WeaterApi

import android.widget.Toast
import com.example.projekt1rain.WeaterApi.ApiOptions.Main
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.coroutineContext


class RetrofitWeatherApi {
    var urlAll = "api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}"
    var url = "https://api.openweathermap.org/data/2.5/"
    var apiKey = "d459f98ffa705ad3f6c5e02f86d9fab9"


     public fun retrofitActivieren(){
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val weatherApi: IWeatherApi = retrofit.create(IWeatherApi::class.java)
        val mainCall: Call<Main> =
            weatherApi.getWeather(editText.getText().toString().trim { it <= ' ' }, apiKey)

         mainCall.enqueue(object : Callback<Main?> {
             override fun onResponse(call: Call<Main?>, response: Response<Main?>) {
                 if (response.code() == 404) {
                     Toast.makeText(this, "Pliease Enter a valid City", Toast.LENGTH_LONG).show()

                 } else if (!response.isSuccessful()) {
                     Toast.makeText(this, response.code(), Toast.LENGTH_LONG).show()
                 }

                 val mydata: Main? = response.body()
                 val main: Main = mydata.getMain()
                 val temp = main.getTemp()
                 val temperature = (temp!! - 273.15).toInt()
                 textView.setText(temperature.toString() + "C")
             }

             override fun onFailure(call: Call<Main?>, t: Throwable) {}
         })
    }



}
