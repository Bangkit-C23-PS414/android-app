package com.bangkit.coffee.util.exception

import com.bangkit.coffee.data.source.remote.response.ErrorResponse
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response

open class NetworkErrorException(
    val errorCode: Int = -1,
    val errorMessage: String,
    val response: String = ""
) : Exception() {
    override val message: String
        get() = localizedMessage

    override fun getLocalizedMessage(): String {
        return errorMessage
    }

    companion object {
        fun parseException(e: HttpException): NetworkErrorException {
            return try {
                // Guard response
                val response = e.response()
                if (response !is Response) throw e
                // Guard response body
                val error = response.errorBody()
                if (error !is ResponseBody) throw e
                // Close stream
                error.close()
                // Parse error
                val parsedError = Gson().fromJson(
                    error.charStream(),
                    ErrorResponse::class.java,
                )
                // Create error
                NetworkErrorException(response.code(), parsedError.message)
            } catch (_: Exception) {
                NetworkErrorException(e.code(), "Unexpected Error")
            }
        }
    }
}