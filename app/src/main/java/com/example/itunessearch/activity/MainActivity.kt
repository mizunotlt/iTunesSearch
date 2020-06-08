package com.example.itunessearch.activity


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.core.os.postDelayed
import androidx.navigation.NavArgument
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.itunessearch.ITunesSearchApplication
import com.example.itunessearch.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var navGraph: NavController
    private lateinit var nav: BottomNavigationView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ITunesSearchApplication.appComponent.inject(activity = this@MainActivity)
        setContentView(R.layout.activity_main)
        nav = findViewById(R.id.navigationMenu)
        setupView()
    }

    private fun setupView(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentNavHost) as NavHostFragment
        navGraph = navHostFragment.navController
        NavigationUI.setupWithNavController(navigationMenu, navHostFragment.navController)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.albumsViewFragment, R.id.songsViewFragment))
        setupActionBarWithNavController(navHostFragment.navController, appBarConfiguration)
    }

    private var backPressedOnce = false

    override fun onBackPressed() {
        if (navGraph.graph.startDestination == navGraph.currentDestination?.id)
        {
            if (backPressedOnce)
            {
                super.onBackPressed()
                return
            }

            backPressedOnce = true
            Toast.makeText(this, "Press BACK again to exit", Toast.LENGTH_SHORT).show()

            Handler().postDelayed(2000) {
                backPressedOnce = false
            }
        }
        else {
            super.onBackPressed()
        }
    }

}




