package com.bangkit.coffee.shared.util

import com.bangkit.coffee.data.source.remote.response.ErrorResponse
import com.google.gson.Gson
import retrofit2.HttpException

fun HttpException.parse(): ErrorResponse {
    return try {
        response()?.errorBody()?.let { responseBody ->
            responseBody.close()
            Gson().fromJson(
                responseBody.charStream(),
                ErrorResponse::class.java,
            )
        } ?: ErrorResponse("Something went wrong during fetch network")
    } catch (_: Exception) {
        ErrorResponse("Something went wrong during fetch network")
    }
}
