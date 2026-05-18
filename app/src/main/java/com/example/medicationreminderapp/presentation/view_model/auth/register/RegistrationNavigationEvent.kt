package com.example.medicationreminderapp.presentation.view_model.auth.register

sealed interface RegistrationNavigationEvent {
    data object Navigate : RegistrationNavigationEvent
    data object NavigateBack : RegistrationNavigationEvent
}