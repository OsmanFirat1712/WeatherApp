package com.example.projekt1rain.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.projekt1rain.Adapter.ForYouAdapter
import com.example.projekt1rain.Fbiragments.MapViewFragment
import com.example.projekt1rain.MainActivity
import com.example.projekt1rain.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ForYouFragment : Fragment() {

    private lateinit var foryouadapter: ForYouAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolbar()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.recylerviewforyou1, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerviewforyou)
/*        foryouadapter = ForYouAdapter(content = ArrayList(), requireContext())*/
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            //adapter = foryouadapter
            setHasFixedSize(true)
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

}




