package com.example.medicationreminderapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.medicationreminderapp.presentation.theme.MedicationReminderAppTheme
import com.example.medicationreminderapp.presentation.ui.screens.medication_details.MedicationDetailsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MedicationReminderAppTheme {
//              HomeScreen()
//              MedicationsScreen()
//              AddMedicationScreen()
                MedicationDetailsScreen()
            }
        }
    }
}