package com.example.calorease.data.response

import com.google.gson.annotations.SerializedName

data class FoodResponse(

	@field:SerializedName("FoodResponse")
	val foodResponse: List<FoodResponseItem>
)

data class FoodResponseItem(

	@field:SerializedName("carbohydrates")
	val carbohydrates: String,

	@field:SerializedName("protein")
	val protein: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("fat")
	val fat: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("calories")
	val calories: String
)
