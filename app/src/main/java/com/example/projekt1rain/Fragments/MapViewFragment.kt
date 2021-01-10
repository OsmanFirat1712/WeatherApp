package  com.example.projekt1rain.Fragments

import android.content.DialogInterface
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.example.projekt1rain.DataStorag.DataService
import com.example.projekt1rain.InterFaces.CallBack
import com.example.projekt1rain.InterFaces.GetName
import com.example.projekt1rain.MainActivity
import com.example.projekt1rain.MyApp
import com.example.projekt1rain.R
import com.example.projekt1rain.RetrofitApi.retrofitResponse
import com.example.projekt1rain.RetrofitApi.retrofitOneCallResponse
import com.example.projekt1rain.Room.City
import com.example.projekt1rain.Room.Favorites
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.mapviewfragment.*
import java.io.IOException
import java.text.DecimalFormat

private const val TAG = "MapViewFragment"

class MapViewFragment : Fragment(), OnMapReadyCallback, CallBack,GetName {
    private var favorites: List<Favorites> = emptyList()

    private lateinit var currentLocation: Location



    private var takeLat:Double = 0.0
    private var takeLon:Double = 0.0

    companion object {
         lateinit var nMap: GoogleMap
        private var markers: MutableList<Marker> = mutableListOf<Marker>()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        map_view.onCreate(savedInstanceState)
        map_view.onResume()
        map_view.getMapAsync(this)
        setToolbar()
    }

    override fun onMapReady(map: GoogleMap?) {

        if (map != null) {
            nMap = map
        }
        nMap.uiSettings.setZoomControlsEnabled(true)
        map?.let {
            nMap = it

            nMap.setOnInfoWindowClickListener { markerToDelete ->
                Log.i(TAG, "onWindowsClickListener - Delete Thismarker")
                markers.remove(markerToDelete)
                markerToDelete.remove()

            }


            favorites.forEach { favorite ->
                val lat = favorite.currentWeatherResponse?.lat
                val lon = favorite.currentWeatherResponse?.lon

                if (lat != null && lon != null)
                    nMap.addMarker(
                        MarkerOptions().position(LatLng(lat, lon))
                            //Need icon
                            .title(getString(R.string.temperature))
                            .snippet(
                                favorite.currentWeatherResponse?.current?.temp?.toInt()?.minus(
                                    273.15.toInt()
                                ).toString() + "°C"
                            )
                    )
            }

            nMap.setOnMapLongClickListener { latlng ->

                Log.i(TAG, "onMapLongClickListener" + latlng)
                showAlertDialog(latlng)
                val address = getAddress(latlng.latitude, latlng.longitude)
                retrofitResponse(address)
                retrofitOneCallResponse(latlng.latitude, latlng.longitude, address)
                Log.d(TAG, "test5 $address")
                Toast.makeText(requireContext(), getString(R.string.hinzufügen), LENGTH_LONG).show()
            }
        }
    }
    private fun getAddress(lat: Double, lng: Double): String {
        val df = DecimalFormat()

        df.maximumFractionDigits = 3
        val geocoder = Geocoder(requireContext())
        val list = geocoder.getFromLocation(lat, lng, 1)
        if (list != null && list.size > 0) {
            list.forEach {
                if (it.locality != null && it.locality.isNotEmpty()) {
                    return it.locality;
                }
            }

        }
        return ""

    }
     fun startBtn(view: View){

        lateinit var location: String
        val dataService: DataService = (requireActivity().application as MyApp).dataService
        val searchView = view.findViewById<SearchView>(R.id.sv_location)
        location = searchView.query.toString()
        dataService.getCitiesFindbyName(location, this)

         searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                searchView.clearFocus()
                var addressList: List<Address>? = null
/*
                if (location == null || location == "") {
*/
                    if (location == "") {
                    Toast.makeText(requireContext(), "provide location", Toast.LENGTH_SHORT).show()
                } else {
                    val geoCoder = Geocoder(requireContext())
                    try {
                        addressList = geoCoder.getFromLocationName(location, 1)

                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    val addresss = addressList?.get(0)
                       val latlng = LatLng(addresss!!.latitude,addresss.longitude)

                    nMap.addMarker(MarkerOptions().position(latlng).title(location))
                        retrofitOneCallResponse(latlng.latitude, latlng.longitude, location)


                        Toast.makeText(
                        requireContext(),
                        addresss?.latitude.toString() + " " + addresss?.longitude,
                        Toast.LENGTH_LONG
                    ).show()

                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                searchView.query.isNullOrEmpty()
                return false
            }
        })

    }
    private fun showAlertDialog(latlng: LatLng) {
        val dialog =
                AlertDialog.Builder(requireContext())
                        .setTitle(getString(R.string.favoritsetzen)).setMessage("")
                        .setNegativeButton(getString(R.string.abbrechen), null)
                        .setPositiveButton(getString(R.string.ok), null)
                        .show()

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
            val marker = nMap.addMarker(

                MarkerOptions().position(latlng).title("my new marker").snippet(


                    "a cool snippet"

                )
            )

            markers.add(marker)


            dialog.dismiss()
        }

        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener {
            dialog.dismiss()
        }

    }
    private fun setToolbar() {
        val actionBar: ActionBar? = (requireActivity() as MainActivity).supportActionBar
        actionBar?.apply {
            title = getString(R.string.Kartenansicht)
            setDisplayShowTitleEnabled(true)
            setHomeButtonEnabled(true)
        }
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
        val dataService: DataService = (requireActivity().application as MyApp).dataService
        val button = view.findViewById<Button>(R.id.startBtn)
        button.setOnClickListener {
            startBtn(view,)
        }
        dataService.getFavorites(this)
    }
    override fun onComplete(favorites: List<Favorites>) {
        this.favorites = favorites
    }
    override fun onFinish(City:City ) {


        if (City.name!= null ){
            val latLng = LatLng(City.coord?.lat!!, City.coord?.lon!!)
            val lat= (City.coord?.lat!!)
            val long = (City.coord?.lon!!)


            nMap!!.addMarker(MarkerOptions().position(latLng).title(City.name))
            retrofitOneCallResponse(lat,long,City.name)
            nMap!!.animateCamera(CameraUpdateFactory.newLatLng(latLng))

        }


        else{
            Toast.makeText(requireContext(), "There is no info about this city", Toast.LENGTH_LONG).show()
        }

    }

}
