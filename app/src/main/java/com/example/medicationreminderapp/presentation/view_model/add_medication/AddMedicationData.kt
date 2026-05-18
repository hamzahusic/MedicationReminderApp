package com.example.medicationreminderapp.presentation.view_model.add_medication

data class AddMedicationData(
    val name: String,
    val dosage: String,
    val hour: Int,
    val minute: Int,
    val userId: Int
)
