package com.example.calorease.presentation.bottom_menu

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.example.calorease.R
import com.example.calorease.data.response.PredictionResponse
import com.example.calorease.data.response.UploadResponse
import com.example.calorease.data.retrofit.ApiConfig
import com.example.calorease.databinding.FragmentCameraBinding
import com.example.calorease.utils.SessionManager
import com.example.calorease.utils.getImageUri
import com.example.calorease.utils.reduceFileImage
import com.example.calorease.utils.uriToFile
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CameraFragment : Fragment() {

    private lateinit var binding: FragmentCameraBinding
    private var currentImageUri: Uri? = null
    private lateinit var sessionManager: SessionManager
    private var imagePath: String? = null

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(context, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        context?.let {
            ContextCompat.checkSelfPermission(
                it,
                REQUIRED_PERMISSION
            )
        } == PackageManager.PERMISSION_GRANTED

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = context?.let { SessionManager(it) }!!

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.cameraButton.setOnClickListener { startCamera() }
        binding.uploadButton.setOnClickListener {
            uploadImage()
        }
        binding.predictButton.setOnClickListener{ predictImage() }

    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun startCamera() {
        currentImageUri = context?.let { getImageUri(it) }
        launcherIntentCamera.launch(currentImageUri)
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun uploadImage() {
        currentImageUri?.let { uri ->
            val imageFile = context?.let { uriToFile(uri, it).reduceFileImage() }
            Log.d("Image File", "showImage: ${imageFile?.path}")

            showLoading(true)

            val requestImageFile = imageFile?.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "image",
                imageFile?.name,
                requestImageFile!!
            )

            lifecycleScope.launch {
                try {
                    val apiService = ApiConfig.getApiService()
                    val successResponse = apiService.uploadImage(multipartBody)
                    showToast(successResponse.status.message)
                    sessionManager.savePredictionImage(successResponse.data.imagePath)
                    showToast(successResponse.data.imagePath)
                    showLoading(false)
                }
                catch (e:Exception) {
//                    val errorBody = e.message.toString()
//                    val errorResponse = Gson().fromJson(errorBody, UploadResponse::class.java)
//                    showToast(errorResponse.status.message)
//                    showLoading(false)
                }
            }

        } ?: showToast(getString(R.string.empty_image_warning))
//        val intent = Intent(context, MainActivity::class.java)
//        startActivity(intent)
    }

    private fun predictImage(){
        val client = ApiConfig.getApiService().predictionImage( "gs://imagestoragedatabase/20231217_2328242189283371457962105.jpg")
//        showToast(sessionManager.fetchPredictionImage().toString())
        client.enqueue(object : Callback<PredictionResponse> {
            override fun onResponse(
                call: Call<PredictionResponse>,
                response: Response<PredictionResponse>
            ) {
                showToast(response.body()?.status?.message.toString())
            }

            override fun onFailure(call: Call<PredictionResponse>, t: Throwable) {
                showToast(t.message.toString())
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}