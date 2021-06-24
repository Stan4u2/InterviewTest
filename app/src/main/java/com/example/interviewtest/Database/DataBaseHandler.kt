package com.example.interviewtest.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.example.interviewtest.Model.Location
import com.google.android.gms.maps.model.LatLng

val DATABASE_NAME = "LocationsDatabase"
val TABLE_NAME = "Locations"
val COL_ID = "id"
val COL_POSITION_LAT = "position_lat"
val COL_POSITION_LNG = "position_lng"
val COL_ADDRESS = "address"
val COL_COLONY = "colony"
val COL_POSTAL_CODE = "postal_code"
val COL_CITY = "city"
val COL_STATE = "state"
val COL_COUNTRY = "country"

class DataBaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_NAME (" +
                "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COL_POSITION_LAT VARCHAR(50)," +
                "$COL_POSITION_LNG VARCHAR(50)," +
                "$COL_ADDRESS VARCHAR(50)," +
                "$COL_COLONY VARCHAR(80)," +
                "$COL_POSTAL_CODE VARCHAR(5)," +
                "$COL_CITY VARCHAR(50)," +
                "$COL_STATE VARCHAR(50)," +
                "$COL_COUNTRY VARCHAR(50))"

        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun insertLocation(location: Location) {
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_POSITION_LAT, location.position.latitude)
        cv.put(COL_POSITION_LNG, location.position.longitude)
        cv.put(COL_ADDRESS, location.address)
        cv.put(COL_COLONY, location.colony)
        cv.put(COL_POSTAL_CODE, location.postalCode)
        cv.put(COL_CITY, location.city)
        cv.put(COL_STATE, location.state)
        cv.put(COL_COUNTRY, location.country)

        var result = db.insert(TABLE_NAME, null, cv)
        if (result == (-1).toLong()) {
            Toast.makeText(context, "Failed the insertion to the database", Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(context, "Location Inserted", Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

    fun consultLocations(): MutableList<Location> {
        val db = this.readableDatabase
        val locations: MutableList<Location> = ArrayList()
        val sqlQuery = "SELECT * FROM $TABLE_NAME"

        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(sqlQuery, null)
        } catch (e: SQLException) {
            Log.e("SQLiteError", e.message.toString())
            db.execSQL(sqlQuery)
            return ArrayList()
        }

        if (cursor.moveToFirst()) {
            do {
                var location = Location()
                location.address = cursor.getString(cursor.getColumnIndex(COL_ADDRESS))
                location.colony = cursor.getString(cursor.getColumnIndex(COL_COLONY))
                location.postalCode = cursor.getString(cursor.getColumnIndex(COL_POSTAL_CODE))
                location.city = cursor.getString(cursor.getColumnIndex(COL_CITY))
                location.state = cursor.getString(cursor.getColumnIndex(COL_STATE))
                location.country = cursor.getString(cursor.getColumnIndex(COL_COUNTRY))
                var lat = (cursor.getString(cursor.getColumnIndex(COL_POSITION_LAT))).toDouble()
                var lng = (cursor.getString(cursor.getColumnIndex(COL_POSITION_LNG))).toDouble()
                location.position = LatLng(lat, lng)
                locations.add(location)
            } while (cursor.moveToNext())
        }
        db.close()
        return locations
    }

    fun deleteLocations(context: Context) {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM $TABLE_NAME")
        Toast.makeText(context, "List Cleared", Toast.LENGTH_SHORT).show()
    }
}
