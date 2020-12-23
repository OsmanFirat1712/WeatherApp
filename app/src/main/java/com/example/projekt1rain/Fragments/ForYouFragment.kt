package com.example.projekt1rain.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.projekt1rain.Adapter.ForYouAdapter
//import com.example.projekt1rain.Adapter.ForYouAdapter
import com.example.projekt1rain.Fbiragments.MapViewFragment
import com.example.projekt1rain.MainActivity
import com.example.projekt1rain.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.foryoufragment.*
import kotlinx.android.synthetic.main.recylerviewforyou1.*

class ForYouFragment : Fragment() {

    //    private lateinit var foryouadapter: ForYouAdapter

    private lateinit var foryouRecyclerView: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var foryoucardview: CardView
    private lateinit var adapter: ForYouAdapter
    private lateinit var listView: ListView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolbar()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.recylerviewforyou1, container, false)
        foryouRecyclerView = view.findViewById(R.id.recyclerviewforyou)
        //layoutManager = LinearLayoutManager(activity.,RecyclerView.VERTICAL,false)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //       foryouadapter = ForYouAdapter(requireContext())
        foryouRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            //  adapter = foryouadapter
            setHasFixedSize(true)

//            foryoucardview.setOnClickListener { startForYouFragment() }
        }
/*

        adapter2 = ForYouAdapter(requireContext())

        view.findViewById<RecyclerView>(R.id.recylerviewforyou).apply {

            layoutManager = LinearLayoutManager(context)
            adapter = adapter2
            adapter = ForYouAdapter(requireContext())
            setHasFixedSize(true)
        }
*/

        /* val swipeRefresh = view.findViewById<SwipeRefreshLayout>(R.id.swipe_refresh)
         swipeRefresh?.setOnRefreshListener {
             swipeRefresh.isRefreshing = false
         }*/

        val flba: FloatingActionButton = view.findViewById(R.id.flab)
        flba.setOnClickListener() { startMapViewFragment() }
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

    /*  private fun initRecyclerVIew() {
          foryouRecyclerView = findViewByID(R.id.)
          adapter=ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1)
          listView.foryouadaptar = adapter
      }
      private fun initDataweather()
      content = DataWeatherClass*/
}




