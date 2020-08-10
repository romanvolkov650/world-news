package com.romanvolkov.worldnews.dagger

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Singleton
    @Provides
    fun provideContext(application: Application): Context {
        return application
    }
}