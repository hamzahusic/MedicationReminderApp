package com.example.medicationreminderapp.presentation.view_model.home
import com.example.medicationreminderapp.presentation.ui.screens.home.util.Medication

sealed interface HomeUiState {
    data object Init : HomeUiState
    data object Loading : HomeUiState
    data class Success(
        val progress: Float,
        val taken: Int,
        val missed: Int,
        val medications: List<Medication>
    ) : HomeUiState
    data class Error(val message: String) : HomeUiState
}
