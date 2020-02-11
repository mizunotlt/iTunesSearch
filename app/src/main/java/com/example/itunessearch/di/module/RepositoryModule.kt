package com.example.itunessearch.di.module

import com.example.itunessearch.api.ClientForApi
import com.example.itunessearch.repository.AlbumsRepository
import com.example.itunessearch.repository.SongsRepository
import toothpick.config.Module
import toothpick.ktp.binding.bind

class RepositoryModule: Module() {
    private val client = ClientForApi()
    init {
        bind<AlbumsRepository>().toInstance{
            AlbumsRepository(client.getApiAlbums())
        }

        bind<SongsRepository>().toInstance{
            SongsRepository(client.getApiSongs())
        }

    }
}