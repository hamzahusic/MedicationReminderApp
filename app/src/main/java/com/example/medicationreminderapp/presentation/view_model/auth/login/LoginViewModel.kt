package com.example.medicationreminderapp.presentation.view_model.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicationreminderapp.data.repository.user.SessionRepository
import com.example.medicationreminderapp.data.repository.user.UserRepository
import com.example.medicationreminderapp.data.service.FirebaseAuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sessionRepository: SessionRepository,
    private val firebaseAuthService: FirebaseAuthService
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Loading)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<LoginNavigationEvent>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    init {
        checkExistingSession()
    }

    private fun checkExistingSession() {
        viewModelScope.launch {
            // Check DataStore session first
            val userId = sessionRepository.observeLoggedInUserId().first()
            if (userId != null) {
                _uiState.value = LoginUiState.Success(isLoggedIn = true)
                _navigationEvent.send(LoginNavigationEvent.Navigate(userId))
                return@launch
            }
            // Check Firebase persistent session
            val firebaseUser = firebaseAuthService.currentUser
            if (firebaseUser != null) {
                val localUser = userRepository.getUserByEmail(firebaseUser.email ?: "")
                if (localUser != null) {
                    sessionRepository.saveLoggedInUserId(localUser.id)
                    _uiState.value = LoginUiState.Success(isLoggedIn = true)
                    _navigationEvent.send(LoginNavigationEvent.Navigate(localUser.id))
                    return@launch
                }
            }
            _uiState.value = LoginUiState.Init
        }
    }

    fun onLoginClick(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            try {
                val user = userRepository.getUserByEmailAndPassword(email, password)
                if (user != null) {
                    sessionRepository.saveLoggedInUserId(user.id)
                    // Firebase sign-in (non-blocking – local auth is the source of truth)
                    try {
                        firebaseAuthService.login(email, password)
                    } catch (_: Exception) { }
                    _uiState.value = LoginUiState.Success(isLoggedIn = true)
                    _navigationEvent.send(LoginNavigationEvent.Navigate(user.id))
                } else {
                    _uiState.value = LoginUiState.Error("Invalid email or password.")
                }
            } catch (e: Exception) {
                _uiState.value = LoginUiState.Error(
                    e.message ?: "Login failed. Please try again."
                )
            }
        }
    }

    fun resetUiState() {
        _uiState.value = LoginUiState.Init
    }
}
