package com.example.itunessearch.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.itunessearch.ITunesSearchApplication
import com.example.itunessearch.R
import com.example.itunessearch.data.SongsData
import com.example.itunessearch.models.SongsModels
import com.example.itunessearch.adapters.AdapterSongs
import kotlinx.android.synthetic.main.songs_view_fragment.*
import java.lang.IllegalArgumentException
import java.lang.IndexOutOfBoundsException
import java.lang.StringBuilder
import javax.inject.Inject


class SongsViewFragment : Fragment() {

    @Inject
    lateinit var songsModel: SongsModels
    private lateinit var adapterSongs: AdapterSongs


    companion object {
        fun newInstance() = SongsViewFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ITunesSearchApplication.appComponent.inject(songsViewFragment = this@SongsViewFragment)

        try {
            val id = arguments?.let { SongsViewFragmentArgs.fromBundle(it).id }
            retainInstance = true
            if (songsModel.id == null){
                songsModel.id = id

            }
            else if(songsModel.id != id){
                songsModel.id = id
            }
            songsModel.getSongs()
        }
        catch (e: IllegalArgumentException){

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.songs_view_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapterSongs = AdapterSongs(arrayListOf())
        songsView.adapter = adapterSongs
        songsModel.songsLiveData.observe(viewLifecycleOwner, Observer<ArrayList<SongsData>>{ data ->

            try {
                textViewCollectionName.text = data[0].collectionName
                textViewArtistName.text = data[0].artistName
                textViewGenre.text = data[0].primaryGenreName
                textViewCountry.text = data[0].country
                textViewPrice.text = data[0].collectionPrice.toString()
                textViewDateRelease.text = data[0].releaseDate
                textViewTrackNumber.text = (StringBuilder().append("Number of tracks: ").append(data[0].trackCount)).toString()
                Glide.with(this).load(data[0].artworkUrl60).into(imageViewAlbums)
            }
            catch (e: IndexOutOfBoundsException){
                Log.i("STATUS DATA", "Data is loading")
            }

        })

        songsModel.songsDataForAdapter.observe(viewLifecycleOwner, Observer<ArrayList<SongsData>>{
            Log.i("STATUS DATA", songsModel.songsDataForAdapter.value!!.toString())
            adapterSongs.update(songsModel.songsDataForAdapter.value!!)
        })
    }
}
