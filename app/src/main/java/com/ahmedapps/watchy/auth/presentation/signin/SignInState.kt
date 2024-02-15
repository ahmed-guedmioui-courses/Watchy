package com.ahmedapps.watchy.auth.presentation.signin

data class SignInState(
    val isLoading: Boolean = false,

    val signInEmail: String = "",
    val signInPassword: String = ""
)