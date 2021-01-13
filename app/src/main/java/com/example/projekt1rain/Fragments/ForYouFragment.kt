package com.example.projekt1rain.Fragments

//import com.example.projekt1rain.Adapter.ForYouAdapter
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.projekt1rain.Adapter.ForYouAdapter
import com.example.projekt1rain.DataStorag.DataService
import com.example.projekt1rain.FragmentCallBack
import com.example.projekt1rain.InterFaces.CallBack
import com.example.projekt1rain.InterFaces.RemoveCallBack
import com.example.projekt1rain.MainActivity
import com.example.projekt1rain.MyApp
import com.example.projekt1rain.R
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
        setHasOptionsMenu(true)
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
        setToolbar()
        val dataService: DataService = (requireActivity().application as MyApp).dataService

        val swipeRefresh = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)
        swipeRefresh.setOnRefreshListener {
            getOneCall()
            Toast.makeText(requireContext(), getString(R.string.refresh), Toast.LENGTH_LONG)
                .show()
            swipeRefresh.isRefreshing = false
        }
        forYouAdapter = ForYouAdapter(

            forYouConstruktorList = ArrayList(), requireContext(),
            this, this
        )

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerviewforyou)
        recyclerView.apply {
            adapter = forYouAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)

        }
        dataService.getFavorites(this)

        forYouAdapter.notifyDataSetChanged()

        val floatingButton: FloatingActionButton = view.findViewById(R.id.flab)
        floatingButton.setOnClickListener() {
            startMapViewFragment()
        }

    }

    private fun setToolbar() {
        val actionBar: androidx.appcompat.app.ActionBar? =
            (requireActivity() as MainActivity).supportActionBar
        actionBar?.apply {
            title = (getString(R.string.foryou))
            setDisplayShowTitleEnabled(true)
            setHomeButtonEnabled(false)
            setDisplayHomeAsUpEnabled(false)
        }
    }

    private fun startMapViewFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, MapViewFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun getOneCall() {
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

    //get the list for swipeToRefresh
    override fun onComplete(favorites: List<Favorites>) {
        requireActivity().runOnUiThread(java.lang.Runnable {
            forYouAdapter.updateFavList(favorites)
            forYouAdapter.notifyDataSetChanged()

            favorites.forEach { favorite ->
                val lat = favorite.currentWeatherResponse?.lat
                val lon = favorite.currentWeatherResponse?.lon
                val adress = favorite.address
                retrofitOneCallrefreshResponse(lat!!, lon!!, adress)
                forYouAdapter.updateFavList(favorites)
                forYouAdapter.notifyDataSetChanged()

            }
        })

    }


    override fun onCall(favorites: Favorites) {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        val blankFragmentDetailPage = DetailFragment()
        val bundle = Bundle()
        bundle.putSerializable("weatherkey", favorites)
        blankFragmentDetailPage.setArguments(bundle)
        transaction.replace(R.id.container, blankFragmentDetailPage)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun delete(favorites: Favorites) {
        val dataService: DataService = (requireActivity().application as MyApp).dataService
        AlertDialog.Builder(context)
            .setNeutralButton(R.string.cancelButton) { dialogInterface, i -> }
            .setNegativeButton(R.string.delete) { dialogInterface, i ->
                Executors.newSingleThreadExecutor()
                    .execute { dataService.deleteFavorites(favorites, this) }
            }
            .create().show()

    }

    override fun onRemove(favorites: Favorites) {
        val dataService: DataService = (requireActivity().application as MyApp).dataService
        AlertDialog.Builder(context)
            .setNeutralButton(R.string.cancelButton) { dialogInterface, i -> }
            .setNegativeButton(R.string.delete) { dialogInterface, i ->
                Executors.newSingleThreadExecutor()
                    .execute { dataService.deleteFavorites(favorites, this) }
            }
            .create().show()
        dataService.getFavorites(this)
        forYouAdapter.notifyDataSetChanged()
    }


}
