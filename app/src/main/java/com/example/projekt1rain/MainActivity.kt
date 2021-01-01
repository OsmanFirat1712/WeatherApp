package com.example.projekt1rain

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.projekt1rain.DataStorag.DataClass
import com.example.projekt1rain.DataStorag.DataService
import com.example.projekt1rain.Fbiragments.MapViewFragment
import com.example.projekt1rain.Fragments.ForYouFragment
import com.example.projekt1rain.Fragments.SettingsFragment
import com.example.projekt1rain.Room.City
import com.example.projekt1rain.Room.LocalJSONParser
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity() : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dataService: DataService = (getApplication() as MyApp).dataService


        //GET JSON
        val jsonFileString =
            LocalJSONParser.getJsonDataFromAsset(applicationContext, "citylist.json")
        Log.i("data", jsonFileString.toString())
        val gson = Gson()
        //PARSE JSON TO STRING
        val listPersonType = object : TypeToken<List<City>>() {}.type
        //val city: List<City> = gson.fromJson(jsonFileString, listPersonType)
        //city.forEachIndexed { idx, city -> Log.i("data", "> Item $idx:\n$city") }
        setToolbar()

        findViewById<BottomNavigationView>(R.id.nav_view)
        val foryoufragment = ForYouFragment()
        val mapViewFragment = MapViewFragment()
        val settingsFragment = SettingsFragment()
        makeCurrentFragment(foryoufragment)
        /*  val navController: NavController = findNavController(R.id.nav_graph)
          findViewById<BottomNavigationView>(R.id.nav_view)
                  .setupWithNavController(navController)*/
        nav_view.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.forYouFragment -> makeCurrentFragment(foryoufragment)
                R.id.mapViewFragment -> makeCurrentFragment(mapViewFragment)
                R.id.settingsFragment -> makeCurrentFragment(settingsFragment)
            }
            true
        }

        //dataService.getCurrentCallFromApi()
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


}


