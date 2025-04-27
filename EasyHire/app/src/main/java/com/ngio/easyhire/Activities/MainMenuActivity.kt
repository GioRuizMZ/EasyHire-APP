package com.ngio.easyhire.Activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ngio.easyhire.Fragments.Homefragment
import com.ngio.easyhire.Fragments.ActivitiesFragment
import com.ngio.easyhire.Fragments.SettingsFragment
import com.ngio.easyhire.R

class MainMenuActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        if (savedInstanceState == null) {
            loadFragment(Homefragment())
        }

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_Home -> loadFragment(Homefragment())
                R.id.navigation_activities -> loadFragment(ActivitiesFragment())
                R.id.navigation_settings -> loadFragment(SettingsFragment())
            }
            true
        }
    }
    private fun loadFragment(fragment: Fragment) {
        try {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("MenuActivity", "Error al cargar el fragmento: ${e.message}")
        }
    }
}