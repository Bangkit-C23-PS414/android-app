package com.bangkit.coffee.data.source.remote

import com.bangkit.coffee.data.source.remote.model.ForgotPasswordUser
import com.bangkit.coffee.data.source.remote.model.LoginUser
import com.bangkit.coffee.data.source.remote.model.RegisterUser
import com.bangkit.coffee.data.source.remote.model.ResetPasswordRequest
import com.bangkit.coffee.data.source.remote.model.VerifyCodeUser
import com.bangkit.coffee.data.source.remote.response.auth.LoginResponse
import com.bangkit.coffee.data.source.remote.response.auth.RegisterResponse
import com.bangkit.coffee.data.source.remote.response.auth.VerifyOTPResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
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
        @Body forgotPasswordUser: ForgotPasswordUser,
    ): Response<Unit>

    @POST("auth/verify-code")
    suspend fun verifyCode(
        @Body verifyCodeUser: VerifyCodeUser,
    ): Response<VerifyOTPResponse>

    @POST("auth/reset-password")
    suspend fun resetPassword(
        @Header("Authorization") token: String,
        @Body newPassword: ResetPasswordRequest,
    ): Response<Unit>

}