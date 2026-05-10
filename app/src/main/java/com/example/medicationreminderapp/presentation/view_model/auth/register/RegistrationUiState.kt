package com.example.medicationreminderapp.presentation.view_model.auth.register

sealed interface RegistrationUiState {
    data object Init : RegistrationUiState
    data object Loading : RegistrationUiState
    data object Success : RegistrationUiState
    data class Error(val message: String) : RegistrationUiState
}