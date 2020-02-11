package com.example.itunessearch

import android.app.Application
import com.example.itunessearch.di.annotation.AlbumsRepositoryScope
import com.example.itunessearch.di.annotation.ApplicationScope
import com.example.itunessearch.di.module.RepositoryModule
import toothpick.Scope
import toothpick.ktp.KTP
import toothpick.ktp.binding.bind
import toothpick.ktp.binding.module


class ITunesSearchApplication: Application(){

    private lateinit var scope: Scope

    override fun onCreate() {
        super.onCreate()

        scope = KTP.openScope(ApplicationScope::class.java)
            .installModules(module {
                bind<Application>().toInstance { this@ITunesSearchApplication }
            })
            .openSubScope(AlbumsRepositoryScope::class.java){
                scope: Scope ->
                scope.installModules(RepositoryModule())
            }
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        scope.release()
    }
}