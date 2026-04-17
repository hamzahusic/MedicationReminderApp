package com.example.medicationreminderapp.presentation.ui.screens.home.util

data class Medication(
    var name: String,
    val dosage: String,
    val takeAtHour: Int,
    val takeAtMinute: Int,
    val isTaken: Boolean = false,
    val takenAtHour: Int = 0,
    val takenAtMinute: Int = 0,
)