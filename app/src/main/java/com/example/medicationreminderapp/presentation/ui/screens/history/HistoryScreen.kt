package com.example.medicationreminderapp.presentation.ui.screens.history

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.medicationreminderapp.presentation.theme.MedicationReminderAppTheme
import com.example.medicationreminderapp.presentation.ui.components.EmptyListLabel
import com.example.medicationreminderapp.presentation.ui.screens.error.ErrorScreen
import com.example.medicationreminderapp.presentation.ui.screens.history.component.AdherenceOverviewCard
import com.example.medicationreminderapp.presentation.ui.screens.history.component.HistoryCard
import com.example.medicationreminderapp.presentation.ui.screens.history.component.WeekCalendar
import com.example.medicationreminderapp.presentation.ui.screens.home.util.Medication
import com.example.medicationreminderapp.presentation.ui.screens.loading.LoadingScreen
import com.example.medicationreminderapp.presentation.view_model.history.HistoryUiState
import com.example.medicationreminderapp.presentation.view_model.history.HistoryViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToAddMedication: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        is HistoryUiState.Loading -> {
            LoadingScreen()
        }

        is HistoryUiState.Error -> {
            ErrorScreen(
                message = state.message,
                onRetryClick = { viewModel.resetUiState() }
            )
        }

        is HistoryUiState.Success -> {
            HistoryScreenContent(
                onNavigateBack = onNavigateBack,
                onNavigateToAddMedication = onNavigateToAddMedication,
                progress = state.progress,
                selectedDate = state.selectedDate,
                today = state.today,
                medications = state.medications,
                onDateSelected = viewModel::onDateSelected,
                onClearDate = viewModel::onClearDate
            )
        }

        else -> {
            //no-op
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HistoryScreenContent(
    onNavigateBack: () -> Unit,
    onNavigateToAddMedication: () -> Unit,
    progress: Float,
    selectedDate: LocalDate,
    today: LocalDate,
    medications: List<Medication>,
    onDateSelected: (LocalDate) -> Unit,
    onClearDate: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "History",
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(
                            Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateToAddMedication() },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            AdherenceOverviewCard(progress)

            Text(
                text = "CALENDAR",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.5.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            WeekCalendar(
                selectedDate = selectedDate,
                onDateSelected = { onDateSelected(it) },
                onClear = { onClearDate() }
            )

            Text(
                text = if (selectedDate == today) "TODAY" else "$selectedDate",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.5.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                if (medications.isEmpty()) {
                    EmptyListLabel(content = "No medication on this day")
                }

                medications.forEach { medication ->
                    HistoryCard(medication)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun HistoryScreenPreview() {
    MedicationReminderAppTheme {
        HistoryScreen(
            viewModel = hiltViewModel(),
            onNavigateBack = {},
            onNavigateToAddMedication = {}
        )
    }
}
