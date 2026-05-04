package com.example.medicationreminderapp.presentation.view_model.history

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicationreminderapp.data.todayHistory
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class HistoryViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<HistoryUiState>(HistoryUiState.Init)
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    init {
        loadHistory()
    }

    private fun loadHistory() {
        viewModelScope.launch {
            _uiState.value = HistoryUiState.Loading
            delay(800)
            val today = LocalDate.now()
            _uiState.value = HistoryUiState.Success(
                progress = 0.70f,
                today = today,
                selectedDate = today,
                medications = todayHistory
            )
        }
    }

    fun onDateSelected(date: LocalDate) {
        val current = currentSuccess() ?: return
        _uiState.value = current.copy(selectedDate = date)
    }

    fun onClearDate() {
        val current = currentSuccess() ?: return
        _uiState.value = current.copy(selectedDate = current.today)
    }

    fun resetUiState() {
        loadHistory()
    }

    private fun currentSuccess() = _uiState.value as? HistoryUiState.Success
}
