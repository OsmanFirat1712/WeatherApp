package com.example.projekt1rain.Adapter


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.projekt1rain.FragmentCallBack
import com.example.projekt1rain.Fragments.MapViewFragment
import com.example.projekt1rain.InterFaces.RemoveCallBack
import com.example.projekt1rain.MyXAxisFormatter
import com.example.projekt1rain.R
import com.example.projekt1rain.Room.Favorites
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.foryoufragment.view.*
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.Executors
import kotlin.collections.ArrayList

class ForYouAdapter(var forYouConstruktorList: List<Favorites>,context: Context, private val removecall: RemoveCallBack,val fragmentCallBack: FragmentCallBack) : RecyclerView.Adapter<ForYouAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForYouAdapter.ViewHolder {
        val itemview =
                LayoutInflater.from(parent.context).inflate(R.layout.foryoufragment, parent, false)

        return ViewHolder(itemview)

    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ForYouAdapter.ViewHolder, position: Int) {
        val curentItem = forYouConstruktorList[position]

        holder.apply {
            Glide.with(itemView)
                    .load("http://openweathermap.org/img/wn/${curentItem.currentWeatherResponse?.current?.weather?.first()?.icon}@2x.png")
                    .into(itemView.ivCityPicture)
            cityName.text = curentItem.address
            time.text = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")) + " Uhr"
            temperture.text = """${curentItem.currentWeatherResponse?.current?.temp?.toInt()?.minus(273.15.toInt()).toString()}°C"""




            

            val list = mutableListOf<Entry>()
            val xValsDateLabel = ArrayList<String>()
            val xValsOriginalMillis = ArrayList<Long>()

            curentItem.currentWeatherResponse?.hourly?.forEachIndexed { index, hourly ->
                if (index < 12) {
                    val temp = hourly.temp.toInt().minus(273.15.toInt().toString().toFloat())
                    list.add(Entry(index.toFloat(), temp))
                    xValsOriginalMillis.add(hourly.dt)
                }
            }

            for (i in xValsOriginalMillis) {
                val mm = i / 60 % 60
                val hh = i / (60 * 60) % 24
                val mDateTime = "$hh:$mm "
                xValsDateLabel.add(mDateTime)
            }

            val cl: ConstraintLayout = itemView.findViewById(R.id.constraint)
            val chart: LineChart = itemView.findViewById(R.id.chChart)
            val barEntries = list.map { Entry(it.x, it.y, xValsDateLabel) }
            val dataSet = LineDataSet(barEntries, "Temperatur den nächsten Stunden")

            dataSet.fillAlpha = 5000
            dataSet.color = Color.RED
            dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
            chart.description.text = ""
            chart.legend.isEnabled = true
            chart.invalidate()
            chart.axisRight.isEnabled = false
            chart.axisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
            chart.axisRight.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)


            val xAxis = chart.xAxis
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
            xAxis.labelCount = 4
            xAxis.granularity = 1f
            xAxis.isGranularityEnabled = true

            chart.data = LineData(dataSet)

            chart.xAxis.valueFormatter = (MyXAxisFormatter.MyValueFormatter(xValsDateLabel))

            cardview.setOnClickListener {
                fragmentCallBack.onCall(curentItem)

            }
            cardview.setOnLongClickListener {
                removecall.onRemove(curentItem)
                notifyDataSetChanged()
                notifyItemRemoved(position)
                true
            }
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cityName: TextView = itemView.tvCityName
        val temperture: TextView = itemView.tvtemperture
        val time: TextView = itemView.tvTime
        val cardview:MaterialCardView = itemView.cvCardViewForYou
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

    private fun getDate(date: Long): String {
        val timeFormatter = SimpleDateFormat("dd.MM.yyyy")
        return timeFormatter.format(Date(date * 1000L))
    }

}

