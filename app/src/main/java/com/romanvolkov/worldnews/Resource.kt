package com.romanvolkov.worldnews

sealed class Resource<T> {

    data class Data<T>(
        val data: T
    ): Resource<T>()

    data class Error<T>(
        val error: Throwable
    ): Resource<T>()
}