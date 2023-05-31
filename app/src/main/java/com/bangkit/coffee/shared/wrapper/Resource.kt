package com.bangkit.coffee.shared.wrapper

sealed class Resource<out T>(
    open val message: String,
    open val data: T? = null
) {
    data class Error(
        override val message: String
    ) : Resource<Nothing>(message, null)

    data class Success<T>(
        override val data: T
    ) : Resource<T>("Success", data)
}