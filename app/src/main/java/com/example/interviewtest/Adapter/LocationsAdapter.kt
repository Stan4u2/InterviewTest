package com.example.interviewtest.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.interviewtest.Model.Location
import com.example.interviewtest.R

class LocationsAdapter : RecyclerView.Adapter<LocationsAdapter.ViewHolder>() {

    var locations: MutableList<Location> = ArrayList()
    lateinit var context: Context

    fun LocationsAdapter(locations: MutableList<Location>) {
        this.locations = locations
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_list_location, parent, false))
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val item_list_address_title = view.findViewById(R.id.item_list_address_title) as TextView
        val item_list_address = view.findViewById(R.id.item_list_address) as TextView
        val item_list_colony_container =
            view.findViewById(R.id.item_list_colony_container) as LinearLayout
        val item_list_colony = view.findViewById(R.id.item_list_colony) as TextView
        val item_list_postal_code_container =
            view.findViewById(R.id.item_list_postal_code_container) as LinearLayout
        val item_list_postal_code = view.findViewById(R.id.item_list_postal_code) as TextView
        val item_list_city_container =
            view.findViewById(R.id.item_list_city_container) as LinearLayout
        val item_list_city = view.findViewById(R.id.item_list_city) as TextView
        val item_list_state_container =
            view.findViewById(R.id.item_list_state_container) as LinearLayout
        val item_list_state = view.findViewById(R.id.item_list_state) as TextView
        val item_list_country_title = view.findViewById(R.id.item_list_country_title) as TextView
        val item_list_country = view.findViewById(R.id.item_list_country) as TextView
        val item_list_position_title = view.findViewById(R.id.item_list_position_title) as TextView
        val item_list_position = view.findViewById(R.id.item_list_position) as TextView
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (locations[position].address == " ") {
            holder.item_list_address_title.isGone = true
            holder.item_list_address.isGone = true
        }
        if (locations[position].colony.isEmpty()) {
            holder.item_list_colony_container.isGone = true
            holder.item_list_colony.isGone = true
        }
        if (locations[position].postalCode.isEmpty()) {
            holder.item_list_postal_code_container.isVisible = false
            holder.item_list_postal_code.isGone = true
        }
        if (locations[position].city.isEmpty()) {
            holder.item_list_city_container.isGone = true
            holder.item_list_city.isGone = true
        }
        if (locations[position].state.isEmpty()) {
            holder.item_list_state_container.isGone = true
            holder.item_list_state.isGone = true
        }
        if (locations[position].country.isEmpty()) {
            holder.item_list_country_title.isGone = true
            holder.item_list_country.isGone = true
        }

        holder.item_list_address.text = locations[position].address
        holder.item_list_colony.text = locations[position].colony
        holder.item_list_postal_code.text = locations[position].postalCode
        holder.item_list_city.text = locations[position].city
        holder.item_list_state.text = locations[position].state
        holder.item_list_country.text = locations[position].country
        holder.item_list_position.text = locations[position].position.toString()
    }

    override fun getItemCount(): Int {
        return locations.size
    }
}