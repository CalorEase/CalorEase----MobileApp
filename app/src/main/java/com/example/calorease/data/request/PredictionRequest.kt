package com.example.calorease.data.request

import com.google.gson.annotations.SerializedName

data class PredictionRequest(
    @field:SerializedName("image_path")
    val imgPath : String? = null
)
