package com.example.calorease.presentation.popup

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.calorease.R
import com.example.calorease.data.request.PredictionRequest
import com.example.calorease.data.response.PredictionResponse
import com.example.calorease.data.retrofit.ApiConfig
import com.example.calorease.databinding.BottomPopupBinding
import com.example.calorease.presentation.bottom_menu.CameraFragment
import com.example.calorease.presentation.bottom_menu.HomeFragment
import com.example.calorease.ui.HomepageActivity
import com.example.calorease.utils.SessionManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BottomPopup : BottomSheetDialogFragment() {

    private lateinit var binding: BottomPopupBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomPopupBinding.inflate(inflater, container, false)
        return binding.root

        val data = arguments
        var foodName = data?.getString("food_name")
        binding.resultNama.text = foodName
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = context?.let { SessionManager(it) }!!
        predictImage()
    }

    private fun predictImage(){
        val client = ApiConfig.getApiService().predictionImage( PredictionRequest("${sessionManager.fetchPredictionImage()}"))
        client.enqueue(object : Callback<PredictionResponse> {
            override fun onResponse(
                call: Call<PredictionResponse>,
                response: Response<PredictionResponse>
            ) {
                showToast(response.body()?.data?.prediction!!.size.toString())
                var foodName = response.body()?.data?.prediction!![0].nama
                var amount = response.body()?.data?.prediction!![0].jumlah
                binding.resultNama.text = foodName
                binding.tvFoodAmount.text = amount

                val bundle = Bundle()
                bundle.putString("food_name", foodName)
                val newFragment = BottomPopup()
                newFragment.arguments = bundle
            }

            override fun onFailure(call: Call<PredictionResponse>, t: Throwable) {
                showToast(t.message.toString())
            }
        })
    }

    private fun showCalories(){
        val client = ApiConfig.getApiService().predictionImage( PredictionRequest("${sessionManager.fetchPredictionImage()}"))
        client.enqueue(object : Callback<PredictionResponse> {
            override fun onResponse(
                call: Call<PredictionResponse>,
                response: Response<PredictionResponse>
            ) {
                showToast(response.body()?.data?.prediction!!.size.toString())
                var foodName = response.body()?.data?.prediction!![0].nama
                var amount = response.body()?.data?.prediction!![0].jumlah
                binding.resultNama.text = foodName
                binding.tvFoodAmount.text = amount

                val bundle = Bundle()
                bundle.putString("food_name", foodName)
                val newFragment = BottomPopup()
                newFragment.arguments = bundle
            }

            override fun onFailure(call: Call<PredictionResponse>, t: Throwable) {
                showToast(t.message.toString())
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}