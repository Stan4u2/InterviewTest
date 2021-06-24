package com.example.interviewtest.Fragments.List

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
import android.widget.Toolbar
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.interviewtest.Adapter.LocationsAdapter
import com.example.interviewtest.Model.Location
import com.example.interviewtest.R

class ListFragment : Fragment(), Contract.View {

    private lateinit var locationRecyclerView: RecyclerView
    private val locationsAdapter: LocationsAdapter = LocationsAdapter()
    private lateinit var addLocation: Button
    private lateinit var topToolBar: Toolbar
    private lateinit var myContext: Context

    val presenter = Presenter(this, Model())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_list, container, false)

        myContext = requireActivity()

        //Button
        addLocation = requireActivity().findViewById(R.id.addLocation)
        //Hide the add location button
        addLocation.isGone = true
        //RecyclerView
        locationRecyclerView = view.findViewById(R.id.locationRecyclerView)
        //ToolBar
        topToolBar = requireActivity().findViewById(R.id.topToolBar)
        topToolBar.menu.findItem(R.id.action_clear).isVisible = true
        clickListener()
        getListLocations()

        return view
    }

    private fun clickListener() {
        //When the tool bar action is clicked delete all directions from the database
        topToolBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_clear -> {
                    presenter.deleteLocations(myContext)
                }
            }
            true
        }
    }

    private fun getListLocations() {
        presenter.getListLocations(myContext)
    }

    override fun showListLocations(locations: MutableList<Location>) {
        locationRecyclerView.setHasFixedSize(true)
        locationRecyclerView.layoutManager = LinearLayoutManager(activity)
        locationsAdapter.LocationsAdapter(locations)
        locationRecyclerView.adapter = locationsAdapter
    }
}