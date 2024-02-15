package com.ahmedapps.watchy.auth.presentation.signup

data class SignUpState(
    val isLoading: Boolean = false,

    val signUpName: String = "",
    val signUpEmail: String = "",
    val signUpPassword: String = ""
)