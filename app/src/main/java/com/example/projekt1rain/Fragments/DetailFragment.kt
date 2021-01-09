
package com.example.projekt1rain.Fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.projekt1rain.MyXAxisFormatter
import com.example.projekt1rain.R
import com.example.projekt1rain.Room.Favorites
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import kotlinx.android.synthetic.main.detailviewfragment.*
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class DetailFragment: Fragment() {

    private lateinit var tvDetailWindDeg: TextView
    private lateinit var tvDetailUvi: TextView
    private lateinit var tvDetailWindSpeed: TextView
    private lateinit var tvDetailTemp: TextView
    private lateinit var tvDetailClouds: TextView
    private lateinit var tvDetailVisibility: TextView
    private lateinit var tvDetailIcon: ImageView
    private lateinit var testTime: TextView

    private lateinit var cl: ConstraintLayout
    private lateinit var chart: BarChart
    private lateinit var dataset: BarDataSet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detailviewfragment, container, false)

    }

    private val list = mutableListOf(
        Entry(0.0f, 10.0f),
        Entry(1.0f, 15.0f),
        Entry(2.0f, 4.0f),
        Entry(3.0f, 7.0f),
        Entry(4.0f, 20.0f),
        Entry(5.0f, 20.0f),
        Entry(6.0f, 20.0f),
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /************************************* for time day and night icons ***************************************/
        val CurrentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH.mm"))
        tvDetailIcon = view.findViewById(R.id.tvDetailIcon)
        testTime = view.findViewById(R.id.testTime)
        testTime.text = CurrentTime


        val currentTimeAsFloat = CurrentTime.toDouble()

        if (18.00 < currentTimeAsFloat && currentTimeAsFloat < 24.00) {
            constraint.setBackgroundColor(Color.parseColor("#34495e"))
            tvDetailIcon.setImageResource(R.drawable.ic_baseline_nights_stay_24) }

        else if (0.00 < currentTimeAsFloat && currentTimeAsFloat < 6.00){
            constraint.setBackgroundColor(Color.parseColor("#34495e"))
            tvDetailIcon.setImageResource(R.drawable.ic_baseline_nights_stay_24)
        }

        else {
            constraint.setBackgroundColor(Color.parseColor("#349Bdb"))
            tvDetailIcon.setImageResource(R.drawable.ic_baseline_wb_sunny_72)

        }

        /********************** detailpage graph ***************************/
        val bundle = arguments
        if (bundle != null) {
            val favorites: Favorites? = bundle.getSerializable("weatherkey") as Favorites?

            val temp = favorites?.currentWeatherResponse?.daily?.get(3)?.temp



            //val list = mutableListOf<Entry>()
            val xValsDateLabel = ArrayList<String>()
            val xValsOriginalMillis = ArrayList<Long>()

            cl = view.findViewById(R.id.constraint)
            chart = view.findViewById(R.id.bcDetailBarchart)
            val barEntry = list.map { BarEntry(it.x, it.y, xValsDateLabel) }
            dataset = BarDataSet(barEntry, "Temperatur Next Days")

            favorites?.currentWeatherResponse?.daily?.forEachIndexed { index, daily ->
                if (index < 12) {
                    val temp = daily.temp.toInt().minus(273.15.toInt().toString().toFloat())
                    list.add(Entry(index.toFloat(), temp))
                    xValsOriginalMillis.add(daily.dt)
                }
            }



            dataset.color = Color.RED

            //dataset.fillAlpha = 5000
            //dataset.mode = LineDataSet.Mode.CUBIC_BEZIER


            chart.description.text = ""
            chart.legend.isEnabled = true
            chart.invalidate()
            chart.axisRight.isEnabled = false
            chart.axisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
            chart.axisRight.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)

            val xAxis = chart.xAxis
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
            xAxis.labelCount = 7
            xAxis.granularity = 1f
            xAxis.isGranularityEnabled = true

            chart.data = BarData(dataset)
            chart.xAxis.valueFormatter = (MyXAxisFormatter.MyValueFormatter(xValsDateLabel))


            /********************** detailpage connection ***************************/


            tvDetailTemp = view.findViewById(R.id.tvDetailTemp)
            tvDetailUvi = view.findViewById(R.id.tvDetailUvi)
            tvDetailWindDeg = view.findViewById(R.id.tvDetailWindDeg)
            tvDetailWindSpeed = view.findViewById(R.id.tvDetailWindSpeed)
            tvDetailClouds = view.findViewById(R.id.tvDetailClouds)
            tvDetailVisibility = view.findViewById(R.id.tvDetailVisibility)




            tvDetailTemp.text =
                favorites?.currentWeatherResponse?.current?.temp?.toInt()?.minus(273.15.toInt())
                    .toString() + "°C"
            tvDetailClouds.text =
                "Clouds :" + favorites?.currentWeatherResponse?.current?.clouds?.toString() + "%"
            tvDetailVisibility.text =
                "Visibility :" + favorites?.currentWeatherResponse?.current?.visibility?.toString()
            tvDetailUvi.text = "Uvi :" + favorites?.currentWeatherResponse?.current?.uvi?.toString()
            tvDetailWindSpeed.text =
                "WindSpeed :" + favorites?.currentWeatherResponse?.current?.windSpeed?.toString()
            tvDetailWindDeg.text =
                "Wind Deg : " + favorites?.currentWeatherResponse?.current?.windDeg?.toString()


        }
    }

}
