package com.bangkit.coffee.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseWrapper<T>(
	@field:SerializedName("data")
	val data: T,

	@field:SerializedName("message")
	val message: String
)