package com.example.itunessearch.di.module

import com.example.itunessearch.models.AlbumsModels
import com.example.itunessearch.models.SongsModels
import com.example.itunessearch.repository.AlbumsRepository
import com.example.itunessearch.repository.SongsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelModule {
    @Provides
    @Singleton
    fun provideAlbumsViewModels(repository: AlbumsRepository): AlbumsModels {
        return AlbumsModels(repository)
    }

    @Provides
    @Singleton
    fun provideSongsViewModels(repository: SongsRepository): SongsModels {
        return SongsModels(repository)
    }
}