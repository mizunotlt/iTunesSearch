package com.example.itunessearch.api


import com.example.itunessearch.data.ResponseAlbums
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

import retrofit2.http.Query

/*
    &term=all&country=US&media=music&entity=album&limit=10&offset=0
 */
interface AlbumsApi {
    @GET("search")
    fun getAlbumsAsync(
        @Query("term") term: String,
        @Query("country") country: String,
        @Query ("media") media: String,
        @Query ("entity") entity: String,
        @Query ("limit") limit: Int): Deferred<Response<ResponseAlbums>>

    @GET("search")
    fun getAlbumsAsync(
        @Query("term") term: String,
        @Query("country") country: String,
        @Query ("media") media: String,
        @Query ("entity") entity: String,
        @Query ("attribute") attribute: String,
        @Query ("limit") limit: Int,
        @Query ("offset") offset: Int): Deferred<Response<ResponseAlbums>>
}
