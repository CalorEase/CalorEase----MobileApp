package com.example.calorease.data.response

import com.google.gson.annotations.SerializedName

data class UploadResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("status")
	val status: Status
)

data class Status(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("message")
	val message: String
)

data class Data(

	@field:SerializedName("image_path")
	val imagePath: String
)
