package com.romanvolkov.worldnews

import android.content.Context

sealed class StringVO {
    abstract fun getString(context: Context): String

    data class Plain(val s: String) : StringVO() {
        override fun getString(context: Context) = s
    }

    data class Resource(val id: Int) : StringVO() {
        override fun getString(context: Context): String {
            return context.getString(id)
        }

    }
}