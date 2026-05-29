package com.example.medicationreminderapp.presentation.view_model.medication_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicationreminderapp.data.repository.dosage_history.DosageHistoryRepository
import com.example.medicationreminderapp.data.repository.medication.MedicationRepository
import com.example.medicationreminderapp.data.repository.user.SessionRepository
import com.example.medicationreminderapp.data.service.FirebaseAuthService
import com.example.medicationreminderapp.data.service.FirestoreService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class MedicationDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val medicationRepository: MedicationRepository,
    private val dosageHistoryRepository: DosageHistoryRepository,
    private val sessionRepository: SessionRepository,
    private val firebaseAuthService: FirebaseAuthService,
    private val firestoreService: FirestoreService
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
                if (medication == null) {
                    _uiState.value = MedicationDetailsUiState.Error("Medication not found.")
                    return@launch
                }
                val startOfDay = todayStartOfDay()
                val endOfDay = startOfDay + 24 * 60 * 60 * 1000L - 1
                dosageHistoryRepository.observeHistoryForDay(startOfDay, endOfDay).collect { history ->
                    val entry = history.firstOrNull { it.scheduleId == medication.scheduleId && it.isTaken }
                    val takenAt = entry?.takenAt
                    val (takenHour, takenMinute) = if (takenAt != null) {
                        val cal = Calendar.getInstance().apply { timeInMillis = takenAt }
                        Pair(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE))
                    } else Pair(0, 0)
                    _uiState.value = MedicationDetailsUiState.Success(
                        medication.copy(
                            isTaken = entry != null,
                            takenAtHour = takenHour,
                            takenAtMinute = takenMinute
                        )
                    )
                }
            } catch (e: Exception) {
                _uiState.value = MedicationDetailsUiState.Error(
                    e.message ?: "Failed to load medication."
                )
            }
        }
    }

    fun onMarkAsTaken() {
        val current = _uiState.value as? MedicationDetailsUiState.Success ?: return
        val medication = current.medication
        if (medication.isTaken || medication.scheduleId == 0) return
        viewModelScope.launch {
            try {
                dosageHistoryRepository.takeMedication(medication.scheduleId, todayStartOfDay())
            } catch (_: Exception) { }
            val uid = firebaseAuthService.currentUser?.uid
            if (uid != null) {
                try {
                    firestoreService.markMedicationAsTaken(uid, medication.id.toString())
                } catch (_: Exception) { }
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
                val uid = firebaseAuthService.currentUser?.uid
                if (uid != null) {
                    try {
                        firestoreService.deleteMedication(uid, current.medication.id.toString())
                    } catch (_: Exception) { }
                }
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

    private fun todayStartOfDay(): Long {
        return Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }
}
