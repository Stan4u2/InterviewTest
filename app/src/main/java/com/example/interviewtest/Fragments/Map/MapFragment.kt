package com.example.interviewtest.Fragments.Map

import android.content.Context
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import com.example.interviewtest.Main.MainActivity

import com.example.interviewtest.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng

class MapFragment : Fragment(), OnMapReadyCallback, Contract.View {

    private lateinit var addLocation: Button
    private lateinit var map: GoogleMap
    private lateinit var myContext: Context
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var lastKnownLocation: Location? = null
    private val presenter = Presenter(this, Model())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_map, container, false)

        myContext = requireActivity()
        //Button
        addLocation = requireActivity().findViewById(R.id.addLocation)
        checkPermissionAndGPS()
        clickListeners()
        return view
    }

    private fun clickListeners() {
        addLocation.setOnClickListener {
            presenter.addLocation(
                myContext, lastKnownLocation!!.latitude,
                lastKnownLocation!!.longitude
            )
        }
    }

    private fun checkPermissionAndGPS() {
        var continueCode = true
        when {
            !presenter.isGPSOn(myContext) -> {
                presenter.showMessage(myContext, "Turn on your GPS")
                continueCode = false
            }
            !presenter.isLocationPermissionGranted(myContext) -> {
                presenter.requestLocationPermission(myContext)
                continueCode = false
            }
        }

        if (continueCode) {
            //Will only appear if the permissions have been accepted
            addLocation.isGone = false
            buildMap()
        } else {
            addLocation.isGone = true
        }
    }

    private fun buildMap() {
        //This creates the map fragment
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.myMap) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        //Location has been granted so activate the current location
        map.isMyLocationEnabled = true
        //This function will get the current device location
        getDeviceLocation()
    }

    override fun onResume() {
        super.onResume()
        //If the Persmission has been granted then continue building the map
        if (presenter.isLocationPermissionGranted(myContext)) {
            addLocation.isGone = false
            buildMap()
        }
    }

    private fun getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        var zoom: Float = 20.0F
        try {
            val locationResult = fusedLocationProviderClient.lastLocation
            locationResult.addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Set the map's camera position to the current location of the device.
                    lastKnownLocation = task.result
                    if (lastKnownLocation != null) {
                        map?.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(
                                    lastKnownLocation!!.latitude,
                                    lastKnownLocation!!.longitude
                                ), zoom
                            )
                        )
                    }
                } else {
                    presenter.showMessage(
                        myContext,
                        "There has been an error in getting you position, try it again please"
                    )
                    Log.d("Error", "Current location is null. Using defaults.")
                    Log.e("Error", "Exception: %s", task.exception)
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    override fun changeFragment() {
        (activity as AppCompatActivity).supportFragmentManager.beginTransaction().replace(R.id.FrameLayout, com.example.interviewtest.Fragments.List.ListFragment()).commit()
        //(getActivity() as AppCompatActivity).supportFragmentManager.beginTransaction()
        //.replace(R.id.YourFrameLayout, YourFragment()).commit()
        /*
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.FrameLayout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
         */
    }


}