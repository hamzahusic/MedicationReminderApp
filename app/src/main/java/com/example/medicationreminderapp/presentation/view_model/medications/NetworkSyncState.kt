package com.example.medicationreminderapp.presentation.view_model.medications

import com.example.medicationreminderapp.data.model.remote.MedicationDto

sealed interface NetworkSyncState {
    data object Idle : NetworkSyncState
    data object Loading : NetworkSyncState
    data class Success(val medications: List<MedicationDto>) : NetworkSyncState
    data class Error(val message: String) : NetworkSyncState
}
