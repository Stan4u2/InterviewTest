package com.example.interviewtest.Fragments.List

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class Presenter(private val view: Contract.View, private val model: Contract.Model) :
    Contract.Presenter {
    override fun getListLocations(context: Context) {
        CoroutineScope(Dispatchers.Main).launch {
            model.consultLocations(context)
            view.showListLocations(model.consultLocations(context))
        }
    }

    override fun deleteLocations(context: Context) {
        CoroutineScope(Dispatchers.Main).launch {
            model.deleteAllLocations(context)
            view.showListLocations(model.consultLocations(context))
        }
    }


}