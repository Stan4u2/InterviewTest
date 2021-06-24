package com.example.interviewtest.Fragments.List

import android.content.Context
import com.example.interviewtest.Model.Location

interface Contract {
    interface View {
        fun showListLocations(locations: MutableList<Location>)
    }

    interface Presenter {
        fun getListLocations(context: Context)
        fun deleteLocations(context: Context)
    }

    interface Model {
        fun consultLocations(context: Context): MutableList<Location>
        fun deleteAllLocations(context: Context)
    }
}