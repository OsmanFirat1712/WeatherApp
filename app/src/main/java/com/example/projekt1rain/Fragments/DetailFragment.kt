package com.example.projekt1rain.Fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import kotlin.collections.ArrayList
import kotlin.time.ExperimentalTime

class DetailFragment : Fragment() {

    private lateinit var tvDetailWindDeg: TextView
    private lateinit var tvDetailUvi: TextView
    private lateinit var tvDetailWindSpeed: TextView
    private lateinit var tvDetailTemp: TextView
    private lateinit var tvDetailClouds: TextView
    private lateinit var tvAddress:TextView
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


    @ExperimentalTime
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /************************************* for time day and night icons ***************************************/
        val CurrentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH.mm"))
        val CurrentTime2 = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))


        tvDetailIcon = view.findViewById(R.id.tvDetailIcon)
        testTime = view.findViewById(R.id.testTime)
        testTime.text = CurrentTime2


        val currentTimeAsFloat = CurrentTime.toDouble()

        if (18.00 < currentTimeAsFloat && currentTimeAsFloat < 24.00) {
            constraint.setBackgroundColor(Color.parseColor("#34495e"))
            tvDetailIcon.setImageResource(R.drawable.ic_baseline_nights_stay_24)
        } else if (0.00 < currentTimeAsFloat && currentTimeAsFloat < 6.00) {
            constraint.setBackgroundColor(Color.parseColor("#34495e"))
            tvDetailIcon.setImageResource(R.drawable.ic_baseline_nights_stay_24)
        } else {
            constraint.setBackgroundColor(Color.parseColor("#349Bdb"))
            tvDetailIcon.setImageResource(R.drawable.ic_baseline_wb_sunny_72)

        }

        /********************** detailpage graph ***************************/
        val bundle = arguments
        if (bundle != null) {
            val favorites: Favorites? = bundle.getSerializable("weatherkey") as Favorites?

            val calendar: Calendar = Calendar.getInstance()
            val dayOfWeeks: Int = calendar.get(Calendar.DAY_OF_WEEK)
            Log.i("lol", "${dayOfWeeks} hier")

            val CurrentDay = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
            val fullCurentDay =
                CurrentDay.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG))
            Log.i("CurrentDay :", "${fullCurentDay} hier")


            val list2 = mutableListOf<BarEntry>()
            val xValsDateLabel2 = ArrayList<String>()
            val xValsOriginalMillis2 = ArrayList<Long>()

            cl = view.findViewById(R.id.constraint)
            chart = view.findViewById(R.id.bcDetailBarchart)

            favorites?.currentWeatherResponse?.daily?.forEachIndexed { index, daily ->
                if (index < 48) {
                    val temp = daily.temp.max.toInt().minus(273.15.toInt().toString().toFloat())
                    list2.add(BarEntry(index.toFloat(), temp.toFloat()))
                    xValsOriginalMillis2.add(daily.dt)
                }
            }


            val barEntry = list2.map { BarEntry(it.x, it.y) }

            dataset = BarDataSet(barEntry, "Temperatur Next Days")


            for (i in xValsOriginalMillis2) {
                val mm = i / 60 % 60
                val hh = i / (60 * 60) % 24
                val mDateTime = "$hh:$mm "
                xValsDateLabel2.add(mDateTime)
            }

            for (i in xValsOriginalMillis2) {
                val mDateTime = "${dayOfWeeks}"
                xValsDateLabel2.add(mDateTime)
            }



            dataset.color = Color.RED



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
            chart.xAxis.valueFormatter = (MyXAxisFormatter.MyValueFormatter(xValsDateLabel2))


            /********************** detailpage connection ***************************/


            tvDetailTemp = view.findViewById(R.id.tvDetailTemp)
            tvDetailUvi = view.findViewById(R.id.tvDetailUvi)
            tvDetailWindDeg = view.findViewById(R.id.tvDetailWindDeg)
            tvDetailWindSpeed = view.findViewById(R.id.tvDetailWindSpeed)
            tvDetailClouds = view.findViewById(R.id.tvDetailClouds)
            tvDetailVisibility = view.findViewById(R.id.tvDetailVisibility)
            tvAddress = view.findViewById(R.id.tvAddress)




            tvAddress.text=
               favorites?.address.toString()

            tvDetailTemp.text =
                favorites?.currentWeatherResponse?.current?.temp?.toInt()?.minus(273.15.toInt())
                    .toString() + "°C"
            tvDetailClouds.text =
                getString(R.string.wolken) + favorites?.currentWeatherResponse?.current?.clouds?.toString() + "%"
            tvDetailVisibility.text =
                getString(R.string.sicht) + favorites?.currentWeatherResponse?.current?.visibility?.toString() + "km"
            tvDetailUvi.text =
                getString(R.string.uvi) + favorites?.currentWeatherResponse?.current?.uvi?.toString()
            tvDetailWindSpeed.text =
                getString(R.string.windgesch) + favorites?.currentWeatherResponse?.current?.windSpeed?.toString() + "m/s"
            tvDetailWindDeg.text =
                getString(R.string.windtemp) + favorites?.currentWeatherResponse?.current?.windDeg?.toString() + "m/s"



        }
    }
}
