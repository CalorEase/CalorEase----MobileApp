package com.example.calorease.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.calorease.R
import com.example.calorease.databinding.ActivityHomepageBinding
import com.example.calorease.presentation.bottom_menu.AddImageActivty
import com.example.calorease.presentation.bottom_menu.CameraFragment
import com.example.calorease.presentation.bottom_menu.HomeFragment
import com.example.calorease.presentation.bottom_menu.ProfileFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomepageActivity : AppCompatActivity() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var binding: ActivityHomepageBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var bottomNavBar: BottomNavigationView

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        //firebase auth
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        val auth = Firebase.auth
        val user = auth.currentUser

        if (user != null) {
            val userName = user.displayName
//            binding.name.text = "Hello, " + userName
        } else {
            // not signed in
        }

        //set fragment di bottom navbar
        setFragment(HomeFragment())
        bottomNavBar = binding.bottomNav as BottomNavigationView
        bottomNavBar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> {
                    setFragment(HomeFragment())
                    true
                }
                R.id.camera -> {
                    setFragment(CameraFragment())
                    true
                }
                R.id.profile -> {
                    setFragment(ProfileFragment())
                    true
                }

                else -> {
                    setFragment(HomeFragment())
                    true
                }
            }
        }

    }

    private fun setFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_layout, fragment)
        transaction.commit()
    }


    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}