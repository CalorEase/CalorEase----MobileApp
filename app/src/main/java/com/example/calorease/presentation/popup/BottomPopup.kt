package com.example.calorease.presentation.popup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.calorease.data.database.Breakfast
import com.example.calorease.data.database.BreakfastDao
import com.example.calorease.data.database.CalorEaseDatabase
import com.example.calorease.data.database.Dinner
import com.example.calorease.data.database.FoodRoomDatabase
import com.example.calorease.data.database.Lunch
import com.example.calorease.data.response.FoodResponseItem
import com.example.calorease.data.retrofit.ApiConfig2
import com.example.calorease.databinding.BottomPopupBinding
import com.example.calorease.utils.SessionManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BottomPopup : BottomSheetDialogFragment() {

    private lateinit var binding: BottomPopupBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var foodName: String

    private lateinit var database: CalorEaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomPopupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = context?.let { SessionManager(it) }!!

        foodName = sessionManager.fetchFoodName().toString()
        val foodAmount = sessionManager.fetchFoodAmount()

        binding.resultNama.text = foodName
        binding.tvFoodAmount.text = "$foodAmount Portion(s)"
        binding.tvMeal.text = foodName

        database = CalorEaseDatabase.getDatabase(requireContext().applicationContext)

        showCalories()
    }

    private fun showCalories(){
        val client = ApiConfig2.getApiService().getFood()
        client.enqueue(object : Callback<List<FoodResponseItem>> {
            override fun onResponse(
                call: Call<List<FoodResponseItem>>,
                response: Response<List<FoodResponseItem>>
            ) {
//                val foodArray = response.body()?.foodResponse?.get(response.body()!!.foodResponse.size) as List<FoodResponseItem>
//                val foodArray = response.body()?.foodResponse?.get(0)?.name as List<FoodResponseItem>

                val foodArray = response.body()

                if (!foodArray.isNullOrEmpty()) {
                    val firstFoodItem = foodArray
                    val itemName = firstFoodItem
                    for (item in firstFoodItem){
                        if (item.name == foodName){
                            binding.tvCaloriesAmount.text = item.calories
                            binding.tvFoodFat.text = item.fat
                            binding.tvFoodCarb.text = item.carbohydrates
                            binding.tvFoodProtein.text = item.protein

                            val auth = Firebase.auth
                            val user = auth.currentUser

                            val id = 0
                            val userid = user?.uid.toString()
                            Log.d("tenant", userid)
                            val username = user?.displayName
                            val foodName = item.name

                            val foodAmount = sessionManager.fetchFoodAmount()!!.toInt()

                            val foodCaloriesInt = extractNumberFromString(item.calories)
                            val foodCalories = (foodCaloriesInt?.times(foodAmount)).toString()

                            val foodFatInt = extractNumberFromString(item.fat)
                            val foodFat = (foodFatInt?.times(foodAmount)).toString()

                            val foodCarboInt = extractNumberFromString(item.carbohydrates)
                            val foodCarbo = (foodCarboInt?.times(foodAmount)).toString()

                            val foodProteinInt = extractNumberFromString(item.protein)
                            val foodProtein = (foodProteinInt?.times(foodAmount)).toString()

                            Log.d("jumlah makan", foodCaloriesInt.toString())

                            binding.btnBreakfast.setOnClickListener {
                                var breakfast =
                                    Breakfast(id, userid, username, foodName, foodAmount.toString(), foodCalories, foodFat, foodCarbo, foodProtein)
                                GlobalScope.launch(Dispatchers.IO) {
                                    database.breakfastDao().insert(breakfast)
                                }
                                Toast.makeText(context, foodName + "Added to Breakfast", Toast.LENGTH_SHORT).show()
                            }

                            binding.btnLunch.setOnClickListener {
                                var lunch =
                                    Lunch(id, userid, username, foodName, foodAmount.toString(), foodCalories, foodFat, foodCarbo, foodProtein)
                                GlobalScope.launch(Dispatchers.IO) {
                                    database.lunchDao().insert(lunch)
                                }
                                Toast.makeText(context, foodName + "Added to Lunch", Toast.LENGTH_SHORT).show()
                            }

                            binding.btnDinner.setOnClickListener {
                                var dinner =
                                    Dinner(id, userid, username, foodName, foodAmount.toString(), foodCalories, foodFat, foodCarbo, foodProtein)
                                GlobalScope.launch(Dispatchers.IO) {
                                    database.dinnerDao().insert(dinner)
                                }
                                Toast.makeText(context, foodName + "Added to Dinner", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    Log.d("haha", itemName.toString())


//                    for (item in firstFoodItem){
//                        Log.d("haha1", item.name)
//                    }
                } else {
                    showToast("No food items received.")
                }

            }

            override fun onFailure(call: Call<List<FoodResponseItem>>, t: Throwable) {
                showToast(t.message.toString())
            }
        })
    }

    fun extractNumberFromString(input: String): Int? {
        val regex = Regex("""(\d+)""")
        val matchResult = regex.find(input)

        return matchResult?.value?.toInt()
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}