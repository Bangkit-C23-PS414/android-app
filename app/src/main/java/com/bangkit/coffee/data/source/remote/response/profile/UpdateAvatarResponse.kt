package com.bangkit.coffee.data.source.remote.response.profile

import com.google.gson.annotations.SerializedName

data class UpdateAvatarResponse(

	@field:SerializedName("data")
	val data: UpdateAvatarResponseData,

	@field:SerializedName("message")
	val message: String
)

data class UpdateAvatarResponseData(

	@field:SerializedName("avatarUrl")
	val avatarUrl: String,

	@field:SerializedName("blurHash")
	val blurHash: String
)
