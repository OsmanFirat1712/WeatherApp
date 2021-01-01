package com.example.projekt1rain.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projekt1rain.Adapter.ForYouAdapter
import com.example.projekt1rain.DataWeatherClass
//import com.example.projekt1rain.Adapter.ForYouAdapter
import com.example.projekt1rain.Fbiragments.MapViewFragment
import com.example.projekt1rain.ForYouConstruktor
import com.example.projekt1rain.MainActivity
import com.example.projekt1rain.R
import com.example.projekt1rain.RetrofitApi.RetrofitSetup
import com.example.projekt1rain.RetrofitApi.WeatherLocation
import com.example.projekt1rain.RetrofitApi.retrofitResponse
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.recylerviewforyou1.*
import kotlinx.android.synthetic.main.recylerviewforyou1.view.*
import javax.security.auth.callback.Callback

class ForYouFragment : Fragment() {
    
    private lateinit var foryouRecyclerView: RecyclerView
    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var foryoucardview: CardView
    private lateinit var adapter: ForYouAdapter
    private lateinit var listView: ListView
    private lateinit var retrofitsetup: RetrofitSetup




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolbar()
        //you can call the retrofit response
        //retrofitResponse()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.recylerviewforyou1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerviewforyou.apply {
            val forYouConstruktorList = adapterGenerate(20)
            adapter =ForYouAdapter(forYouConstruktorList)
            layoutManager = LinearLayoutManager(activity)
            recyclerviewforyou.setHasFixedSize(true)

        }

        val flba: FloatingActionButton = view.findViewById(R.id.flab)
        flba.setOnClickListener() { startMapViewFragment() }
    }

    private fun adapterGenerate(size : Int) : List<ForYouConstruktor>{
        val list = ArrayList<ForYouConstruktor>()

        val item = ForYouConstruktor("adana", 22,22,R.drawable.ic_baseline_settings_24,R.drawable.ic_baseline_add_location_alt_24)

        list += item

        return list
    }

    private fun setToolbar() {
        val actionBar: androidx.appcompat.app.ActionBar? =
            (requireActivity() as MainActivity).supportActionBar
        actionBar?.apply {
            title = (getString(R.string.foryou))
            setDisplayShowTitleEnabled(true)
        }
    }

    private fun startMapViewFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, MapViewFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun startForYouFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, DetailFragment())
            .addToBackStack(null)
            .commit()
    }

}




