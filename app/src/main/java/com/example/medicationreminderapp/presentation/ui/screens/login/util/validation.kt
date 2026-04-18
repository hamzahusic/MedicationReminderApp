package com.example.medicationreminderapp.presentation.ui.screens.login.util

fun isLoginFormValid(email: String, password: String): Boolean {
    return email.isNotEmpty() && password.isNotEmpty()
}