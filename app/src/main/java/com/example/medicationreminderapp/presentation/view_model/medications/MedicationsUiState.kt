package com.example.medicationreminderapp.presentation.view_model.medications

import com.example.medicationreminderapp.presentation.ui.screens.home.util.Medication

sealed interface MedicationsUiState {
    data object Init : MedicationsUiState
    data object Loading : MedicationsUiState
    data class Success(
        var inputText: String,
        val medications: List<Medication>
    ) : MedicationsUiState
    data class Error(val message: String) : MedicationsUiState
}
