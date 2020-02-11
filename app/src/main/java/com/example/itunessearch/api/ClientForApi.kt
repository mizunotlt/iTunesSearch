package com.example.itunessearch.api

import com.example.itunessearch.utils.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Singleton
class ClientForApi {

    private val albumsApi: AlbumsApi
    private val songsApi: SongsApi


    init {
        val iTunesClient = OkHttpClient().newBuilder()
            .addInterceptor(interceptor())
            .build()

        /*
            api interface for retrofit requests
         */
        albumsApi = Retrofit.Builder()
            .client(iTunesClient)
            .baseUrl(Constants.BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build().create(AlbumsApi::class.java)

        songsApi = Retrofit.Builder()
            .client(iTunesClient)
            .baseUrl(Constants.BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(SongsApi::class.java)


    }

    private fun interceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level= HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    fun getApiAlbums() = albumsApi
    fun getApiSongs() = songsApi
}