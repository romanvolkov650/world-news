package com.romanvolkov.worldnews.entity

data class NewsEntity(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)

data class Article(
    val source: Source,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String
)

data class Source(
    val id: String?,
    val name: String
)