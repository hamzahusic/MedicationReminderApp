package com.example.medicationreminderapp.data

import com.example.medicationreminderapp.presentation.ui.screens.home.component.Medication

val todayHistory = listOf<Medication>(
    Medication("Paracetamol", "500mg", "08:02"),
    Medication("Ibuprofen", "400mg", "14:02"),
    Medication("Ibuprofen", "400mg", "18:00"),
)

val medications = listOf<Medication>(
    Medication("Paracetamol", "500mg", "14:00"),
    Medication("Ibuprofen", "450mg", "08:00"),
    Medication("Caffetin", "500mg", "12:00"),
    Medication("Ibuprofen", "400mg", "18:00"),
)