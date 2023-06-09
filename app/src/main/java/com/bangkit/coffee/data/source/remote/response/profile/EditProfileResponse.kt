package com.bangkit.coffee.data.source.remote.response.profile

import com.google.gson.annotations.SerializedName

data class EditProfileResponse(

	@field:SerializedName("data")
	val data: EditProfileResponseData,

	@field:SerializedName("message")
	val message: String
)

data class EditProfileResponseData(

	@field:SerializedName("name")
	val name: String
)
