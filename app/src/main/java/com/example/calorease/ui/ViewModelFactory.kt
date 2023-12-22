package com.example.calorease.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.calorease.ui.breakfast.BreakfastViewModel
import com.example.calorease.ui.dinner.DinnerViewModel
import com.example.calorease.ui.lunch.LunchViewModel

class ViewModelFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BreakfastViewModel::class.java)) {
            return BreakfastViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(LunchViewModel::class.java)) {
            return LunchViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(DinnerViewModel::class.java)) {
            return DinnerViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}