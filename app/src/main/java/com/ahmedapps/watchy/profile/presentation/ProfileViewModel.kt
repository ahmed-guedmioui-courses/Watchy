package com.ahmedapps.watchy.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmedapps.watchy.profile.domain.repository.ProfileRepository
import com.ahmedapps.watchy.auth.domain.repository.AuthRepository
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
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository,
) : ViewModel() {

    private val _profileScreenState = MutableStateFlow(ProfileScreenState())
    val profileScreenState = _profileScreenState.asStateFlow()

    private val _signOutResultChannel = Channel<AuthResult<Unit>>()
    val signOutResultChannel = _signOutResultChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            _profileScreenState.update {
                it.copy(
                    name = profileRepository.getName(),
                    email = profileRepository.getEmail()
                )
            }
        }
    }

    fun onEvent(event: ProfileUiEvent) {
        when (event) {
            ProfileUiEvent.SignOut -> {
                viewModelScope.launch {
                    val result = authRepository.singOut()
                    _signOutResultChannel.send(result)
                }
            }
        }
    }

}


















