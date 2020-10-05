package com.example.touristapp.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.touristapp.R
import com.example.touristapp.User
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_home.*
import java.io.Serializable

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var googleMap : GoogleMap
   lateinit var user:User

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)

        val mapFragment: MapView =root.findViewById(R.id.mapView)
        mapFragment.onCreate(savedInstanceState)
        mapFragment.onResume()
       /* try {
           MapsInitializer.initialize(activity?.applicationContext)
        }catch (e:Exception){
            e.printStackTrace()
        }*/
        user=activity?.intent?.getSerializableExtra("User") as User
        var listDone=user.placesDone
        var listUndone=user.placesUndone


        try {
            MapsInitializer.initialize(requireActivity().applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        ActivityCompat.requestPermissions(
            this.requireActivity(),
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            1
        )
        Log.i("w","przed if")
       if(canAccessLocation()){

           //requestPermissions(INITIAL_PERMS, INITIAL_REQUEST);
           //}
           mapFragment.getMapAsync { mMap ->
               googleMap = mMap

               // For showing a move to my location button
               googleMap.isMyLocationEnabled = true

               // For dropping a marker at a point on the Map
               val sydney = LatLng(-34.0, 151.0)
               googleMap.addMarker(
                   MarkerOptions().position(sydney).title("Marker Title")
                       .snippet("Marker Description")
               )
               val katedra = LatLng(50.841111, 16.491944)
               googleMap.addMarker(
                   MarkerOptions().position(katedra).title("Katedra świdnicka")
                       .snippet("Marker Description")
               )
             //  val swidnica = LatLng(50.8421141,16.4862283)
              /*googleMap.addMarker(
                   MarkerOptions().position(swidnica).title("Rynek świdnica")
                       .snippet("Marker Description")
               )*/
               val rynek=LatLng(50.842528,16.486817)
               googleMap.addMarker(
                   MarkerOptions().position(rynek).title("Rynek świdnica")
                       .snippet("Marker Description")
               )
               drawCircle(rynek)
               Log.i("w", "tu")
               // For zooming automatically to the location of the marker
               val cameraPosition =
                   CameraPosition.Builder().target(rynek).zoom(12f).build()
               googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

               val mCircle=drawCircle(rynek)
               var imInside=false
                 googleMap.setOnMyLocationChangeListener { location ->
                     val distance = FloatArray(2)

                Location.distanceBetween(
                     location.latitude, location.longitude,
                     mCircle.getCenter().latitude, mCircle.getCenter().longitude, distance
                 )
                     if (distance[0] > mCircle.getRadius()) {
                         imInside=false
                         Toast.makeText(this@HomeFragment.requireActivity(),
                             "Outside, distance from center: " + distance[0] + " radius: " + mCircle.getRadius(),
                             Toast.LENGTH_LONG
                         ).show()
                     } else {
                         imInside=true
                         Toast.makeText(this@HomeFragment.requireActivity(),
                             "Inside, distance from center: " + distance[0] + " radius: " + mCircle.getRadius(),
                             Toast.LENGTH_LONG
                         ).show()
                     }
                 }
           }
       }

        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    private fun canAccessLocation(): Boolean {
        return hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun hasPermission(perm: String): Boolean {
        return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(
            this.requireContext(),
            perm
        )
    }

    private fun drawCircle(point: LatLng):CircleOptions {

        // Instantiating CircleOptions to draw a circle around the marker
        val circleOptions = CircleOptions()

        // Specifying the center of the circle
        circleOptions.center(point)

        // Radius of the circle
        circleOptions.radius(60.0)

        // Border color of the circle
        circleOptions.strokeColor(Color.BLACK)

        // Fill color of the circle
        circleOptions.fillColor(0x30ff0000)

        // Border width of the circle
        circleOptions.strokeWidth(2F)

        // Adding the circle to the GoogleMap
        googleMap.addCircle(circleOptions)
        return circleOptions
    }

    fun newInstance(index: User): HomeFragment? {
        val f = HomeFragment()

        // Supply index input as an argument.
        val args = Bundle()
        args.putSerializable("User", index)
        f.setArguments(args)
        return f
    }

    fun getShownIndex(): Serializable? {
        return requireArguments().getSerializable("User")
    }

    public fun showPopUp(popup:PopupWindow){
      //  popup.showAsDropDown(, 50, -500);
        popup.showAtLocation(mapView,50,50,-500)
    }
}
