package com.example.medicationreminderapp.presentation.view_model.home

sealed interface HomeNavigationEvent {
    data object NavigateToLogin : HomeNavigationEvent
}
