package com.example.projekt1rain.Adapter


import android.app.Person
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projekt1rain.DataWeatherClass
import com.example.projekt1rain.Fragments.MapViewFragment
import com.example.projekt1rain.MyXAxisFormatter
import com.example.projekt1rain.R
import com.example.projekt1rain.Room.Favorites
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.foryoufragment.view.*
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


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

            val list = mutableListOf(
                Entry(0.0F, 0.0F),
                Entry(2f, 0.0f),
                Entry(3f, 0.0f),
                Entry(4F,curentItem.currentWeatherResponse?.main?.temp?.toInt()?.minus(273.15.toInt()).toString().toFloat()
                )

            )






            val xValsDateLabel = ArrayList<String>()

            val xValsOriginalMillis = ArrayList<Long>()
            xValsOriginalMillis.add(1555275494836L)
            xValsOriginalMillis.add(1585578525900L)
            xValsOriginalMillis.add(1596679626245L)
            xValsOriginalMillis.add(1609990727820L)

            for (i in xValsOriginalMillis) {
                val mm = i / 60 % 60
                val hh = i / (60 * 60) % 24
                val mDateTime = "$hh:$mm"
                xValsDateLabel.add(mDateTime)
            }

            val cl : ConstraintLayout = itemView.findViewById(R.id.constraint)
            val chart: LineChart = itemView.findViewById(R.id.chChart)
            val barEntries = list.map{Entry(it.x, it.y,xValsDateLabel)}
            val dataSet = LineDataSet(barEntries, "BUGEL")
            dataSet.fillAlpha =110
            dataSet.color = Color.RED
            dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
            chart.description.text = ""
            chart.legend.isEnabled = false
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

            xAxis.valueFormatter = (MyXAxisFormatter.MyValueFormatter(xValsDateLabel))


       /*     val xLabel = ArrayList<String>()
            val calendar = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("dd-MMM-yyyy")

            for (i in 0..50) {
                calendar.add(Calendar.DAY_OF_YEAR, i)
                val date = calendar.time
                val txtDate = dateFormat.format(date)

                xLabel.add(txtDate)
            }
*/


            // or use some other logic to save your data in list. For ex.
        /*    var i = 1
            while (i < 50) {
                xLabel.add("" + 3 * i)
                i += 2
            }


            val xAxis = mChart.xAxis
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
            xAxis.valueFormatter = IAxisValueFormatter { value, axis -> xLabel[value.toInt()] }
*/



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


