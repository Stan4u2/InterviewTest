package com.example.interviewtest.Fragments.Map

import android.content.Context
import com.example.interviewtest.Model.Location

interface Contract {
    interface View {
        fun changeFragment()
    }

    interface Presenter {
        fun addLocation(context: Context, lat: Double, lng: Double)
        fun isLocationPermissionGranted(context: Context): Boolean
        fun isGPSOn(context: Context): Boolean
        fun requestLocationPermission(context: Context)
        fun showMessage(context: Context, message: String)
    }

    interface Model {
        fun insertLocationDatabase(context: Context, location: Location)
    }
}