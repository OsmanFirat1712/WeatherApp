package com.example.projekt1rain.Fbiragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.projekt1rain.MainActivity
import com.example.projekt1rain.R

class MapViewFragment: Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolbar()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.mapviewfragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun setToolbar(){
        val actionBar : ActionBar? =(requireActivity() as MainActivity).supportActionBar
        actionBar?.apply {
            title = getString(R.string.Kartenansicht)
            setDisplayShowTitleEnabled(true)
            setHomeButtonEnabled(true)
        }
    }
}