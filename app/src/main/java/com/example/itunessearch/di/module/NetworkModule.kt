package com.example.itunessearch.di.module

import com.example.itunessearch.api.AlbumsApi
import com.example.itunessearch.api.SongsApi
import com.example.itunessearch.utils.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        return client.build()
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constants.BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    internal fun provideAlbumsApi(retrofitClient: Retrofit): AlbumsApi {
        return retrofitClient.create(AlbumsApi::class.java)
    }

    @Provides
    @Singleton
    internal fun provideSongApi(retrofitClient: Retrofit): SongsApi{
        return retrofitClient.create(SongsApi::class.java)
    }

}