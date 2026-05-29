package com.example.medicationreminderapp.presentation.view_model.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val medicationRepository: MedicationRepository,
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
                medicationRepository.observeMedicationsByUser(userId).collect { medications ->
                    val taken = medications.count { it.isTaken }
                    val progress = if (medications.isEmpty()) 0f
                                   else taken.toFloat() / medications.size
                    _uiState.value = HomeUiState.Success(
                        progress = progress,
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
