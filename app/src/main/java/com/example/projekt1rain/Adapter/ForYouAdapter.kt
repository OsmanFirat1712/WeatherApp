package com.example.projekt1rain.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.projekt1rain.DataWeatherClass
import com.example.projekt1rain.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry

private lateinit var graph: BarChart
private lateinit var cityPicture: ImageView
private lateinit var cityName: TextView
private lateinit var temperture: TextView
private lateinit var iconDayNight: ImageView
private lateinit var time: TextView


//class ForYouAdapter(requireContext: Context) : RecyclerView.Adapter<ForYouAdapter.ViewHolder>() {
class ForYouAdapter(weatherClass: DataWeatherClass) :
    RecyclerView.Adapter<ForYouAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForYouAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.foryoufragment, parent, false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ForYouAdapter.ViewHolder, position: Int) {
       val weatherData: MutableList<Entry> = mutableListOf(
            Entry(0.0f, 10.0f),
            Entry(1.0f, 15.0f),
            Entry(2.0f, 4.0f),
            Entry(3.0f, 7.0f),
            Entry(4.0f, 20.0f),
        )
        holder.cityPicture
        holder.cityName
        holder.temperture
        holder.iconDayNight
        holder.time
        holder.graph
        val dataSet = BarDataSet(weatherData.map { BarEntry(it.x, it.y) }, "Weather Date")
        holder.graph.data = BarData(dataSet)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val cityPicture = itemView.findViewById<ImageView>(R.id.ivCityPicture)
        val cityName = itemView.findViewById<TextView>(R.id.tvCityName)
        val temperture = itemView.findViewById<TextView>(R.id.tvtemperture)
        val iconDayNight = itemView.findViewById<ImageView>(R.id.ivDayNightIcon)
        val time = itemView.findViewById<TextView>(R.id.tvTime)
        val graph = itemView.findViewById<BarChart>(R.id.chChart)

    }


    override fun getItemCount(): Int {
        return 1 //favorites.sizes
    }

}

/*
graph = itemView.findViewById(R.id.chChart)
cityPicture = view.findViewById(R.id.ivCityPicture)
cityName = view.findViewById(R.id.tvCityName)
temperture = view.findViewById(R.id.tvtemperture)
iconDayNight = view.findViewById(R.id.ivDayNightIcon)
time = view.findViewById(R.id.tvTime)
cardview = view.findViewById(R.id.cvCardViewForYou)*/
/*
val dataSet = BarDataSet(weatherData.map { BarEntry(it.x, it.y) }, "Weather Date")
graph.data = BarData(dataSet)*/
