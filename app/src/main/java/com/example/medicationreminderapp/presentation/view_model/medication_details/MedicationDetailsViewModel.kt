package com.example.medicationreminderapp.presentation.view_model.medication_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicationreminderapp.data.medications
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MedicationDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val id: Int = checkNotNull(savedStateHandle["id"])

    private val _uiState = MutableStateFlow<MedicationDetailsUiState>(MedicationDetailsUiState.Init)
    val uiState: StateFlow<MedicationDetailsUiState> = _uiState.asStateFlow()

    init {
        loadMedication()
    }

    private fun loadMedication() {
        viewModelScope.launch {
            _uiState.value = MedicationDetailsUiState.Loading
            delay(800)
            val medication = medications.find { it.id == id }
            _uiState.value = if (medication != null) {
                MedicationDetailsUiState.Success(medication)
            } else {
                MedicationDetailsUiState.Error("Medication not found")
            }
        }
    }

    fun resetUiState() {
        loadMedication()
    }
}
