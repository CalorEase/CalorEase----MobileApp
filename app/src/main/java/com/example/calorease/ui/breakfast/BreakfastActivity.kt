package com.example.calorease.ui.breakfast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calorease.data.database.CalorEaseDatabase
import com.example.calorease.databinding.ActivityBreakfastBinding
import com.example.calorease.ui.ViewModelFactory
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class BreakfastActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBreakfastBinding
    private lateinit var adapter: BreakfastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBreakfastBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = BreakfastAdapter()

        binding.rvBreakfast.setHasFixedSize(true)
        binding.rvBreakfast.layoutManager = LinearLayoutManager(this)
        binding.rvBreakfast.adapter = adapter

//        val auth = Firebase.auth
//        val user = auth.currentUser
//        val userId = user?.uid

        val breakfastViewModel = obtainViewModel(this@BreakfastActivity)
        breakfastViewModel.getAllBreakfast().observe(this){breakfastList ->
            if (breakfastList != null){
                adapter.setListBreakfast(breakfastList)
                Log.d("breakfastList", breakfastList.toString())
            }
        }
    }
    private fun obtainViewModel(activity: AppCompatActivity): BreakfastViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(BreakfastViewModel::class.java)
    }
}