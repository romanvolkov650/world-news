package com.romanvolkov.worldnews.model

import com.romanvolkov.worldnews.entity.NewsEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("top-headlines")
    fun getTopNews(
        @Query("country") country: String = "ua",
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = 3,
        @Query("apiKey") apiKey: String = "8d0727bdfb7c443a994cfaf1642a7b5d"
    ): Observable<NewsEntity>
}
