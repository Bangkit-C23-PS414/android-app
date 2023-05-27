package com.bangkit.coffee.util

import com.auth0.android.jwt.JWT

fun tryParseJWT(token: String?): JWT? {
    return try {
        if (token.isNullOrBlank()) null else JWT(token)
    } catch (e: Exception) {
        return null
    }
}