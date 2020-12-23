package com.example.projekt1rain

import com.github.mikephil.charting.data.Entry

data class DataWeatherClass(
    private val weatherData: MutableList<Entry> = mutableListOf(
        Entry(0.0f, 10.0f),
        Entry(1.0f, 15.0f),
        Entry(2.0f, 4.0f),
        Entry(3.0f, 7.0f),
        Entry(4.0f, 20.0f),
    )
)

