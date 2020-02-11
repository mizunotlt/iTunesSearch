package com.example.itunessearch.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.itunessearch.data.SongsData
import com.example.itunessearch.di.module.RepositoryModule
import com.example.itunessearch.di.annotation.SongsRepositoryScope
import com.example.itunessearch.di.annotation.SongsViewModelScope
import com.example.itunessearch.repository.SongsRepository
import kotlinx.coroutines.*
import toothpick.Scope
import toothpick.ktp.KTP
import toothpick.ktp.delegate.inject
import kotlin.coroutines.CoroutineContext


class SongsModels: ViewModel() {

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private val  repository: SongsRepository by inject()

    init {
        KTP.openScopes(SongsViewModelScope::class.java)
            .openSubScope(SongsRepositoryScope::class.java){
                    scope: Scope ->
                    scope.installModules(
                        RepositoryModule()
                    )
            }
            .inject(this)
    }

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