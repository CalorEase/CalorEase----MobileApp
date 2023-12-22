package com.example.calorease.ui.dinner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calorease.R
import com.example.calorease.databinding.ActivityBreakfastBinding
import com.example.calorease.databinding.ActivityDinnerBinding
import com.example.calorease.ui.ViewModelFactory
import com.example.calorease.ui.breakfast.BreakfastAdapter
import com.example.calorease.ui.breakfast.BreakfastViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class DinnerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDinnerBinding
    private lateinit var adapter: DinnerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDinnerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = DinnerAdapter()

        binding.rvDinner.setHasFixedSize(true)
        binding.rvDinner.layoutManager = LinearLayoutManager(this)
        binding.rvDinner.adapter = adapter


        val dinnerViewModel = obtainViewModel(this@DinnerActivity)
        dinnerViewModel.getAllDinner().observe(this){dinnerList ->
            if (dinnerList != null){
                adapter.setListDinner(dinnerList)
                Log.d("dinnerList", dinnerList.toString())
            }
        }
    }
    private fun obtainViewModel(activity: AppCompatActivity): DinnerViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DinnerViewModel::class.java)
    }
}