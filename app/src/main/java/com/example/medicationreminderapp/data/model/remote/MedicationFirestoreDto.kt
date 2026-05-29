package com.example.medicationreminderapp.data.model.remote

data class MedicationFirestoreDto(
    val id: String = "",
    val name: String = "",
    val dosage: String = "",
    val userId: Int = 0,
    val createdAt: Long = 0L
)
