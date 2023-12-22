package com.example.calorease.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.calorease.R
import com.example.calorease.presentation.sign_in.SignInActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lifecycleScope.launch(Dispatchers.Default){
            delay(2000)
            val intent = Intent(this@SplashActivity, SignInActivity ::class.java)
            startActivity(intent)
            finish()
        }
    }
}