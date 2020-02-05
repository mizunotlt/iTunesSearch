package com.example.itunessearch.fragments

import android.graphics.Color
import androidx.lifecycle.ViewModelProviders
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itunessearch.R
import com.example.itunessearch.data.AlbumsData
import com.example.itunessearch.models.AlbumsModels
import com.example.itunessearch.utils.AdapterAlbums
import com.example.itunessearch.utils.OnItemClickListener
import com.example.itunessearch.utils.ScrollListener
import java.lang.NumberFormatException


class AlbumsViewFragment : Fragment() {

    private val albumsViewModel by lazy { ViewModelProviders.of(this).get(AlbumsModels::class.java) }
    private lateinit var listAlbums: RecyclerView
    private lateinit var adapterAlbums: AdapterAlbums
    private lateinit var editTextFind: EditText
    private lateinit var viewAlbums: View
    private lateinit var buttonFind: ImageButton
    private lateinit var progressBar: ProgressBar
    private  var songsFragment: SongsViewFragment? = null
    private val songsTAG = "songsFragment"
    var isLastPage: Boolean = false
    var isLoading: Boolean = false

    companion object {
        fun newInstance() = AlbumsViewFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
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
                albumsViewModel.setIdAlbums(albumsViewModel.albumsLiveData.value!![position].collectionId)
                Log.i("Helps", albumsViewModel.albumsLiveData.value!![position].collectionId.toString())
                songsFragment = SongsViewFragment()
                val bundleId = Bundle()
                bundleId.putInt("id",albumsViewModel.idAlbums.value!! )
                songsFragment!!.arguments = bundleId
                val fragmentTransaction = fragmentManager!!.beginTransaction()
                fragmentTransaction.replace(R.id.controlFrameLayout, songsFragment!!,  songsTAG)
                fragmentTransaction.commit()
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
