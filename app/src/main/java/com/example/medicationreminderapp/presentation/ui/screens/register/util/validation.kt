package com.example.medicationreminderapp.presentation.ui.screens.register.util

fun isRegistrationFormValid(username: String, email: String, password: String): Boolean {
    return username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()
}