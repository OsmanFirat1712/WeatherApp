package com.example.projekt1rain.Adapter


import android.app.Person
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projekt1rain.DataWeatherClass
import com.example.projekt1rain.Fragments.MapViewFragment
import com.example.projekt1rain.R
import com.example.projekt1rain.Room.Favorites
import com.github.mikephil.charting.charts.BarChart
import kotlinx.android.synthetic.main.foryoufragment.view.*
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

private lateinit var graph: BarChart
private lateinit var cityPicture: ImageView
private lateinit var cityName: TextView
private lateinit var temperture: TextView
private lateinit var iconDayNight: ImageView
private lateinit var time: TextView
val mapViewFragment = MapViewFragment()
private lateinit var person: Person
private lateinit var weatherClass: MutableList<DataWeatherClass>


class ForYouAdapter(var forYouConstruktorList: List<Favorites>, context: Context) : RecyclerView.Adapter<ForYouAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForYouAdapter.ViewHolder {
        val itemview =
                LayoutInflater.from(parent.context).inflate(R.layout.foryoufragment, parent, false)

        return ViewHolder(itemview)

    }


    override fun onBindViewHolder(holder: ForYouAdapter.ViewHolder, position: Int) {

        val curentItem = forYouConstruktorList[position]
/*        val iconUrl = "http://openweathermap.org/img/w/${curentItem.currentWeatherResponse?.weather?.icon}@2x.png"*/

        holder.apply {
          Glide.with(itemView)
                    .load("http://openweathermap.org/img/wn/${curentItem.currentWeatherResponse?.weather?.first()?.icon}.png")
                    .into(itemView.ivCityPicture)
            cityName.text = curentItem.address
            time.text = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")) + " Uhr"
            temperture.text = """${curentItem.currentWeatherResponse?.main?.temp?.toInt()?.minus(273.15.toInt()).toString()}°C"""
    /*        Glide.with(itemView.context)
                    .load(iconUrl)
                    .into(itemView.ivCityPicture)*/
            /*  holder.temperture.text = (curentItem.currentWeatherResponse?.main?.temp?.minus(273.15.toInt())).toString() + " °C"*/
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cityName: TextView = itemView.tvCityName
        val temperture: TextView = itemView.tvtemperture
        val time: TextView = itemView.tvTime
        val iconDayNight = itemView.ivDayNightIcon
        val cityPicture = itemView.ivCityPicture
        var iconUrl = "http://openweathermap.org/img/wn/10d@2x"


    }

    override fun getItemCount(): Int {
        return forYouConstruktorList.size

    }

    fun updateforyou(forYouConstruktorList: List<Favorites>) {
        this.forYouConstruktorList = forYouConstruktorList
        notifyDataSetChanged()
    }

    private fun  getDate(date: Long): String {
        val timeFormatter = SimpleDateFormat("dd.MM.yyyy")
        return timeFormatter.format(Date(date * 1000L))
    }

}


