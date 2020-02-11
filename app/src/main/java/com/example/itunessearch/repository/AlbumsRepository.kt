package com.example.itunessearch.repository

import com.example.itunessearch.api.AlbumsApi
import com.example.itunessearch.data.AlbumsData
import com.example.itunessearch.di.annotation.ApplicationScope
import com.example.itunessearch.utils.Constants
import toothpick.InjectConstructor
import javax.inject.Singleton

@Singleton
@InjectConstructor
@ApplicationScope
open class AlbumsRepository (private val apiAl: AlbumsApi): BaseRepository() {

    /*
        Function for getting album from api iTunes
        Search request
        Function is suspending
     */

    suspend fun getAlbumsListByTerm(term: String, offset: Int): ArrayList<AlbumsData>?{
        val albumResponse = safeApiCall(
            call = { apiAl.getAlbumsAsync(term,"US", "music", "album","albumTerm", Constants.LIMIT, offset).await()},
            errorMessage = "Error Getting albums from api"
        )
        return albumResponse?.results
    }

}