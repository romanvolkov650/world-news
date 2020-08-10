package com.romanvolkov.worldnews.dagger

import com.romanvolkov.worldnews.model.NewsApi
import com.romanvolkov.worldnews.repository.INewsRepository
import com.romanvolkov.worldnews.repository.NewsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideNewsRepository(api: NewsApi): INewsRepository {
        return NewsRepository(api)
    }
}