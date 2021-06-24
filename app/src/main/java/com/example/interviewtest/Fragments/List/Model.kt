package com.example.interviewtest.Fragments.List

import android.content.Context
import com.example.interviewtest.Database.DataBaseHandler
import com.example.interviewtest.Model.Location
import com.google.android.gms.maps.model.LatLng

class Model : Contract.Model {
    override fun consultLocations(context: Context): MutableList<Location> {
        var db = DataBaseHandler(context)
        return db.consultLocations()
    }

    override fun deleteAllLocations(context: Context) {
        var db = DataBaseHandler(context)
        db.deleteLocations(context)
    }
}