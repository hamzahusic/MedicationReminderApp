package com.example.medicationreminderapp.presentation.view_model.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicationreminderapp.data.repository.user.SessionRepository
import com.example.medicationreminderapp.data.repository.user.UserRepository
import com.example.medicationreminderapp.presentation.view_model.auth.util.RegisterUserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sessionRepository: SessionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<RegistrationUiState>(RegistrationUiState.Init)
    val uiState: StateFlow<RegistrationUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<RegistrationNavigationEvent>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    fun onRegisterClick(userData: RegisterUserData) {
        viewModelScope.launch {
            _uiState.value = RegistrationUiState.Loading
            try {
                val existingUser = userRepository.getUserByEmail(userData.email)
                if (existingUser != null) {
                    _uiState.value = RegistrationUiState.Error(
                        "An account with this email already exists."
                    )
                    return@launch
                }

                userRepository.insertUser(userData)

                val insertedUser = userRepository.getUserByEmail(userData.email)
                if (insertedUser != null) {
                    sessionRepository.saveLoggedInUserId(insertedUser.id)
                }

                _uiState.value = RegistrationUiState.Success
                _navigationEvent.send(RegistrationNavigationEvent.Navigate)
            } catch (e: Exception) {
                _uiState.value = RegistrationUiState.Error(
                    e.message ?: "Registration failed. Please try again."
                )
            }
        }
    }

    fun resetUiState() {
        _uiState.value = RegistrationUiState.Init
    }
}
