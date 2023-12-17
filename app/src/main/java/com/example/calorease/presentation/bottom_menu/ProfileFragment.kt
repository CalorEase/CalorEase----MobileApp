package com.example.calorease.presentation.bottom_menu

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.calorease.R
import com.example.calorease.databinding.FragmentProfileBinding
import com.example.calorease.presentation.sign_in.SignInActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val auth = Firebase.auth
        val user = auth.currentUser
        mAuth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        if (user != null){
            val profilePhoto = user.photoUrl
            val username = user.displayName

            Glide.with(view.context)
                .load(profilePhoto).apply(RequestOptions().override(150, 150)
                    .placeholder(R.drawable.ic_launcher_background))
                .transform(CircleCrop())
                .into(binding.ivProfile)
            binding.tvProfile.text = username.toString()
            binding.btnLogout.setOnClickListener{
                logoutBackToSignIn()
            }
        }
    }

    private fun logoutBackToSignIn(){
        mAuth.signOut()

        mGoogleSignInClient.signOut().addOnCompleteListener() {
            // Optional: Update UI or show a message to the user
            val intent = Intent(context, SignInActivity::class.java)
            startActivity(intent)
        }
    }

}