package com.example.medicationreminderapp.presentation.ui.screens.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicationreminderapp.data.medications
import com.example.medicationreminderapp.presentation.ui.components.EmptyListLabel
import com.example.medicationreminderapp.presentation.ui.components.MedicationCard

@Composable
fun UpcomingMedication(
    onNavigateToMedicationDetailsScreen: (route: String) -> Unit
) {
    Column{
        val filters = listOf("All", "Morning", "Afternoon")
        var selectedFilter by remember { mutableStateOf("All") }
        val filteredMedications by remember {
            derivedStateOf {
                medications.filter {
                    when (selectedFilter) {
                        "Morning" -> it.takeAtHour < 12
                        "Afternoon" -> it.takeAtHour >= 12
                        else -> true
                    }
                }
            }
        }

        Text(
            text = "UPCOMING",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Column {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                items(filters) { filter ->
                    FilterChip(
                        selected = selectedFilter == filter,
                        onClick = { selectedFilter = filter },
                        label = { Text(filter) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedLabelColor = MaterialTheme.colorScheme.primary,
                            selectedLeadingIconColor = MaterialTheme.colorScheme.primary,
                            containerColor = MaterialTheme.colorScheme.surface,
                            labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }

            if(filteredMedications.isEmpty()){
                EmptyListLabel()
            }

            LazyColumn (
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredMedications) { medication ->
                    MedicationCard(
                        medication,
                        onNavigateToMedicationDetailsScreen
                    )
                }
            }
        }

    }
}
