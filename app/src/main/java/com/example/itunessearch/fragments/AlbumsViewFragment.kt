package com.example.itunessearch.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itunessearch.ITunesSearchApplication
import com.example.itunessearch.R
import com.example.itunessearch.data.AlbumsData
import com.example.itunessearch.models.AlbumsModels
import com.example.itunessearch.adapters.AdapterAlbums
import com.example.itunessearch.utils.OnItemClickListener
import com.example.itunessearch.utils.ScrollListener
import java.lang.NumberFormatException
import javax.inject.Inject


class AlbumsViewFragment : Fragment() {

    @Inject
    lateinit var albumsViewModel: AlbumsModels
    private lateinit var listAlbums: RecyclerView
    private lateinit var adapterAlbums: AdapterAlbums
    private lateinit var editTextFind: EditText
    private lateinit var viewAlbums: View
    private lateinit var buttonFind: ImageButton
    private lateinit var progressBar: ProgressBar
    var isLastPage: Boolean = false
    var isLoading: Boolean = false

    companion object {
        fun newInstance() = AlbumsViewFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        ITunesSearchApplication.appComponent.inject(albumsFragment = this@AlbumsViewFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewAlbums = inflater.inflate(R.layout.albums_view_fragment, container, false)
        listAlbums =viewAlbums.findViewById(R.id.listAlbums)
        adapterAlbums = AdapterAlbums(listOf())
        listAlbums.adapter = adapterAlbums
        editTextFind = viewAlbums.findViewById(R.id.editTextFind)
        buttonFind = viewAlbums.findViewById(R.id.imageButtonFind)
        progressBar= viewAlbums.findViewById(R.id.progressBar)
        progressBar.isVisible = false
        buttonFind.setOnClickListener {
            try{
                albumsViewModel.getAlbumsByTerm(editTextFind.text.toString())
                progressBar.isVisible = true
            }
            catch (e: NumberFormatException){
                Log.e("Error", "Error with input number")
            }
        }

        listAlbums.addOnItemClickListener(object : OnItemClickListener {

            override fun onItemClicked(position: Int, view: View) {
                findNavController().currentDestination!!.id = albumsViewModel.albumsLiveData.value!![position].collectionId
                albumsViewModel.setIdAlbums(albumsViewModel.albumsLiveData.value!![position].collectionId)
                val action = AlbumsViewFragmentDirections.actionAlbumsViewFragmentToSongsViewFragment(albumsViewModel.idAlbums.value!!)
                val bundleId = Bundle()
                bundleId.putInt("id",albumsViewModel.idAlbums.value!! )
                findNavController().navigate(action)
            }

        })

        listAlbums.addOnScrollListener(object : ScrollListener(listAlbums.layoutManager as LinearLayoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun getNewItems() {
                isLoading = true
                getNewAlbums()
            }
        })

        return viewAlbums
    }

    fun getIdAlbums(): Int{
        return albumsViewModel.idAlbums.value!!
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        albumsViewModel.albumsLiveData.observe(viewLifecycleOwner, Observer<List<AlbumsData>> {

            if(isLoading){
                adapterAlbums.pagingAdd(albumsViewModel.albumsLiveData.value!!)
                isLoading = false
            }
            else{
                adapterAlbums.update(albumsViewModel.albumsLiveData.value!!)
            }

            if (albumsViewModel.responseStatus.value!!){
                progressBar.isVisible = false
            }
            if (albumsViewModel.albumsLiveData.value!!.isEmpty() && albumsViewModel.responseStatus.value!!){
                createToast("Not found")
                progressBar.isVisible = false
            }
        })

    }

    private fun RecyclerView.addOnItemClickListener(onClickListener: OnItemClickListener) {
        this.addOnChildAttachStateChangeListener(object : RecyclerView.OnChildAttachStateChangeListener {

            override fun onChildViewDetachedFromWindow(view: View) {
                view.setOnClickListener(null)
            }

            override fun onChildViewAttachedToWindow(view: View) {
                view.setOnClickListener {
                    val holder = getChildViewHolder(view)
                    onClickListener.onItemClicked(holder.adapterPosition, view)
                }
            }
        })
    }

    private fun createToast (text: String){
        val toast = Toast.makeText(activity, text, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.BOTTOM, 0, 50)
        toast.view.setBackgroundColor(Color.GRAY)
        toast.show()
    }

    fun getNewAlbums() {
        albumsViewModel.getAlbumsByTermOffset(editTextFind.text.toString())
    }

}
