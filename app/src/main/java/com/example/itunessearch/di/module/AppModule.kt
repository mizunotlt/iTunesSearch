package com.example.itunessearch.di.module

import android.content.Context
import com.example.itunessearch.ITunesSearchApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun providesApplicationContext(app: ITunesSearchApplication): Context = app.applicationContext

}