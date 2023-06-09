package com.bangkit.coffee.data.source.remote

import com.bangkit.coffee.data.source.remote.model.RemoteUser
import com.bangkit.coffee.data.source.remote.response.profile.EditProfileResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ProfileService {

    @GET("/profile/me")
    suspend fun get(): RemoteUser

    @FormUrlEncoded
    @POST("/profile/edit")
    suspend fun edit(
        @Field("name") name: String,
    ) : EditProfileResponse
}