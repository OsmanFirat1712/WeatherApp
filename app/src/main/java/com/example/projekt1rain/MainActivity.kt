package com.example.projekt1rain

import android.Manifest
import android.Manifest.permission
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.projekt1rain.DataStorag.DataService
import com.example.projekt1rain.Fragments.MapViewFragment
import com.example.projekt1rain.Fragments.ForYouFragment
import com.example.projekt1rain.Fragments.SettingsFragment
import com.example.projekt1rain.Room.City
import com.example.projekt1rain.Room.LocalJSONParser
import com.example.projekt1rain.Room.WeatherDatabase
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.concurrent.Executors


class MainActivity() : AppCompatActivity() {
    private lateinit var dataService: DataService
    private lateinit var database: WeatherDatabase
    private lateinit var cities: List<City>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dataService: DataService = (application as MyApp).dataService
        setToolbar()
        /* weatherDatabase.weatherDao.getLiveDataCityList(cities)*/
        val jsonFileString =
            LocalJSONParser.getJsonDataFromAsset(applicationContext, "citylist.json")
        val gson = Gson()
        //PARSE JSON TO STRING
        val listPersonType = object : TypeToken<List<City>>() {}.type
        val cities: List<City> = gson.fromJson(jsonFileString, listPersonType)
        Executors.newSingleThreadExecutor().execute { dataService.saveCities(cities) }

        //val city: List<City> = gson.fromJson(jsonFileString, listPersonType)
        //city.forEachIndexed { idx, city -> Log.i("data", "> Item $idx:\n$city") }
        setToolbar()

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

    private fun makeCurrentFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, fragment)
            commit()

        }

    override fun onResume() {
        super.onResume()
    }



}


