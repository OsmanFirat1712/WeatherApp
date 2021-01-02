package com.example.projekt1rain.Adapter

import android.app.Person
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi

import androidx.recyclerview.widget.RecyclerView
import com.example.projekt1rain.DataWeatherClass
import com.example.projekt1rain.Fbiragments.MapViewFragment
import com.example.projekt1rain.ForYouConstruktor
import com.example.projekt1rain.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import kotlinx.android.synthetic.main.foryoufragment.view.*

private lateinit var graph: BarChart
private lateinit var cityPicture: ImageView
private lateinit var cityName: TextView
private lateinit var temperture: TextView
private lateinit var iconDayNight: ImageView
private lateinit var time: TextView
val mapViewFragment = MapViewFragment()
private lateinit var person: Person
private lateinit var weatherClass: MutableList<DataWeatherClass>


class ForYouAdapter(private val forYouConstruktorList:List<ForYouConstruktor>) : RecyclerView.Adapter<ForYouAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForYouAdapter.ViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.foryoufragment, parent, false)

        return ViewHolder(itemview)

    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onBindViewHolder(holder: ForYouAdapter.ViewHolder, position: Int) {

        val curentItem = forYouConstruktorList[position]

        holder.cityPicture.setImageResource(curentItem.icon2)
        holder.cityName.text = curentItem.city
        holder.temperture.text = curentItem.temper.toString()
        holder.iconDayNight.setImageResource(curentItem.iconLight)
        holder.time.text = curentItem.timeNumber.toString()

    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cityName:TextView = itemView.tvCityName
        val temperture:TextView = itemView.tvtemperture
        val time:TextView = itemView.tvTime
        val iconDayNight = itemView.ivDayNightIcon
        val cityPicture= itemView.ivCityPicture

    }

    override fun getItemCount() = forYouConstruktorList.size

    }


