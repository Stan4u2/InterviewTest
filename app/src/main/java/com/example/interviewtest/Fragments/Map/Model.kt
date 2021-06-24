package com.example.interviewtest.Fragments.Map

import android.content.Context
import com.example.interviewtest.Database.DataBaseHandler
import com.example.interviewtest.Model.Location

class Model : Contract.Model {
    override fun insertLocationDatabase(context: Context, location: Location) {
        var db = DataBaseHandler(context)
        db.insertLocation(location)
    }
}