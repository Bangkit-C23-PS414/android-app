package com.bangkit.coffee.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("message")
    val message: String,
)