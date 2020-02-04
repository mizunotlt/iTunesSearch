package com.example.itunessearch.repository

import com.example.itunessearch.api.AlbumsApi
import com.example.itunessearch.data.AlbumsData
import com.example.itunessearch.utils.Constants

class AlbumsRepository (private val api: AlbumsApi): BaseRepository() {

    /*
        Function for getting album from api iTunes
        Search request
        Function is suspending
     */

    suspend fun getAlbumsListByTerm(term: String, offset: Int): ArrayList<AlbumsData>?{
        val albumResponse = safeApiCall(
            call = { api.getAlbumsAsync(term,"US", "music", "album","albumTerm", Constants.LIMIT, offset).await()},
            errorMessage = "Error Getting albums from api"
        )
        return albumResponse?.results
    }

}