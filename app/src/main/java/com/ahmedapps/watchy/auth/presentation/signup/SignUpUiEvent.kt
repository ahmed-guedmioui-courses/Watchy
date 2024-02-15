package com.ahmedapps.watchy.auth.presentation.signup

sealed class SignUpUiEvent {
    data class SignUpNameChanged(val value: String): SignUpUiEvent()
    data class SignUpEmailChanged(val value: String): SignUpUiEvent()
    data class SignUpPasswordChanged(val value: String): SignUpUiEvent()
    object SignUp: SignUpUiEvent()
}