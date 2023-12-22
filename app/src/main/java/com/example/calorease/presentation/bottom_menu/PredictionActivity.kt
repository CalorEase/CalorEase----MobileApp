package com.example.calorease.presentation.bottom_menu

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import com.example.calorease.R
import com.example.calorease.databinding.ActivityPredictionBinding
import com.example.calorease.presentation.bottom_menu.AddImageActivty.Companion.CAMERAX_RESULT

class PredictionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPredictionBinding
    private lateinit var currentImageUri: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPredictionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(this, AddImageActivty::class.java)
        launcherIntentCameraX.launch(intent)
        showImage()
    }

    private fun showImage() {
        currentImageUri = intent.getStringExtra("currentImageUri")!!
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
        }
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERAX_RESULT) {
            currentImageUri = it.data?.getStringExtra(AddImageActivty.EXTRA_CAMERAX_IMAGE)?.toUri().toString()
            showImage()
        }
    }
}