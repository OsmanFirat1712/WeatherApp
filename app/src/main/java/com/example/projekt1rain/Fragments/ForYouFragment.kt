package com.example.projekt1rain.Fragments

//import com.example.projekt1rain.Adapter.ForYouAdapter
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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

class ForYouFragment : Fragment(),CallBack,FragmentCallBack {
    var recyclerViewAdapter: ForYouAdapter? = null
    private lateinit var foryouRecyclerView: RecyclerView
    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var foryoucardview: CardView
    private lateinit var adapter3: ForYouAdapter
    private lateinit var listView: ListView
    private lateinit var retrofitsetup: RetrofitSetup


    val FINE_LOCATION_REQUEST = 101


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolbar()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.recylerviewforyou1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataService: DataService = (requireActivity().application as MyApp).dataService

        adapter3 = ForYouAdapter(
                forYouConstruktorList = ArrayList(),
                requireContext(),this
        )

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerviewforyou)
        recyclerView.apply {
            adapter = adapter3
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)

        }
        dataService.getFavorites(this)
        adapter3.notifyDataSetChanged()

        val flba: FloatingActionButton = view.findViewById(R.id.flab)
        flba.setOnClickListener() {


            checkForPermissions(android.Manifest.permission.ACCESS_FINE_LOCATION, "location", FINE_LOCATION_REQUEST
            )
            startMapViewFragment()
        }
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
/*
    private fun startForYouFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, DetailFragment())
                .addToBackStack(null)
                .commit()
    }*/

    override fun onComplete(favorites: List<Favorites>) {
        requireActivity().runOnUiThread(java.lang.Runnable {
            adapter3.updateforyou(favorites)
            adapter3.notifyDataSetChanged()

        })
    }

    private fun checkForPermissions(permission: String, name: String, requestCode: Int): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED -> {
                    Toast.makeText(requireContext(), "$name permission granted", Toast.LENGTH_LONG).show()
                }
                shouldShowRequestPermissionRationale(permission) -> showDialog(
                        permission,
                        name,
                        requestCode
                )
                else -> ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(permission),
                        requestCode
                )
            }
        }
        return false
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        fun innerCheck(name: String) {
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "$name permission refused", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "$name permission granted", Toast.LENGTH_LONG).show()
            }
        }
        when (requestCode) {
            FINE_LOCATION_REQUEST -> innerCheck("location")
        }
    }

    private fun showDialog(permission: String, name: String, requestCode: Int) {
        val builder = AlertDialog.Builder(requireContext())

        builder.apply {
            setMessage("Permission to Access your $name is required to use this app")
            setTitle("Permission required")
            setPositiveButton("OK") { dialog, which ->
                ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(permission),
                        requestCode
                )

            }
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun onCall(favorites: Favorites) {

        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        val blankFragment_detailPage = DetailFragment()
        val bundle = Bundle()
        bundle.putSerializable("weatherkey", favorites)
        blankFragment_detailPage.setArguments(bundle)
        transaction.replace(R.id.container, blankFragment_detailPage)
        transaction.commit()
    }

    }





