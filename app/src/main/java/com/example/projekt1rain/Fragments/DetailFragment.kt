package com.example.projekt1rain.Fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.projekt1rain.MainActivity
import com.example.projekt1rain.MyBarChartConverter
import com.example.projekt1rain.R
import com.example.projekt1rain.Room.Favorites
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import kotlinx.android.synthetic.main.detailviewfragment.*
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
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

        (activity as AppCompatActivity?)?.supportActionBar?.setTitle("Detailansicht")
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            (activity as AppCompatActivity?)?.supportActionBar?.setHomeButtonEnabled(true)
        setHasOptionsMenu(true)

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



        tvDetailIcon = view.findViewById(R.id.tvDetailIcon)



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

            cl = view.findViewById(R.id.constraint)
            chart = view.findViewById(R.id.bcDetailBarchart)


            favorites?.currentWeatherResponse?.daily?.forEachIndexed { index, daily ->
                if (index < 7) {
                    val temp = daily.temp.day.toInt().minus(273.15.toInt().toString().toFloat())
                    list2.add(BarEntry(index.toFloat(), temp.toFloat()))

                }
            }

            val barEntry = list2.map { BarEntry(it.x, it.y) }
            dataset = BarDataSet(barEntry, "Temperatur Next Days")
            dataset.color = Color.WHITE
            dataset.barBorderColor = Color.WHITE
            dataset.valueTextColor = Color.WHITE
            dataset.valueTextSize = 10f
            chart.description.text = ""
            chart.legend.isEnabled = true
            chart.invalidate()
            chart.axisRight.isEnabled = false
            chart.axisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
            chart.axisRight.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
            val xAxis = chart.xAxis
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
            xAxis.labelCount = 6
            xAxis.granularity = 1f
            xAxis.isGranularityEnabled = true

/*
            xAxis.valueFormatter = object : ValueFormatter() {
                private val mFormat: SimpleDateFormat = SimpleDateFormat("dd MMM", Locale.GERMAN)
                override fun getFormattedValue(value: Float): String {
                    val millis = value.toLong() * 1000L
                    return mFormat.format(Date(millis))
                }
            }*/


            chart.xAxis.valueFormatter = MyBarChartConverter()
            chart.data = BarData(dataset)

            /********************** detailpage connection ***************************/


            tvDetailTemp = view.findViewById(R.id.tvDetailTemp)
            tvDetailUvi = view.findViewById(R.id.tvDetailUvi)
            tvDetailWindDeg = view.findViewById(R.id.tvDetailWindDeg)
            tvDetailWindSpeed = view.findViewById(R.id.tvDetailWindSpeed)
            tvDetailClouds = view.findViewById(R.id.tvDetailClouds)
            tvDetailVisibility = view.findViewById(R.id.tvDetailVisibility)
            tvAddress = view.findViewById(R.id.tvAddress)




            tvAddress.text= favorites?.address.toString()

            tvDetailTemp.text = favorites?.currentWeatherResponse?.current?.temp?.toInt()?.minus(273.15.toInt()).toString() + "°C"
            tvDetailClouds.text =favorites?.currentWeatherResponse?.current?.clouds?.toString() + "%"
            tvDetailVisibility.text =favorites?.currentWeatherResponse?.current?.visibility?.toString() + "km"
            tvDetailUvi.text = favorites?.currentWeatherResponse?.current?.uvi?.toString()
            tvDetailWindSpeed.text = favorites?.currentWeatherResponse?.current?.windSpeed?.toString() + "m/s"
            tvDetailWindDeg.text = favorites?.currentWeatherResponse?.current?.windDeg?.toString() + "m/s"



        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            activity?.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}
