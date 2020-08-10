package com.romanvolkov.worldnews.repository

import com.romanvolkov.worldnews.Resource
import com.romanvolkov.worldnews.entity.NewsEntity
import com.romanvolkov.worldnews.model.NewsApi
import io.reactivex.Observable


interface INewsRepository {
    fun getTopNews(page: Int): Observable<Resource<NewsEntity>>
}

class NewsRepository(private val api: NewsApi) : INewsRepository {
    override fun getTopNews(page: Int): Observable<Resource<NewsEntity>> {
        return api
            .getTopNews(page = page)
            .map { Resource.Data(it) as Resource<NewsEntity> }
            .onErrorReturn { Resource.Error(it) }
    }
}