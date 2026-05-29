package com.example.medicationreminderapp.presentation.view_model.medications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicationreminderapp.data.repository.medication.MedicationNetworkRepository
import com.example.medicationreminderapp.data.repository.medication.MedicationRepository
import com.example.medicationreminderapp.data.repository.user.SessionRepository
import com.example.medicationreminderapp.presentation.ui.screens.home.util.Medication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicationsViewModel @Inject constructor(
    private val medicationRepository: MedicationRepository,
    private val medicationNetworkRepository: MedicationNetworkRepository,
    private val sessionRepository: SessionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<MedicationsUiState>(MedicationsUiState.Init)
    val uiState: StateFlow<MedicationsUiState> = _uiState.asStateFlow()

    private val _networkSyncState = MutableStateFlow<NetworkSyncState>(NetworkSyncState.Idle)
    val networkSyncState: StateFlow<NetworkSyncState> = _networkSyncState.asStateFlow()

    private var allMedications: List<Medication> = emptyList()

    init {
        loadMedications()
    }

    private fun loadMedications() {
        viewModelScope.launch {
            _uiState.value = MedicationsUiState.Loading
            try {
                val userId = sessionRepository.observeLoggedInUserId().first()
                if (userId == null) {
                    _uiState.value = MedicationsUiState.Error("Session expired. Please log in again.")
                    return@launch
                }
                medicationRepository.observeMedicationsByUser(userId).collect { medications ->
                    allMedications = medications
                    val currentInput = (_uiState.value as? MedicationsUiState.Success)?.inputText ?: ""
                    _uiState.value = MedicationsUiState.Success(
                        inputText = currentInput,
                        medications = filter(currentInput)
                    )
                }
            } catch (e: Exception) {
                _uiState.value = MedicationsUiState.Error(
                    e.message ?: "Failed to load medications."
                )
            }
        }
    }

    fun syncFromNetwork() {
        viewModelScope.launch {
            _networkSyncState.value = NetworkSyncState.Loading
            try {
                val remote = medicationNetworkRepository.getRemoteMedications()
                _networkSyncState.value = NetworkSyncState.Success(remote)
            } catch (e: Exception) {
                _networkSyncState.value = NetworkSyncState.Error(
                    e.message ?: "Network sync failed."
                )
            }
        }
    }

    fun dismissNetworkSync() {
        _networkSyncState.value = NetworkSyncState.Idle
    }

    fun onInputTextChange(input: String) {
        _uiState.value = MedicationsUiState.Success(
            inputText = input,
            medications = filter(input)
        )
    }

    fun resetUiState() {
        loadMedications()
    }

    private fun filter(input: String): List<Medication> {
        return if (input.isEmpty()) allMedications
               else allMedications.filter { it.name.contains(input, ignoreCase = true) }
    }
}
