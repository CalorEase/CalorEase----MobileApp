package com.example.calorease.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.calorease.R

class SessionManager(context: Context) {
    private var prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    //fungsi simpan gambar hasil GCS
    fun savePredictionImage(imagePath: String){
        val editor = prefs.edit()
        editor.putString(IMAGE_PATH, imagePath)
        editor.apply()
    }

    //fungsi get gambar hasil GCS
    fun fetchPredictionImage(): String? {
        return prefs.getString(IMAGE_PATH, null)
    }

    companion object {
        const val IMAGE_PATH = "image_path"
    }
}