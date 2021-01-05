package com.example.projekt1rain.Fragments

//import com.example.projekt1rain.Adapter.ForYouAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projekt1rain.*
import com.example.projekt1rain.Adapter.ForYouAdapter
import com.example.projekt1rain.DataStorag.DataService
import com.example.projekt1rain.InterFaces.CallBack
import com.example.projekt1rain.RetrofitApi.RetrofitSetup
import com.example.projekt1rain.Room.Favorites
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ForYouFragment : Fragment(),CallBack {
        var recyclerViewAdapter: ForYouAdapter?=null
    private lateinit var foryouRecyclerView: RecyclerView
    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var foryoucardview: CardView
    private lateinit var adapter3: ForYouAdapter
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
        val dataService: DataService = (requireActivity().application as MyApp).dataService

        adapter3 = ForYouAdapter(forYouConstruktorList = ArrayList(),requireContext())

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerviewforyou)
        recyclerView.apply {
            adapter = adapter3
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)

        }
        dataService.getFavorites(this)
        adapter3.notifyDataSetChanged()

        val flba: FloatingActionButton = view.findViewById(R.id.flab)
        flba.setOnClickListener() { startMapViewFragment() }



    }



 /*   private fun adapterGenerate(size: Int, city: City, currentWeather: CurrentWeather): List<Favorites> {
        val list = ArrayList<Favorites>()

        val item = Favorites(0, C, "")

        list += item

        return list
    }*/


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

    override fun onComplete(favorites: List<Favorites>) {
        requireActivity().runOnUiThread(java.lang.Runnable {
            adapter3.updateforyou(favorites)
            adapter3.notifyDataSetChanged()

        })
    }

}




