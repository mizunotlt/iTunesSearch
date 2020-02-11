package com.example.itunessearch.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.itunessearch.R
import com.example.itunessearch.data.SongsData
import com.example.itunessearch.models.SongsModels
import com.example.itunessearch.adapters.AdapterSongs
import com.example.itunessearch.di.annotation.ApplicationScope
import com.example.itunessearch.di.annotation.SongsViewModelScope
import toothpick.Scope
import toothpick.ktp.KTP
import toothpick.smoothie.lifecycle.closeOnDestroy
import toothpick.smoothie.viewmodel.closeOnViewModelCleared
import toothpick.smoothie.viewmodel.installViewModelBinding
import java.lang.IndexOutOfBoundsException
import java.lang.StringBuilder


class SongsViewFragment : Fragment() {

    private val songsModel by lazy { ViewModelProviders.of(this).get(SongsModels::class.java)}
    private lateinit var listAlbums: RecyclerView
    private lateinit var listSongs: RecyclerView
    private lateinit var adapterSongs: AdapterSongs
    private lateinit var textViewCollectionName: TextView
    private lateinit var textViewArtistName: TextView
    private lateinit var textViewGenre: TextView
    private lateinit var textViewCountry: TextView
    private lateinit var textViewPrice: TextView
    private lateinit var textViewReleaseDate: TextView
    private lateinit var imageView: ImageView
    private lateinit var songsView: View
    private lateinit var trackCount: TextView

    companion object {
        fun newInstance() = SongsViewFragment()
    }

    override fun onStart() {
        super.onStart()
        injectDependencies()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

        val bundleId = arguments
        songsModel.getSongs(bundleId!!.getInt("id"))

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        songsView = inflater.inflate(R.layout.songs_view_fragment, container, false)
        textViewCollectionName = songsView.findViewById(R.id.textViewCollectionName)
        textViewArtistName = songsView.findViewById(R.id.textViewArtistName)
        textViewGenre = songsView.findViewById(R.id.textViewGenre)
        textViewCountry = songsView.findViewById(R.id.textViewCountry)
        textViewPrice = songsView.findViewById(R.id.textViewPrice)
        textViewReleaseDate = songsView.findViewById(R.id.textViewDateRelease)
        listSongs = songsView.findViewById(R.id.songsView)
        imageView = songsView.findViewById(R.id.imageViewAlbums)
        trackCount = songsView.findViewById(R.id.textViewTrackNumber)
        adapterSongs = AdapterSongs(arrayListOf())
        listSongs.adapter = adapterSongs


        return songsView
    }

    private fun injectDependencies() {

        KTP.openScopes(ApplicationScope::class.java)
            .openSubScope(SongsViewModelScope::class.java) { scope: Scope ->
                scope.installViewModelBinding<SongsModels>(this)
                    .closeOnViewModelCleared(this)
            }
            .closeOnDestroy(this)
            .inject(this)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        songsModel.songsLiveData.observe(viewLifecycleOwner, Observer<ArrayList<SongsData>>{ data ->

            try {
                textViewCollectionName.text = data[0].collectionName
                textViewArtistName.text = data[0].artistName
                textViewGenre.text = data[0].primaryGenreName
                textViewCountry.text = data[0].country
                textViewPrice.text = data[0].collectionPrice.toString()
                textViewReleaseDate.text = data[0].releaseDate
                trackCount.text = (StringBuilder().append("Number of tracks: ").append(data[0].trackCount)).toString()
                Glide.with(this).load(data[0].artworkUrl60).into(imageView)
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

    override fun onResume() {
        super.onResume()
        val bundleId = arguments
        songsModel.getSongs(bundleId!!.getInt("id"))
    }

}
