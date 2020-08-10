package com.romanvolkov.worldnews.news

import com.romanvolkov.worldnews.IValueObject
import com.romanvolkov.worldnews.StringVO

data class NewsItem(
    val id: String,
    val title: StringVO,
    val description: StringVO,
    val source: StringVO,
    val url: String,
    val imageUrl: String?,
    val publishedAt: String
) : IValueObject