package com.bangkit.coffee.data.source.remote.response.camera

import com.google.gson.annotations.SerializedName

data class CameraResponse(

	@field:SerializedName("data")
	val data: CameraResponseData,

	@field:SerializedName("message")
	val message: String
)

data class CameraResponseData(

	@field:SerializedName("filename")
	val filename: String,

	@field:SerializedName("fileURL")
	val fileURL: String
)
