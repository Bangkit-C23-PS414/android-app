package com.bangkit.coffee.data.source.remote

import com.bangkit.coffee.data.source.remote.response.profile.ProfileResponse
import retrofit2.http.GET

interface ProfileService {

    @GET("/profile/me")
    suspend fun get(): ProfileResponse
}