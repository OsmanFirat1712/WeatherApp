package com.example.projekt1rain.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.projekt1rain.MainActivity
import com.example.projekt1rain.R

class SettingsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolbar()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.settingsfragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    private fun setToolbar() {
        val actionBar: androidx.appcompat.app.ActionBar? =
            (requireActivity() as MainActivity).supportActionBar
        actionBar?.apply {
            title = (getString(R.string.Einstellungen))
            setDisplayShowTitleEnabled(true)
        }
    }

}