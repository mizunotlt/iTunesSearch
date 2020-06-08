package com.example.itunessearch.di.components

import com.example.itunessearch.activity.MainActivity
import com.example.itunessearch.di.module.AppModule
import com.example.itunessearch.di.module.NetworkModule
import com.example.itunessearch.di.module.RepositoryModule
import com.example.itunessearch.di.module.ViewModelModule
import com.example.itunessearch.fragments.AlbumsViewFragment
import com.example.itunessearch.fragments.SongsViewFragment
import com.example.itunessearch.models.AlbumsModels
import com.example.itunessearch.models.SongsModels
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, NetworkModule::class, RepositoryModule::class, ViewModelModule::class])
@Singleton
interface AppComponent{

    //Inject Activity
    fun inject(activity: MainActivity)

    //Inject Fragments
    fun inject(albumsFragment: AlbumsViewFragment)
    fun inject(songsViewFragment: SongsViewFragment)

    //Inject viewModels
    fun inject(albumsModels: AlbumsModels)
    fun inject(songsModels: SongsModels)

}