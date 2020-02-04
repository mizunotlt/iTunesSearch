package com.example.itunessearch.api

import com.example.itunessearch.utils.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ITunesApiFactory {

    /*
        Building http clients with using OkHttp
     */
    private val iTunesClient = OkHttpClient().newBuilder()
        .addInterceptor(interceptor())
        .build()

    private fun interceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level= HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    private fun retrofit() : Retrofit = Retrofit.Builder()
        .client(iTunesClient)
        .baseUrl(Constants.BASEURL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    /*
        api interface for retrofit requests
     */
    val albumsApi : AlbumsApi = retrofit().create(AlbumsApi::class.java)
    val songsApi: SongsApi = retrofit().create(SongsApi::class.java)
}