package com.example.medicationreminderapp.presentation.view_model.medication_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicationreminderapp.data.repository.medication.MedicationRepository
import com.example.medicationreminderapp.data.repository.user.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicationDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val medicationRepository: MedicationRepository,
    private val sessionRepository: SessionRepository
) : ViewModel() {

    private val id: Int = checkNotNull(savedStateHandle["id"])

    private val _uiState = MutableStateFlow<MedicationDetailsUiState>(MedicationDetailsUiState.Init)
    val uiState: StateFlow<MedicationDetailsUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<MedicationDetailsNavigationEvent>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    init {
        loadMedication()
    }

    private fun loadMedication() {
        viewModelScope.launch {
            _uiState.value = MedicationDetailsUiState.Loading
            try {
                val medication = medicationRepository.getMedicationById(id)
                _uiState.value = if (medication != null) {
                    MedicationDetailsUiState.Success(medication)
                } else {
                    MedicationDetailsUiState.Error("Medication not found.")
                }
            } catch (e: Exception) {
                _uiState.value = MedicationDetailsUiState.Error(
                    e.message ?: "Failed to load medication."
                )
            }
        }
    }

    fun onDeleteClick() {
        val current = _uiState.value as? MedicationDetailsUiState.Success ?: return
        viewModelScope.launch {
            _uiState.value = MedicationDetailsUiState.Loading
            try {
                val userId = sessionRepository.observeLoggedInUserId().first()
                if (userId == null) {
                    _uiState.value = MedicationDetailsUiState.Error("Session expired. Please log in again.")
                    return@launch
                }
                medicationRepository.deleteMedication(current.medication, userId)
                _navigationEvent.send(MedicationDetailsNavigationEvent.NavigateBack)
            } catch (e: Exception) {
                _uiState.value = MedicationDetailsUiState.Error(
                    e.message ?: "Failed to delete medication."
                )
            }
        }
    }

    fun resetUiState() {
        loadMedication()
    }
}
