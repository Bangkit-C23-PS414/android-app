package com.bangkit.coffee.data.source.remote.response.profile

import com.google.gson.annotations.SerializedName

data class ChangePasswordResponse(

	@field:SerializedName("message")
	val message: String
)
