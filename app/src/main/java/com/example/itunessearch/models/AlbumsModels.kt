package com.example.itunessearch.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.itunessearch.api.ITunesApiFactory
import com.example.itunessearch.data.AlbumsData
import com.example.itunessearch.repository.AlbumsRepository
import com.example.itunessearch.utils.Constants
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class AlbumsModels: ViewModel() {

    private val parentJob = Job()

    private val coroutinesContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutinesContext)

    private val repository : AlbumsRepository = AlbumsRepository(ITunesApiFactory.albumsApi)

    val albumsLiveData = MutableLiveData<List<AlbumsData>>().apply { value = listOf() }
    val idAlbums = MutableLiveData<Int>().apply { value = 0 }
    private var offset = 0
    private val albums: ArrayList<AlbumsData> = arrayListOf()
    private val listAlbums: MutableList<AlbumsData> = mutableListOf()
    var responseStatus = MutableLiveData<Boolean>().apply { value = false }


    fun setIdAlbums(id: Int){
        idAlbums.apply { value = id }
    }


    fun getAlbumsByTerm(term: String){
        albumsLiveData.apply { value = listOf() }
        albums.clear()
        listAlbums.clear()
        offset = 0
        responseStatus.apply { value = false }
        scope.launch {
            while (true){
                val responseAlbums = repository.getAlbumsListByTerm(term, offset)
                if (responseAlbums != null && responseAlbums.size == Constants.LIMIT){
                    albums.addAll(responseAlbums)
                    listAlbums.addAll(albums.sortedBy { it.collectionName })
                    offset +=Constants.LIMIT

                }
                else{
                    if (responseAlbums != null && responseAlbums.size < Constants.LIMIT){
                        albums.addAll(responseAlbums)
                        listAlbums.addAll(albums.sortedBy { it.collectionName })
                        responseStatus.postValue(true)
                        albumsLiveData.postValue(listAlbums)
                        break
                    }
                }
            }

        }
    }

    fun cancelAllRequests() = coroutinesContext.cancel()

}