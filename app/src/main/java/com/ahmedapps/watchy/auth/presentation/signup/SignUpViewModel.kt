package com.ahmedapps.watchy.auth.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmedapps.watchy.auth.domain.repository.AuthRepository
import com.ahmedapps.watchy.auth.domain.usecase.FormValidatorUseCase
import com.ahmedapps.watchy.auth.util.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val formValidatorUseCase: FormValidatorUseCase
) : ViewModel() {

    private var _signUpState = MutableStateFlow(SignUpState())
    val signInState = _signUpState.asStateFlow()

    private val _authResultChannel = Channel<AuthResult<Unit>>()
    val authResultChannel = _authResultChannel.receiveAsFlow()

    private val _invalidCredentialsToastChannel = Channel<Boolean>()
    val invalidCredentialsToastChannel = _invalidCredentialsToastChannel.receiveAsFlow()

    fun onEvent(event: SignUpUiEvent) {
        when (event) {

            is SignUpUiEvent.SignUpNameChanged -> {
                _signUpState.update {
                    it.copy(signUpName = event.value)
                }
            }

            is SignUpUiEvent.SignUpEmailChanged -> {
                _signUpState.update {
                    it.copy(signUpEmail = event.value)
                }
            }

            is SignUpUiEvent.SignUpPasswordChanged -> {
                _signUpState.update {
                    it.copy(signUpPassword = event.value)
                }
            }

            is SignUpUiEvent.SignUp -> {
                val isValidName = formValidatorUseCase.validName(
                    name = signInState.value.signUpName
                )
                val isValidEmail = formValidatorUseCase.validEmail(
                    email = signInState.value.signUpEmail
                )
                val isValidPassword = formValidatorUseCase.validPassword(
                    password = signInState.value.signUpPassword
                )

                if (isValidName && isValidEmail && isValidPassword) {
                    signUp()
                } else {
                    viewModelScope.launch {
                        _invalidCredentialsToastChannel.send(true)
                    }
                }
            }

        }
    }

    private fun signUp() {
        viewModelScope.launch {
            _signUpState.update { it.copy(isLoading = true) }
            val result = authRepository.singUp(
                name = signInState.value.signUpName,
                email = signInState.value.signUpEmail,
                password = signInState.value.signUpPassword
            )
            _authResultChannel.send(result)
            _signUpState.update { it.copy(isLoading = false) }
        }
    }

    private fun authenticate() {
        viewModelScope.launch {
            _signUpState.update { it.copy(isLoading = true) }
            val result = authRepository.authenticate()
            _authResultChannel.send(result)
            _signUpState.update { it.copy(isLoading = false) }
        }
    }
}


















