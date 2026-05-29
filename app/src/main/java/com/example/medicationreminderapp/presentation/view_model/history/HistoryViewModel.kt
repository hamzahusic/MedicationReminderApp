package com.example.medicationreminderapp.presentation.view_model.history

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicationreminderapp.data.repository.dosage_history.DosageHistoryRepository
import com.example.medicationreminderapp.data.repository.medication.MedicationRepository
import com.example.medicationreminderapp.data.repository.user.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Calendar
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val medicationRepository: MedicationRepository,
    private val dosageHistoryRepository: DosageHistoryRepository,
    private val sessionRepository: SessionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HistoryUiState>(HistoryUiState.Init)
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    private val _selectedDate = MutableStateFlow(LocalDate.now())

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
                combine(
                    medicationRepository.observeMedicationsByUser(userId),
                    _selectedDate.flatMapLatest { date ->
                        val (start, end) = dateRange(date)
                        dosageHistoryRepository.observeTakenScheduleIdsForDay(start, end)
                    }
                ) { medications, takenIds ->
                    val takenSet = takenIds.toSet()
                    medications.map { it.copy(isTaken = it.scheduleId in takenSet) }
                }.collect { medications ->
                    val taken = medications.count { it.isTaken }
                    val missed = medications.count { !it.isTaken }
                    _uiState.value = HistoryUiState.Success(
                        progress = if (medications.isEmpty()) 0f else taken.toFloat() / medications.size,
                        taken = taken,
                        missed = missed,
                        today = today,
                        selectedDate = _selectedDate.value,
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
        _selectedDate.value = date
    }

    fun onClearDate() {
        _selectedDate.value = LocalDate.now()
    }

    fun resetUiState() {
        loadHistory()
    }

    private fun dateRange(date: LocalDate): Pair<Long, Long> {
        val cal = Calendar.getInstance().apply {
            set(date.year, date.monthValue - 1, date.dayOfMonth, 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val start = cal.timeInMillis
        val end = start + 24 * 60 * 60 * 1000L - 1
        return Pair(start, end)
    }
}
