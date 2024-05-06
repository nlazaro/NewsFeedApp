package com.example.newsfeedapp

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.newsfeedapp.ui.books.BooksFragment
import com.example.newsfeedapp.ui.home.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enableEdgeToEdge()

        val fragmentManager : FragmentManager = supportFragmentManager
        val bottomNavigationView : BottomNavigationView = findViewById(R.id.nav_view)

        // Handles navigation selection
        bottomNavigationView.setOnItemSelectedListener { item ->
            lateinit var fragmentToShow : Fragment
            when (item.itemId){
                R.id.navigation_home -> fragmentToShow = HomeFragment()
                R.id.navigation_books -> fragmentToShow = BooksFragment()
            }
            fragmentManager.beginTransaction().replace(R.id.content, fragmentToShow).commit()
            // Return true to say that we've handled this user interaction on the item
            true
        }
        bottomNavigationView.selectedItemId = R.id.navigation_home
        // end of setup actions
    }
}