package com.example.interviewtest.Main

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import android.widget.Toolbar
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import com.example.interviewtest.Fragments.List.ListFragment
import com.example.interviewtest.Fragments.Map.MapFragment
import com.example.interviewtest.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    val manager = supportFragmentManager
    private lateinit var bottomNavigationBar: BottomNavigationView
    private lateinit var addLocation: Button
    private lateinit var topToolBar: Toolbar
    private val requestCode = 101


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Button
        addLocation = findViewById(R.id.addLocation)
        //BottomNavigationBar
        bottomNavigationBar = findViewById(R.id.bottomNavigationBar)
        //Toolbar
        topToolBar = findViewById(R.id.topToolBar)
        topToolBar.inflateMenu(R.menu.menu_main)
        //Hide the toolbar action
        topToolBar.menu.findItem(R.id.action_clear).isVisible = false
        //Change to Map Fragment
        changeFragment(MapFragment())
        clickListeners()
    }

    private fun clickListeners() {
        bottomNavigationBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.mapButton -> {
                    topToolBar.menu.findItem(R.id.action_clear).isVisible = false
                    changeFragment(MapFragment())
                    true
                }
                R.id.listButton -> {
                    topToolBar.menu.findItem(R.id.action_clear).isVisible = true
                    changeFragment(ListFragment())
                    true
                }
                else -> false
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            101 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Permission Granted
                } else {
                    Toast.makeText(
                        this,
                        "To activate the location go to configurations and accept the permissions",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            else -> {
            }
        }
    }

    fun changeFragment(fragment: Fragment){
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.FrameLayout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
