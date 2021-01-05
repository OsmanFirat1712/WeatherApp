package  com.example.projekt1rain.Fragments

import android.content.DialogInterface
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.projekt1rain.*
import com.example.projekt1rain.DataStorag.DataService
import com.example.projekt1rain.InterFaces.CallBack
import com.example.projekt1rain.RetrofitApi.retrofitResponse
import com.example.projekt1rain.Room.Favorites
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.mapviewfragment.*

private const val TAG = "MapViewFragment"

class MapViewFragment : Fragment(), OnMapReadyCallback, CallBack {
    private var favorites: List<Favorites> = emptyList()

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
                    nMap.addMarker(MarkerOptions().position(LatLng(lat, lon)))
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

                Log.d(TAG, "test5 $address")
                Toast.makeText(requireContext(), "test" + address, LENGTH_LONG).show()
            }

            nMap.setOnMarkerClickListener {

                MarkerOptions().position(it.position).title("my new marker").snippet(
                        "a cool snippet"
                )
                true
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
        dataService.getFavorites(this)

    }

    private fun setToolbar() {
        val actionBar: ActionBar? = (requireActivity() as MainActivity).supportActionBar
        actionBar?.apply {
            title = getString(R.string.Kartenansicht)
            setDisplayShowTitleEnabled(true)
            setHomeButtonEnabled(true)
        }
    }

    override fun onComplete(favorites: List<Favorites>) {
        this.favorites = favorites
    }
}
