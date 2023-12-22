package com.example.calorease.ui.lunch

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.calorease.data.database.Breakfast
import com.example.calorease.data.database.Lunch
import com.example.calorease.ui.breakfast.BreakfastRepository

class LunchViewModel(application: Application) : ViewModel() {
    private val lunchRepository: LunchRepository = LunchRepository(application)
    fun getAllLunch(): LiveData<List<Lunch>> = lunchRepository.getAllLunch()
}