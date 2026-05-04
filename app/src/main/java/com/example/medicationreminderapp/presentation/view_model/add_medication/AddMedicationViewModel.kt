package com.example.medicationreminderapp.presentation.view_model.add_medication

import androidx.lifecycle.ViewModel
import com.example.medicationreminderapp.presentation.ui.screens.add_medication.util.isAddMedicationFormValid
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class AddMedicationViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<AddMedicationUiState>(AddMedicationUiState.Init)
    val uiState: StateFlow<AddMedicationUiState> = _uiState.asStateFlow()

    init {
        _uiState.value = AddMedicationUiState.Success(
            name = "",
            dosage = "",
            selectedHour = 8,
            selectedMinute = 0,
            isValid = false
        )
    }

    fun onNameChange(name: String) {
        val current = currentSuccess() ?: return
        _uiState.value = current.copy(
            name = name,
            isValid = isAddMedicationFormValid(name, current.dosage)
        )
    }

    fun onDosageChange(dosage: String) {
        val current = currentSuccess() ?: return
        _uiState.value = current.copy(
            dosage = dosage,
            isValid = isAddMedicationFormValid(current.name, dosage)
        )
    }

    fun onHourChange(hour: Int) {
        currentSuccess()?.let { _uiState.value = it.copy(selectedHour = hour) }
    }

    fun onMinuteChange(minute: Int) {
        currentSuccess()?.let { _uiState.value = it.copy(selectedMinute = minute) }
    }

    private fun currentSuccess() = _uiState.value as? AddMedicationUiState.Success
}
