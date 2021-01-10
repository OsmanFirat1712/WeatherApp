package com.example.projekt1rain.Fragments

//import com.example.projekt1rain.Adapter.ForYouAdapter
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.projekt1rain.*
import com.example.projekt1rain.Adapter.ForYouAdapter
import com.example.projekt1rain.DataStorag.DataService
import com.example.projekt1rain.InterFaces.CallBack
import com.example.projekt1rain.InterFaces.RemoveCallBack
import com.example.projekt1rain.RetrofitApi.retrofitOneCallResponse
import com.example.projekt1rain.RetrofitApi.retrofitOneCallrefreshResponse
import com.example.projekt1rain.Room.Favorites
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.concurrent.Executors

class ForYouFragment() : Fragment(), CallBack, FragmentCallBack, RemoveCallBack {
    var recyclerViewAdapter: ForYouAdapter? = null
    private lateinit var forYouAdapter: ForYouAdapter
    private var favorites: List<Favorites> = ArrayList()
    val FINE_LOCATION_REQUEST = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolbar()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.recylerviewforyou1, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*
        val CurrentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH.mm"))
        val currentTimeAsFloat = CurrentTime.toDouble()
        if (18.00 < currentTimeAsFloat && currentTimeAsFloat < 24.00) {
            forYouLayout.setBackgroundColor(Color.parseColor("#34495e"))
        }
        else if (0.00 < currentTimeAsFloat && currentTimeAsFloat < 6.00){
            constraint.setBackgroundColor(Color.parseColor("#34495e"))

        }
        else {
            constraint.setBackgroundColor(Color.parseColor("#349Bdb"))

        }*/



        val dataService: DataService = (requireActivity().application as MyApp).dataService

        val swipeRefresh = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)
        swipeRefresh.setOnRefreshListener {
            getOnecall()

            Toast.makeText(requireContext(), getString(R.string.refresh), Toast.LENGTH_LONG)
                .show()
            swipeRefresh.isRefreshing = false
        }
        forYouAdapter = ForYouAdapter(

            forYouConstruktorList = ArrayList(), requireContext(),
            this,this
        )


        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerviewforyou)
        recyclerView.apply {
            adapter = forYouAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)

        }
        dataService.getFavorites(this)

        forYouAdapter.notifyDataSetChanged()

        val flba: FloatingActionButton = view.findViewById(R.id.flab)
        flba.setOnClickListener() {

            checkForPermissions(
                android.Manifest.permission.ACCESS_FINE_LOCATION, "location", FINE_LOCATION_REQUEST
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

    private fun startForYouFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, DetailFragment())
                .addToBackStack(null)
                .commit()
    }


    private fun getOnecall() {
        val dataService: DataService = (requireActivity().application as MyApp).dataService
        dataService.getFavorites(this)
        favorites.forEach { favorite ->
            val lat = favorite.currentWeatherResponse?.lat
            val lon = favorite.currentWeatherResponse?.lon
            val adress = favorite.address
            retrofitOneCallResponse(lat!!, lon!!, adress)
            forYouAdapter.notifyDataSetChanged()

        }
    }

    override fun onComplete(favorites: List<Favorites>) {
        requireActivity().runOnUiThread(java.lang.Runnable {
            forYouAdapter.updateforyou(favorites)
            forYouAdapter.notifyDataSetChanged()

            favorites.forEach { favorite ->
                val lat = favorite.currentWeatherResponse?.lat
                val lon = favorite.currentWeatherResponse?.lon
                val adress = favorite.address
                retrofitOneCallrefreshResponse(lat!!, lon!!, adress)
                forYouAdapter.updateforyou(favorites)
                forYouAdapter.notifyDataSetChanged()

            }
        })
        Toast.makeText(requireContext(), getString(R.string.verbindunghergestellt), Toast.LENGTH_LONG).show()

    }

    private fun checkForPermissions(permission: String, name: String, requestCode: Int): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    permission
                ) == PackageManager.PERMISSION_GRANTED -> {
                    Toast.makeText(requireContext(), "$name permission granted", Toast.LENGTH_LONG)
                        .show()
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        fun innerCheck(name: String) {
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "$name permission refused", Toast.LENGTH_LONG)
                    .show()
            } else {
                Toast.makeText(requireContext(), "$name permission granted", Toast.LENGTH_LONG)
                    .show()
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
        val blankFragmentDetailPage = DetailFragment()
        val bundle = Bundle()
        bundle.putSerializable("weatherkey", favorites)
        blankFragmentDetailPage.setArguments(bundle)
        transaction.replace(R.id.container, blankFragmentDetailPage)
        transaction.commit()
    }

/*     fun delete(favorites:Favorites) {
         val dataService: DataService = (requireActivity().application as MyApp).dataService
         AlertDialog.Builder(context)
                .setNeutralButton(R.string.cancelButton) { dialogInterface, i->}
                .setNegativeButton(R.string.delete) { dialogInterface, i->
                    Executors.newSingleThreadExecutor().execute { dataService.deleteFavorites(favorites,this) }                }
                .create().show()

    }*/

    override fun onRemove(favorites: Favorites) {
        val dataService: DataService = (requireActivity().application as MyApp).dataService
        AlertDialog.Builder(context)
            .setNeutralButton(R.string.cancelButton) { dialogInterface, i->}
            .setNegativeButton(R.string.delete) { dialogInterface, i->
                Executors.newSingleThreadExecutor().execute { dataService.deleteFavorites(favorites,this) }                }
            .create().show()
        dataService.getFavorites(this)
        forYouAdapter.notifyDataSetChanged()
    }



}
