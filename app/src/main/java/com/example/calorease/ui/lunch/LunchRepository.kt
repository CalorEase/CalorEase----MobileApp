package com.example.calorease.ui.lunch

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.calorease.data.database.Breakfast
import com.example.calorease.data.database.BreakfastDao
import com.example.calorease.data.database.CalorEaseDatabase
import com.example.calorease.data.database.Lunch
import com.example.calorease.data.database.LunchDao
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class LunchRepository(application: Application) {
    private val lunchDao: LunchDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    val auth = Firebase.auth
    val user = auth.currentUser
    val userId = user?.uid

    init{
        val db = CalorEaseDatabase.getDatabase(application)
        lunchDao = db.lunchDao()
    }

    fun getAllLunch(): LiveData<List<Lunch>> = lunchDao.getAllLunch(userId.toString())

    fun insert(lunch: Lunch){
        executorService.execute{lunchDao.insert(lunch)}
    }

    fun update(lunch: Lunch){
        executorService.execute{lunchDao.update(lunch)}
    }

    fun delete(lunch: Lunch){
        executorService.execute { lunchDao.delete(lunch) }
    }
}