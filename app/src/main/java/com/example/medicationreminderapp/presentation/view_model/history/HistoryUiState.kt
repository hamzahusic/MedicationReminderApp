package com.example.medicationreminderapp.presentation.view_model.history

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.medicationreminderapp.presentation.ui.screens.home.util.Medication
import java.time.LocalDate

sealed interface HistoryUiState {
    data object Init : HistoryUiState
    data object Loading : HistoryUiState
    @RequiresApi(Build.VERSION_CODES.O)
    data class Success(
        val progress: Float,
        val today: LocalDate,
        val selectedDate: LocalDate,
        val medications: List<Medication>
    ) : HistoryUiState
    data class Error(val message: String) : HistoryUiState
}
