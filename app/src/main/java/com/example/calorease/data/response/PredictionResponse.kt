package com.example.calorease.data.response

import com.google.gson.annotations.SerializedName

data class PredictionResponse(

	@field:SerializedName("data")
	val data: DataPrediction,

	@field:SerializedName("status")
	val status: StatusPrediction
)

data class DataPrediction(

	@field:SerializedName("prediction")
	val prediction: List<PredictionItem>
)

data class PredictionItem(

	@field:SerializedName("jumlah")
	val jumlah: String,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("id")
	val id: String
)

data class StatusPrediction(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("message")
	val message: String
)
