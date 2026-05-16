package com.example.medicationreminderapp.presentation.view_model.add_medication

sealed interface AddMedicationNavigationEvent {
    data object NavigateBack : AddMedicationNavigationEvent
}
