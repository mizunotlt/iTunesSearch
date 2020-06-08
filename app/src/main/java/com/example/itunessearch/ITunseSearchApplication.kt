package com.example.itunessearch

import android.app.Application
import android.content.Context
import com.example.itunessearch.di.components.AppComponent
import com.example.itunessearch.di.components.DaggerAppComponent


class ITunesSearchApplication: Application(){

    companion object {
        lateinit var appContext: Context
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        appComponent = initDagger()
    }

    private fun initDagger(): AppComponent {
        return DaggerAppComponent.builder()
            .build()
    }

}