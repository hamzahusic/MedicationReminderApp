package com.example.medicationreminderapp.presentation.view_model.history

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicationreminderapp.data.repository.medication.MedicationRepository
import com.example.medicationreminderapp.data.repository.user.SessionRepository
import com.example.medicationreminderapp.presentation.ui.screens.home.util.Medication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val medicationRepository: MedicationRepository,
    private val sessionRepository: SessionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HistoryUiState>(HistoryUiState.Init)
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    private var allMedications: List<Medication> = emptyList()

    init {
        loadHistory()
    }

    private fun loadHistory() {
        viewModelScope.launch {
            _uiState.value = HistoryUiState.Loading
            try {
                val userId = sessionRepository.observeLoggedInUserId().first()
                if (userId == null) {
                    _uiState.value = HistoryUiState.Error("Session expired. Please log in again.")
                    return@launch
                }
                val today = LocalDate.now()
                medicationRepository.observeMedicationsByUser(userId).collect { medications ->
                    allMedications = medications
                    val currentSelected = (_uiState.value as? HistoryUiState.Success)?.selectedDate ?: today
                    _uiState.value = HistoryUiState.Success(
                        progress = computeProgress(medications),
                        today = today,
                        selectedDate = currentSelected,
                        medications = medications
                    )
                }
            } catch (e: Exception) {
                _uiState.value = HistoryUiState.Error(
                    e.message ?: "Failed to load history."
                )
            }
        }
    }

    fun onDateSelected(date: LocalDate) {
        val current = currentSuccess() ?: return
        _uiState.value = current.copy(
            selectedDate = date,
            medications = allMedications
        )
    }

    fun onClearDate() {
        val current = currentSuccess() ?: return
        _uiState.value = current.copy(
            selectedDate = current.today,
            medications = allMedications
        )
    }

    fun resetUiState() {
        loadHistory()
    }

    private fun computeProgress(medications: List<Medication>): Float {
        if (medications.isEmpty()) return 0f
        return medications.count { it.isTaken }.toFloat() / medications.size
    }

    private fun currentSuccess() = _uiState.value as? HistoryUiState.Success
}
