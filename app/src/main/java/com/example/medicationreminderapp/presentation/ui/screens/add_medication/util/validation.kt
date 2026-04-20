package com.example.medicationreminderapp.presentation.ui.screens.add_medication.util

fun isAddMedicationFormValid(name: String, dosage: String): Boolean {
    return name.isNotEmpty() && dosage.isNotEmpty()
}