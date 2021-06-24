package com.example.interviewtest.Model

import com.google.android.gms.maps.model.LatLng

class Location(
    var position: LatLng = LatLng(0.0, 0.0),
    var address: String = "",
    var colony: String = "",
    var postalCode: String = "",
    var city: String = "",
    var state: String = "",
    var country: String = ""
)