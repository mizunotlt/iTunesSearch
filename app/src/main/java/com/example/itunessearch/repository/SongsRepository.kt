package com.example.itunessearch.repository

import com.example.itunessearch.api.SongsApi
import com.example.itunessearch.data.SongsData

class SongsRepository(private val api: SongsApi): BaseRepository(){
    /*
        Function for getting songs from api iTunes
        lookup request
        Function is suspending
     */
    suspend fun getSongsList(id: Int): ArrayList<SongsData>?{
        val songsResponse = safeApiCall(
            call = { api.getSongsAsync(id,"song").await()},
            errorMessage = "Error Getting songs from api"
        )
        return songsResponse?.results
    }
}