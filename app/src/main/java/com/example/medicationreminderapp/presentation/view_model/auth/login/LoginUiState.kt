package com.example.medicationreminderapp.presentation.view_model.auth.login

sealed interface LoginUiState {
    data object Init : LoginUiState
    data object Loading : LoginUiState
    data class Success(val isLoggedIn: Boolean) : LoginUiState
    data class Error(val message: String) : LoginUiState
}
