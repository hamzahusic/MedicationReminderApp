package com.example.medicationreminderapp.presentation.view_model.medication_details

import com.example.medicationreminderapp.presentation.ui.screens.home.util.Medication

sealed interface MedicationDetailsUiState {
    data object Init : MedicationDetailsUiState
    data object Loading : MedicationDetailsUiState
    data class Success(val medication: Medication) : MedicationDetailsUiState
    data class Error(val message: String) : MedicationDetailsUiState
}
