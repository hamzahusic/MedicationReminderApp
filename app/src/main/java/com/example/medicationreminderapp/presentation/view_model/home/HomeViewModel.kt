package com.example.medicationreminderapp.presentation.view_model.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicationreminderapp.data.repository.dosage_history.DosageHistoryRepository
import com.example.medicationreminderapp.data.repository.medication.MedicationRepository
import com.example.medicationreminderapp.data.repository.user.SessionRepository
import com.example.medicationreminderapp.data.service.FirebaseAuthService
import com.example.medicationreminderapp.data.service.FirestoreService
import com.example.medicationreminderapp.presentation.ui.screens.home.util.Medication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val medicationRepository: MedicationRepository,
    private val dosageHistoryRepository: DosageHistoryRepository,
    private val sessionRepository: SessionRepository,
    private val firebaseAuthService: FirebaseAuthService,
    private val firestoreService: FirestoreService
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Init)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<HomeNavigationEvent>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    private val _cloudSyncCount = MutableStateFlow(0)
    val cloudSyncCount: StateFlow<Int> = _cloudSyncCount.asStateFlow()

    init {
        loadMedications()
        observeFirestoreMedications()
    }

    private fun loadMedications() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                val userId = sessionRepository.observeLoggedInUserId().first()
                if (userId == null) {
                    _uiState.value = HomeUiState.Error("Session expired. Please log in again.")
                    return@launch
                }
                val startOfDay = todayStartOfDay()
                val endOfDay = startOfDay + 24 * 60 * 60 * 1000L - 1
                combine(
                    medicationRepository.observeMedicationsByUser(userId),
                    dosageHistoryRepository.observeTakenScheduleIdsForDay(startOfDay, endOfDay)
                ) { medications, takenIds ->
                    val takenSet = takenIds.toSet()
                    medications.map { it.copy(isTaken = it.scheduleId in takenSet) }
                }.collect { medications ->
                    val taken = medications.count { it.isTaken }
                    val missed = medications.count { !it.isTaken }
                    val progress = if (medications.isEmpty()) 0f
                                   else taken.toFloat() / medications.size
                    _uiState.value = HomeUiState.Success(
                        progress = progress,
                        taken = taken,
                        missed = missed,
                        medications = medications
                    )
                }
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(
                    e.message ?: "Failed to load medications."
                )
            }
        }
    }

    fun onTakeMedication(medication: Medication) {
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

    private fun todayStartOfDay(): Long {
        return Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }

    private fun observeFirestoreMedications() {
        val uid = firebaseAuthService.currentUser?.uid ?: return
        viewModelScope.launch {
            try {
                firestoreService.observeMedications(uid).collect { list ->
                    _cloudSyncCount.value = list.size
                }
            } catch (_: Exception) { }
        }
    }

    fun logout() {
        viewModelScope.launch {
            sessionRepository.clearSession()
            firebaseAuthService.logout()
            _navigationEvent.send(HomeNavigationEvent.NavigateToLogin)
        }
    }

    fun resetUiState() {
        loadMedications()
    }
}
