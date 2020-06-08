package com.example.itunessearch.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.itunessearch.ITunesSearchApplication
import com.example.itunessearch.data.AlbumsData
import com.example.itunessearch.repository.AlbumsRepository
import com.example.itunessearch.utils.Constants
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class AlbumsModels @Inject constructor(private val  repository: AlbumsRepository) : ViewModel() {

    init {
        ITunesSearchApplication.appComponent.inject(albumsModels = this)
    }

    private val parentJob = Job()

    private val coroutinesContext: CoroutineContext
        get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutinesContext)

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
            val responseAlbums = repository.getAlbumsListByTerm(term, offset)
            if (responseAlbums != null){
                albums.addAll(responseAlbums)
                offset += Constants.LIMIT
                listAlbums.addAll(albums.sortedBy { it.collectionName })
                responseStatus.postValue(true)
                albumsLiveData.postValue(listAlbums)
            }
        }
    }

    fun getAlbumsByTermOffset(term: String){
        scope.launch {
            val responseAlbums = repository.getAlbumsListByTerm(term, offset)
            if (responseAlbums != null && responseAlbums.size != 0){
                albums.addAll(responseAlbums)
                if(responseAlbums.size == Constants.LIMIT){
                    offset += Constants.LIMIT
                }

                listAlbums.addAll(albums.sortedBy { it.collectionName })
                responseStatus.postValue(true)
                albumsLiveData.postValue(listAlbums)
            }
        }
    }

    fun cancelAllRequests() = coroutinesContext.cancel()

}