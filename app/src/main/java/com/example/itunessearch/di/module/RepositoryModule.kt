package com.example.itunessearch.di.module

import com.example.itunessearch.api.AlbumsApi
import com.example.itunessearch.api.SongsApi
import com.example.itunessearch.repository.AlbumsRepository
import com.example.itunessearch.repository.SongsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepositoryAlbums(api: AlbumsApi): AlbumsRepository {
        return AlbumsRepository(api)
    }

    @Provides
    fun provideRepositorySongs(api: SongsApi): SongsRepository{
        return SongsRepository(api)
    }


}