package com.example.medicationreminderapp.presentation.view_model.medication_details

sealed interface MedicationDetailsNavigationEvent {
    data object NavigateBack : MedicationDetailsNavigationEvent
}
