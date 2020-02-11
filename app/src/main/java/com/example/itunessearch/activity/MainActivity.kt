package com.example.itunessearch.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.itunessearch.R
import com.example.itunessearch.di.*
import com.example.itunessearch.fragments.AlbumsViewFragment
import com.example.itunessearch.fragments.SongsViewFragment
import com.example.itunessearch.models.AlbumsModels
import com.example.itunessearch.models.SongsModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import toothpick.Scope
import toothpick.ktp.KTP
import toothpick.smoothie.lifecycle.closeOnDestroy
import toothpick.smoothie.viewmodel.closeOnViewModelCleared
import toothpick.smoothie.viewmodel.installViewModelBinding


class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView
    private val fragmentManager by lazy { supportFragmentManager }
    private val albumsTAG = "albumsFragment"
    private val songsTAG = "songsFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var albumsFragment = fragmentManager.findFragmentByTag(albumsTAG) as? AlbumsViewFragment
        var songsFragment = fragmentManager.findFragmentByTag(songsTAG) as? SongsViewFragment
        if (albumsFragment == null) {
            albumsFragment = AlbumsViewFragment()
            createFragment(albumsFragment, albumsTAG)
        }
        bottomNavigation = findViewById(R.id.navigationMenu)
        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_album ->{
                    if (albumsFragment == null) {
                        albumsFragment = AlbumsViewFragment()
                        createFragment(albumsFragment!!,albumsTAG)
                    }
                    else{
                        replaceFragment(albumsFragment!!, albumsTAG)
                    }
                    it.isChecked = true

                }
                R.id.navigation_songs -> {
                    val bundleId = Bundle()
                    if (songsFragment == null) {
                        if(albumsFragment!!.getIdAlbums() == 0){
                            createToast("Albums not pick")
                        }
                        else{
                            songsFragment = SongsViewFragment()
                            bundleId.putInt("id", albumsFragment!!.getIdAlbums())
                            songsFragment!!.arguments = bundleId
                            createFragment(songsFragment!!, songsTAG)
                            it.isChecked = true
                        }
                    }
                    else{
                        bundleId.putInt("id", albumsFragment!!.getIdAlbums())
                        songsFragment!!.arguments = bundleId
                        replaceFragment(songsFragment!!,songsTAG)
                        it.isChecked = true
                    }

                }
            }
            false
        }

    }
    /*
        Function for createFragmentFirst if it is first call!
     */
    private fun createFragment(fragment: Fragment, tag: String){

        val fm = supportFragmentManager
        val fragmentTransaction = fm.beginTransaction()
        fragmentTransaction.replace(R.id.controlFrameLayout, fragment,  tag)
        fragmentTransaction.addToBackStack(tag)
        fragmentTransaction.commit()
    }

    /*
        Function for replace fragment another
     */
    private fun replaceFragment(fragment: Fragment, tag: String){

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.controlFrameLayout, fragment,  tag)
        fragmentTransaction.addToBackStack(tag)
        fragmentTransaction.commit()

    }

    /*
        Function for message users that he did't pick album
     */
    private fun createToast (text: String){
        val toast = Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.BOTTOM, 0, 50)
        toast.view.setBackgroundColor(Color.GRAY)
        toast.show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        bottomNavigation.selectedItemId = R.id.navigation_album
    }

}




