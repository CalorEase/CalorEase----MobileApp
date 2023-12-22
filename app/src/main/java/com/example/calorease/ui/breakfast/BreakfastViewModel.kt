package com.example.calorease.ui.breakfast

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.calorease.data.database.Breakfast

class BreakfastViewModel(application: Application) : ViewModel() {
    private val breakfastRepository: BreakfastRepository = BreakfastRepository(application)
    fun getAllBreakfast(): LiveData<List<Breakfast>> = breakfastRepository.getAllBreakfast()
}