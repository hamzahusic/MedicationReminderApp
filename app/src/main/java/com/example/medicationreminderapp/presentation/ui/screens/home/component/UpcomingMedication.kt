package com.example.medicationreminderapp.presentation.ui.screens.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicationreminderapp.data.medications
import com.example.medicationreminderapp.presentation.ui.components.EmptyListLabel
import com.example.medicationreminderapp.presentation.ui.screens.medications.component.MedicationCard

@Composable
fun UpcomingMedication() {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "UPCOMING",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        if(medications.isEmpty()){
            EmptyListLabel()
        }

        medications.forEach { medication ->
            MedicationCard(medication)
        }
    }
}

data class Medication(var name: String, val dosage: String, val takeAt: String)
