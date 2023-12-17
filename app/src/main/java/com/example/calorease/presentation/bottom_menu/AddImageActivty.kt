package com.example.calorease.presentation.bottom_menu

import android.Manifest
import android.content.ContentValues.TAG
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.core.content.ContextCompat
import com.example.calorease.databinding.ActivityAddImageActivtyBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

class AddImageActivty : AppCompatActivity() {

    private lateinit var binding: ActivityAddImageActivtyBinding

    private var imageCapture: ImageCapture? = null
    private var currentImageUri: Uri? = null

//    private val requestPermissionLauncher =
//        registerForActivityResult(
//            ActivityResultContracts.RequestPermission()
//        ) { isGranted: Boolean ->
//            if (isGranted) {
//                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
//            } else {
//                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
//            }
//        }
//
//    private fun allPermissionsGranted() =
//        ContextCompat.checkSelfPermission(
//            this,
//            REQUIRED_PERMISSION
//        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddImageActivtyBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        if (!allPermissionsGranted()) {
//            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
//        }

        binding.captureImage.setOnClickListener {
            takePicture()
        }
    }

    private fun takePicture(){
        val imageFolder = File(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyImages")
        if (!imageFolder.exists()){
            imageFolder.mkdir()
        }
        val fileName = SimpleDateFormat("yyyy_MM_dd", Locale.getDefault())
            .format(System.currentTimeMillis()) + ".jpg"
        val imageFile = File(imageFolder, fileName)
        val outputOption = ImageCapture.OutputFileOptions.Builder(imageFile).build()

        imageCapture?.takePicture(
            outputOption,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
//                    val intent = Intent()
//                    intent.putExtra(EXTRA_CAMERAX_IMAGE, outputOption.savedUri.toString())
//                    setResult(CAMERAX_RESULT, intent)
//                    finish()
                    val message = "Photo capture succes: ${outputFileResults.savedUri}"
                    Toast.makeText(this@AddImageActivty, message, Toast.LENGTH_SHORT).show()
                }

                override fun onError(exc: ImageCaptureException) {
                    Toast.makeText(
                        this@AddImageActivty,
                        exc.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e(TAG, "onError: ${exc.message}")
                }

            }
        )

    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}