package com.example.touristapp.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.touristapp.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var googleMap : GoogleMap

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
               Log.i("w", "tu")
               // For zooming automatically to the location of the marker
               val cameraPosition =
                   CameraPosition.Builder().target(sydney).zoom(12f).build()
               googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
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
}
