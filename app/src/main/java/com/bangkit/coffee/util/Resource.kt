package com.bangkit.coffee.util

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    data class Error(val message: String) : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
}
