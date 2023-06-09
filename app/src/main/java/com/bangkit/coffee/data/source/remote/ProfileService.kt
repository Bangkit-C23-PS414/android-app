package com.bangkit.coffee.data.source.remote

import com.bangkit.coffee.data.source.remote.model.RemoteUser
import com.bangkit.coffee.data.source.remote.response.profile.ChangePasswordResponse
import com.bangkit.coffee.data.source.remote.response.profile.EditProfileResponse
import com.bangkit.coffee.data.source.remote.response.profile.UpdateAvatarResponse
import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ProfileService {

    @GET("/profile/me")
    suspend fun get(): RemoteUser

    @FormUrlEncoded
    @POST("/profile/edit")
    suspend fun edit(
        @Field("name") name: String,
    ): EditProfileResponse

    @Multipart
    @POST("/profile/update-avatar")
    suspend fun updateAvatar(
        @Part avatar: MultipartBody.Part
    ) : UpdateAvatarResponse

    @FormUrlEncoded
    @POST("/profile/change-password")
    suspend fun changePassword(
        @Field("oldPassword") oldPassword: String,
        @Field("newPassword") newPassword: String,
        @Field("confirmNewPassword") confirmNewPassword: String,
    ): ChangePasswordResponse
}