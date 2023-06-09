package com.bangkit.coffee.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteUser(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("avatarUrl")
    val avatarUrl: String?,

    @field:SerializedName("blurHash")
    val blurHash: String?
)
