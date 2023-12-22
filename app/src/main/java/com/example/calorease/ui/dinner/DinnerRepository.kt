package com.example.calorease.ui.dinner

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.calorease.data.database.Breakfast
import com.example.calorease.data.database.BreakfastDao
import com.example.calorease.data.database.CalorEaseDatabase
import com.example.calorease.data.database.Dinner
import com.example.calorease.data.database.DinnerDao
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class DinnerRepository(application: Application) {
    private val dinnerDao: DinnerDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    val auth = Firebase.auth
    val user = auth.currentUser
    val userId = user?.uid

    init{
        val db = CalorEaseDatabase.getDatabase(application)
        dinnerDao = db.dinnerDao()
    }

    fun getAllDinner(): LiveData<List<Dinner>> = dinnerDao.getAllDinner(userId.toString())

    fun insert(dinner: Dinner){
        executorService.execute{dinnerDao.insert(dinner)}
    }

    fun update(dinner: Dinner){
        executorService.execute{dinnerDao.update(dinner)}
    }

    fun delete(dinner: Dinner){
        executorService.execute { dinnerDao.delete(dinner) }
    }
}