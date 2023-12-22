package com.example.calorease.ui.lunch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calorease.R
import com.example.calorease.databinding.ActivityBreakfastBinding
import com.example.calorease.databinding.ActivityLunchBinding
import com.example.calorease.ui.ViewModelFactory
import com.example.calorease.ui.breakfast.BreakfastAdapter
import com.example.calorease.ui.breakfast.BreakfastViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LunchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLunchBinding
    private lateinit var adapter: LunchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLunchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = LunchAdapter()

        binding.rvLunch.setHasFixedSize(true)
        binding.rvLunch.layoutManager = LinearLayoutManager(this)
        binding.rvLunch.adapter = adapter

        val lunchViewModel = obtainViewModel(this@LunchActivity)
        lunchViewModel.getAllLunch().observe(this){lunchList ->
            if (lunchList != null){
                adapter.setListLunch(lunchList)
                Log.d("lunchList", lunchList.toString())
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): LunchViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(LunchViewModel::class.java)
    }
}