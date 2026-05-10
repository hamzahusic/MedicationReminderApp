package com.example.medicationreminderapp.presentation.view_model.auth.login

sealed interface LoginNavigationEvent {
    data class Navigate(val userId: Int) : LoginNavigationEvent
    data object NavigateBack : LoginNavigationEvent
}