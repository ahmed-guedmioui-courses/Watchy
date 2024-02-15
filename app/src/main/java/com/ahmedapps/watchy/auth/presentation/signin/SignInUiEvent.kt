package com.ahmedapps.watchy.auth.presentation.signin

sealed class SignInUiEvent {

    data class SignInEmailChanged(val value: String): SignInUiEvent()
    data class SignInPasswordChanged(val value: String): SignInUiEvent()
    object SignIn: SignInUiEvent()
}