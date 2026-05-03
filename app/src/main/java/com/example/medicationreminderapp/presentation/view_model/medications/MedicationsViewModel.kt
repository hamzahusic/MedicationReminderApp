package com.example.medicationreminderapp.presentation.view_model.medications

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
class MedicationsViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<MedicationsUiState>(MedicationsUiState.Init)
    val uiState: StateFlow<MedicationsUiState> = _uiState.asStateFlow()

    init {
        loadMedications()
    }

    private fun loadMedications() {
        viewModelScope.launch {
            _uiState.value = MedicationsUiState.Loading
            delay(800)
            _uiState.value = MedicationsUiState.Success(
                inputText = "",
                medications = medications
            )
        }
    }

    fun onInputTextChange(input: String){
        val filteredMedications = medications.filter {
            it.name.contains(input, ignoreCase = true)
        }

        _uiState.value = MedicationsUiState.Success(
            inputText = input,
            medications = filteredMedications
        )
    }

    fun resetUiState() {
        loadMedications()
    }
}