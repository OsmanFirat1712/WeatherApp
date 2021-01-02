package com.example.projekt1rain

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.projekt1rain.DataStorag.DataService
import com.example.projekt1rain.Fbiragments.MapViewFragment
import com.example.projekt1rain.Fragments.ForYouFragment
import com.example.projekt1rain.Fragments.SettingsFragment
import com.example.projekt1rain.Room.City
import com.example.projekt1rain.Room.LocalJSONParser
import com.example.projekt1rain.Room.WeatherDatabase
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.security.AccessController.getContext

class MainActivity() : AppCompatActivity() {
    private lateinit var dataService: DataService
    private lateinit var database: WeatherDatabase
    private lateinit var cities: List<City>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dataService: DataService = (getApplication() as MyApp).dataService
        setToolbar()
        /* weatherDatabase.weatherDao.getLiveDataCityList(cities)*/
        val jsonFileString =
            LocalJSONParser.getJsonDataFromAsset(applicationContext, "citylist.json")
        Log.i("data", jsonFileString.toString())
        val gson = Gson()
        //PARSE JSON TO STRING
        val listPersonType = object : TypeToken<List<City>>() {}.type
        val city: List<City> = gson.fromJson(jsonFileString, listPersonType)
        city.forEachIndexed { idx, city -> Log.i("data", "> Item $idx:\n$city") }
        dataService.saveCities(city)

        val nav = findViewById<BottomNavigationView>(R.id.nav_view)
        val foryoufragment = ForYouFragment()
        val mapViewFragment = MapViewFragment()
        val settingsFragment = SettingsFragment()
        makeCurrentFragment(foryoufragment)
        /*  val navController: NavController = findNavController(R.id.nav_graph)
          findViewById<BottomNavigationView>(R.id.nav_view)
                  .setupWithNavController(navController)*/
        nav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.forYouFragment -> makeCurrentFragment(foryoufragment)
                R.id.mapViewFragment -> makeCurrentFragment(mapViewFragment)
                R.id.settingsFragment -> makeCurrentFragment(settingsFragment)
            }
            true
        }
    }

    private fun setToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.let {

        }

    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, fragment)
            commit()

        }



    override fun onResume() {
        super.onResume()
    }
}


