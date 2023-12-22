package com.example.calorease.data.retrofit

import com.example.calorease.data.response.FoodResponse
import com.example.calorease.data.response.FoodResponseItem
import retrofit2.Call
import retrofit2.http.GET

interface ApiService2 {
    @GET("makanan")
    fun getFood(
    ): Call<List<FoodResponseItem>>
}