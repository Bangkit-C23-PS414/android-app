package com.bangkit.coffee.data.source.remote

import com.bangkit.coffee.data.source.remote.model.LoginUser
import com.bangkit.coffee.data.source.remote.model.RegisterUser
import com.bangkit.coffee.data.source.remote.response.auth.LoginResponse
import com.bangkit.coffee.data.source.remote.response.auth.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("auth/register")
    suspend fun register(
        @Body registerUser: RegisterUser
    ): RegisterResponse

    @POST("auth/login")
    suspend fun login(
        @Body loginUser: LoginUser,
    ): LoginResponse

    @POST("auth/forgot-password")
    suspend fun forgotPassword(
        @Body email: String,
    ): Response<Unit>

}