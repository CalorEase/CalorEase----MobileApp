package com.example.calorease.ui.breakfast

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.calorease.data.database.Breakfast
import com.example.calorease.data.database.BreakfastDao
import com.example.calorease.data.database.CalorEaseDatabase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class BreakfastRepository(application: Application) {
    private val breakfastDao: BreakfastDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    val auth = Firebase.auth
    val user = auth.currentUser
    val userId = user?.uid

    init{
        val db = CalorEaseDatabase.getDatabase(application)
        breakfastDao = db.breakfastDao()
    }

    fun getAllBreakfast(): LiveData<List<Breakfast>> = breakfastDao.getAllBreakfast(userId.toString())

    fun insert(breakfast: Breakfast){
        executorService.execute{breakfastDao.insert(breakfast)}
    }

    fun update(breakfast: Breakfast){
        executorService.execute{breakfastDao.update(breakfast)}
    }

    fun delete(breakfast: Breakfast){
        executorService.execute { breakfastDao.delete(breakfast) }
    }
}