package com.example.medicationreminderapp.presentation.view_model.add_medication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicationreminderapp.data.model.remote.MedicationFirestoreDto
import com.example.medicationreminderapp.data.repository.medication.MedicationRepository
import com.example.medicationreminderapp.data.repository.user.SessionRepository
import com.example.medicationreminderapp.data.service.FirebaseAuthService
import com.example.medicationreminderapp.data.service.FirestoreService
import com.example.medicationreminderapp.presentation.ui.screens.add_medication.util.isAddMedicationFormValid
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddMedicationViewModel @Inject constructor(
    private val medicationRepository: MedicationRepository,
    private val sessionRepository: SessionRepository,
    private val firebaseAuthService: FirebaseAuthService,
    private val firestoreService: FirestoreService
) : ViewModel() {

    private val _uiState = MutableStateFlow<AddMedicationUiState>(AddMedicationUiState.Init)
    val uiState: StateFlow<AddMedicationUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<AddMedicationNavigationEvent>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

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

    fun onSaveClick() {
        val current = currentSuccess() ?: return
        viewModelScope.launch {
            _uiState.value = AddMedicationUiState.Loading
            try {
                val userId = sessionRepository.observeLoggedInUserId().first()
                if (userId == null) {
                    _uiState.value = AddMedicationUiState.Error("Session expired. Please log in again.")
                    return@launch
                }
                val data = AddMedicationData(
                    name = current.name,
                    dosage = current.dosage,
                    hour = current.selectedHour,
                    minute = current.selectedMinute,
                    userId = userId
                )
                medicationRepository.insertMedication(data)

                // Sync to Firestore if the user is signed in with Firebase
                val firebaseUid = firebaseAuthService.currentUser?.uid
                if (firebaseUid != null) {
                    try {
                        val dto = MedicationFirestoreDto(
                            id = UUID.randomUUID().toString(),
                            name = current.name,
                            dosage = current.dosage,
                            userId = userId,
                            createdAt = System.currentTimeMillis()
                        )
                        firestoreService.saveMedication(firebaseUid, dto)
                    } catch (_: Exception) { }
                }

                _navigationEvent.send(AddMedicationNavigationEvent.NavigateBack)
            } catch (e: Exception) {
                _uiState.value = AddMedicationUiState.Error(
                    e.message ?: "Failed to save medication. Please try again."
                )
            }
        }
    }

    fun resetUiState() {
        _uiState.value = AddMedicationUiState.Success(
            name = "",
            dosage = "",
            selectedHour = 8,
            selectedMinute = 0,
            isValid = false
        )
    }

    private fun currentSuccess() = _uiState.value as? AddMedicationUiState.Success
}
