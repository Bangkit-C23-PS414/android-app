package com.bangkit.coffee.data.source.remote

import com.bangkit.coffee.data.source.remote.model.LoginUser
import com.bangkit.coffee.data.source.remote.model.RegisterUser
import com.bangkit.coffee.data.source.remote.response.auth.AuthResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("auth/register")
    suspend fun register(
        @Body registerUser: RegisterUser
    ): AuthResponse

    @POST("auth/login")
    suspend fun login(
        @Body loginUser: LoginUser,
    ): AuthResponse
}