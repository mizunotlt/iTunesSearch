package com.example.itunessearch.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.itunessearch.api.ITunesApiFactory
import com.example.itunessearch.data.SongsData
import com.example.itunessearch.repository.SongsRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SongsModels: ViewModel() {

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private val repository : SongsRepository = SongsRepository(ITunesApiFactory.songsApi)

    val songsLiveData = MutableLiveData<ArrayList<SongsData>>().apply { value = arrayListOf() }
    val songsDataForAdapter = MutableLiveData<ArrayList<SongsData>>().apply { value = arrayListOf() }

    /*
        Function for getting tracks from api
        @Param - id where id = collectionId
     */
    fun getSongs(id: Int){
        scope.launch {
            val songs = repository.getSongsList(id)
            songsLiveData.postValue(songs)
            songsDataForAdapter.postValue(songs?.filter { it.wrapperType != "collection" } as ArrayList<SongsData>?)
        }
    }

    fun cancelAllRequests() = coroutineContext.cancel()


}