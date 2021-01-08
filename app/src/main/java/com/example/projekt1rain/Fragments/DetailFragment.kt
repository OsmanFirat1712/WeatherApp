/*
package com.example.projekt1rain.Fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.projekt1rain.R
import com.example.projekt1rain.Room.Favorites
import com.example.projekt1rain.MyXAxisFormatter2
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class DetailFragment: Fragment() {

    private lateinit var tvDetailPressure:TextView
    private lateinit var tvDetailFeelsLike:TextView
    private lateinit var tvDetailHumidity:TextView
    private lateinit var tvDetailTemp:TextView
    private lateinit var tvDetailTempMax:TextView
    private lateinit var tvDetailTempMin:TextView

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        */
/********************** detailpage connection ***************************//*


        val bundle = arguments
        if (bundle != null) { val favorites: Favorites? = bundle.getSerializable("weatherkey") as Favorites?

            tvDetailTemp = view.findViewById(R.id.tvDetailTemp)
            tvDetailFeelsLike = view.findViewById(R.id.tvDetailFeelsLike)
            tvDetailPressure = view.findViewById(R.id.tvDetailPressure)
            tvDetailHumidity = view.findViewById(R.id.tvDetailHumidity)
            tvDetailTempMax = view.findViewById(R.id.tvDetailTempMax)
            tvDetailTempMin = view.findViewById(R.id.tvDetailTempMin)

            tvDetailTemp.text = favorites?.currentWeatherResponse?.main?.temp?.toInt()?.minus(273.15.toInt()).toString() + "°C"
            tvDetailTempMax.text = favorites?.currentWeatherResponse?.main?.tempMax?.toInt()?.minus(273.15.toInt()).toString() + "°C"
            tvDetailTempMin.text = favorites?.currentWeatherResponse?.main?.tempMin?.toInt()?.minus(273.15.toInt()).toString() + "°C"
            tvDetailFeelsLike.text = favorites?.currentWeatherResponse?.main?.feelsLike?.toInt()?.toString()
            tvDetailHumidity.text = favorites?.currentWeatherResponse?.main?.humidity?.toString()
            tvDetailPressure.text = favorites?.currentWeatherResponse?.main?.pressure?.toString()
        }
    }
}*/
