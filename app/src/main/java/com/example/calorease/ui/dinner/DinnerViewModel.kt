package com.example.calorease.ui.dinner

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.calorease.data.database.Dinner

class DinnerViewModel(application: Application) : ViewModel() {
    private val dinnerRepository: DinnerRepository = DinnerRepository(application)
    fun getAllDinner(): LiveData<List<Dinner>> = dinnerRepository.getAllDinner()
}