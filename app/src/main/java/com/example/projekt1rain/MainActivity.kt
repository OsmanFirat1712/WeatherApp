package com.example.projekt1rain

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.projekt1rain.Fragments.ForYouFragment
import com.example.projekt1rain.Fragments.MapViewFragment
import com.example.projekt1rain.Fragments.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setToolbar()

        findViewById<BottomNavigationView>(R.id.nav_view)
        val foryoufragment = ForYouFragment()
        val mapViewFragment = MapViewFragment()
        val settingsFragment = SettingsFragment()
        makeCurrentFragment(foryoufragment)
      val navController: NavController = findNavController(R.id.nav_graph)
        findViewById<BottomNavigationView>(R.id.nav_view)
                .setupWithNavController(navController)
        nav_view.setOnNavigationItemSelectedListener {
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

}


