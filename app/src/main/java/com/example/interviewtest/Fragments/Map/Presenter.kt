package com.example.interviewtest.Fragments.Map

import android.Manifest
import android.R
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class Presenter(private val view: Contract.View, private val model: Contract.Model) :
    Contract.Presenter {

    override fun addLocation(context: Context, lat: Double, lng: Double) {
        CoroutineScope(Dispatchers.Main).launch {
            model.insertLocationDatabase(context, consultAddress(context, lat, lng))
            view.changeFragment()
        }
    }

    private fun consultAddress(
        context: Context,
        lat: Double,
        lng: Double
    ): com.example.interviewtest.Model.Location {
        //Create an object location to store the data
        var location = com.example.interviewtest.Model.Location()
        val geoCoder = Geocoder(context, Locale.getDefault())
        val address = geoCoder.getFromLocation(lat, lng, 1)
        //If the address retrieved by geoCoder is
        if (address[0].getAddressLine(0).toString().isNotEmpty()) {
            location.position = LatLng(lat, lng)
            var street = address[0].thoroughfare ?: ""
            var num = address[0].subThoroughfare ?: ""
            location.address = "$street $num"
            location.colony = address[0].subLocality ?: ""
            location.postalCode = address[0].postalCode ?: ""
            location.city = address[0].locality ?: ""
            location.state = address[0].adminArea ?: ""
            location.country = address[0].countryName ?: ""
        } else {
            location.address = "Address not available"
        }
        return location
    }

    override fun isLocationPermissionGranted(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun isGPSOn(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return LocationManagerCompat.isLocationEnabled(locationManager)
    }

    override fun requestLocationPermission(context: Context) {
        requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            101
        )
    }

    override fun showMessage(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}