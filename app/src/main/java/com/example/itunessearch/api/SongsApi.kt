package com.example.itunessearch.api

import com.example.itunessearch.data.ResponseSongs
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

import retrofit2.http.Query

/*
    lookup?id=id&entity=song
 */
interface SongsApi {
    @GET("lookup")
    fun getSongsAsync(@Query("id") id: Int,
                      @Query("entity") entity: String): Deferred<Response<ResponseSongs>>
}