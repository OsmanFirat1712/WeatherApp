package  com.example.projekt1rain.Fragments

import android.content.DialogInterface
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.SearchView;
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projekt1rain.*
import com.example.projekt1rain.DataStorag.DataService
import com.example.projekt1rain.InterFaces.CallBack
import com.example.projekt1rain.InterFaces.GetName
import com.example.projekt1rain.RetrofitApi.retrofitResponse
import com.example.projekt1rain.RetrofitApi.retrofitResponse2
import com.example.projekt1rain.Room.City
import com.example.projekt1rain.Room.Favorites
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.foryoufragment.view.*
import kotlinx.android.synthetic.main.mapviewfragment.*
import java.io.IOException

private const val TAG = "MapViewFragment"

class MapViewFragment : Fragment(), OnMapReadyCallback, CallBack,GetName {
    private var favorites: List<Favorites> = emptyList()
    private var takeLat:Double = 0.0
    private var takeLon:Double = 0.0
    private lateinit var currentWeather:CurrentWeather

    companion object {
        private lateinit var nMap: GoogleMap
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
                val lat = favorite.currentWeatherResponse?.coord?.lat
                val lon = favorite.currentWeatherResponse?.coord?.lon

                if (lat != null && lon != null)
                    nMap.addMarker(MarkerOptions().position(LatLng(lat, lon))

                        //Need icon
                        .title("Temperature :")
                        .snippet(favorite.currentWeatherResponse?.main?.temp?.toInt()?.minus(273.15.toInt()).toString()+"°C"))
            }

            nMap.setOnMapLongClickListener { latlng ->


                Log.i(TAG, "onMapLongClickListener" + latlng)

                Toast.makeText(
                        requireContext(),
                        "this is toast message" + latlng,
                        Toast.LENGTH_SHORT
                ).show()
                showAlertDialog(latlng)



                val address = getAddress(latlng.latitude, latlng.longitude)
                retrofitResponse(address)
                retrofitResponse2(latlng.latitude,latlng.longitude)


                Log.d(TAG, "test5 $address")
                Toast.makeText(requireContext(), "test" + address, LENGTH_LONG).show()
            }
        }
    }
    private fun getAddress(lat: Double, lng: Double): String {

        val geocoder = Geocoder(requireContext())
        val list = geocoder.getFromLocation(lat, lng, 1)
        if (list != null && list.size > 0) {
            list.forEach {
                if (it.getLocality() != null && it.getLocality().length > 0) {
                    return it.getLocality();
                }
            }

        }
        return ""
    }
    private fun startBtn(view: View){

        Toast.makeText(requireContext(), "Clicked on Button", Toast.LENGTH_LONG).show()


        lateinit var location: String
        val dataService: DataService = (requireActivity().application as MyApp).dataService
        val searchView = view.findViewById<SearchView>(R.id.sv_location)
        location = searchView.query.toString()
        dataService.getCitiesFindbyName(location,this)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                searchView.clearFocus()
                var addressList: List<Address>? = null

                if (location == null || location == "") {
                    Toast.makeText(requireContext(),"provide location",Toast.LENGTH_SHORT).show()
                }

                else{
                    val geoCoder = Geocoder(requireContext())
                    try {
                        addressList = geoCoder.getFromLocationName(location, 1)

                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    val addresss = addressList!![0]

                    Toast.makeText(requireContext(), addresss.latitude.toString() + " " + addresss.longitude, Toast.LENGTH_LONG).show()
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                TODO("Not yet implemented")

                return false
            }
        })
    }
    private fun showAlertDialog(latlng: LatLng) {
        val dialog =
                AlertDialog.Builder(requireContext())
                        .setTitle("Create a marker").setMessage("add marker...")
                        .setNegativeButton("Cancel", null)
                        .setPositiveButton("Ok", null)
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
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.mapviewfragment, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataService: DataService = (requireActivity().application as MyApp).dataService
        val button = view.findViewById<Button>(R.id.startBtn)
        button.setOnClickListener {
            startBtn(view)
        }
        dataService.getFavorites(this)
    }
    override fun onComplete(favorites: List<Favorites>) {
        this.favorites = favorites
    }
    override fun onFinish(City: City) {



        if (City.name!= null){
            val latLng = LatLng(City.coord?.lat!!,City.coord?.lon!!)

            nMap!!.addMarker(MarkerOptions().position(latLng).title(City.name))
            nMap!!.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        }

        else{
            Toast.makeText(requireContext(),"There is no info about this city",Toast.LENGTH_LONG).show()
        }

    }
}
