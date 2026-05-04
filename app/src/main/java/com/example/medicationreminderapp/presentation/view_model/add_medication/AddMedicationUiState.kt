package com.example.medicationreminderapp.presentation.view_model.add_medication

sealed interface AddMedicationUiState {
    data object Init : AddMedicationUiState
    data class Success(
        val name: String,
        val dosage: String,
        val selectedHour: Int,
        val selectedMinute: Int,
        val isValid: Boolean
    ) : AddMedicationUiState
    data class Error(val message: String) : AddMedicationUiState
}
