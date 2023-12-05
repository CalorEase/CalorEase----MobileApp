package com.example.calorease.presentation.sign_in

data class SignInState(
    val isSignedIn: Boolean = false,
    val signInError: String? = null
)
