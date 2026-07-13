package com.example.medicationreminderapp.presentation.ui.screens.add_medication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.medicationreminderapp.presentation.ui.screens.add_medication.component.FirstDoseTimePicker
import com.example.medicationreminderapp.presentation.ui.screens.error.ErrorScreen
import com.example.medicationreminderapp.presentation.ui.screens.loading.LoadingScreen
import com.example.medicationreminderapp.presentation.view_model.add_medication.AddMedicationNavigationEvent
import com.example.medicationreminderapp.presentation.view_model.add_medication.AddMedicationUiState
import com.example.medicationreminderapp.presentation.view_model.add_medication.AddMedicationViewModel

@Composable
fun AddMedicationScreen(
    viewModel: AddMedicationViewModel,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is AddMedicationNavigationEvent.NavigateBack -> onNavigateBack()
            }
        }
    }

    when (val state = uiState) {
        is AddMedicationUiState.Loading -> LoadingScreen()

        is AddMedicationUiState.Error -> ErrorScreen(
            message = state.message,
            onRetryClick = { viewModel.resetUiState() }
        )

        is AddMedicationUiState.Success -> AddMedicationScreenContent(
            onNavigateBack = onNavigateBack,
            name = state.name,
            onNameChange = viewModel::onNameChange,
            dosage = state.dosage,
            onDosageChange = viewModel::onDosageChange,
            selectedHour = state.selectedHour,
            selectedMinute = state.selectedMinute,
            isValid = state.isValid,
            onSelectedHour = viewModel::onHourChange,
            onSelectedMinute = viewModel::onMinuteChange,
            onSaveClick = viewModel::onSaveClick
        )

        else -> Unit
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedicationScreenContent(
    onNavigateBack: () -> Unit,
    name: String,
    onNameChange: (String) -> Unit,
    dosage: String,
    onDosageChange: (String) -> Unit,
    selectedHour: Int,
    selectedMinute: Int,
    isValid: Boolean,
    onSelectedHour: (Int) -> Unit,
    onSelectedMinute: (Int) -> Unit,
    onSaveClick: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Add Medication", fontWeight = FontWeight.ExtraBold) },
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Menu")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "MEDICATION NAME",
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onBackground
            )

            TextField(
                value = name,
                onValueChange = { onNameChange(it) },
                placeholder = { Text("Paracetamol") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
            )

            Text(
                text = "DOSAGE",
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onBackground
            )
            TextField(
                value = dosage,
                onValueChange = { onDosageChange(it) },
                placeholder = { Text("500mg") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
            )

            Text(
                text = "DOSAGE TIME",
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                FirstDoseTimePicker(
                    onTimeSelected = { hour, minute ->
                        onSelectedHour(hour)
                        onSelectedMinute(minute)
                    }
                )

                Text(
                    text = "Reminder set for $selectedHour:${selectedMinute.toString().padStart(2, '0')}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Button(
                onClick = onSaveClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                enabled = isValid,
                shape = RoundedCornerShape(12.dp),
            ) {
                Text(
                    text = "Save Medication",
                    modifier = Modifier.padding(vertical = 12.dp),
                    fontSize = 17.sp
                )
            }
        }
    }
}
