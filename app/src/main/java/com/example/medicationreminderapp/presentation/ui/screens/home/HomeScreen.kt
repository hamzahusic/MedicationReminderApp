package com.example.medicationreminderapp.presentation.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.medicationreminderapp.presentation.navigation.Screen
import com.example.medicationreminderapp.presentation.theme.MedicationReminderAppTheme
import com.example.medicationreminderapp.presentation.ui.components.AppBottomBar
import com.example.medicationreminderapp.presentation.ui.screens.error.ErrorScreen
import com.example.medicationreminderapp.presentation.ui.screens.home.component.AdherenceStat
import com.example.medicationreminderapp.presentation.ui.screens.home.component.Greeting
import com.example.medicationreminderapp.presentation.ui.screens.home.component.Stats
import com.example.medicationreminderapp.presentation.ui.screens.home.component.UpcomingMedication
import com.example.medicationreminderapp.presentation.ui.screens.home.util.Medication
import com.example.medicationreminderapp.presentation.ui.screens.loading.LoadingScreen
import com.example.medicationreminderapp.presentation.view_model.home.HomeNavigationEvent
import com.example.medicationreminderapp.presentation.view_model.home.HomeUiState
import com.example.medicationreminderapp.presentation.view_model.home.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToScreen: (route: String) -> Unit,
    onLogout: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val cloudSyncCount by viewModel.cloudSyncCount.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                HomeNavigationEvent.NavigateToLogin -> onLogout()
            }
        }
    }

    when (val state = uiState) {
        is HomeUiState.Loading -> LoadingScreen()

        is HomeUiState.Error -> ErrorScreen(
            message = state.message,
            onRetryClick = { viewModel.resetUiState() }
        )

        is HomeUiState.Success -> HomeScreenContent(
            onNavigateToScreen = onNavigateToScreen,
            onLogout = viewModel::logout,
            onTakeMedication = viewModel::onTakeMedication,
            progress = state.progress,
            taken = state.taken,
            missed = state.missed,
            medications = state.medications,
            cloudSyncCount = cloudSyncCount
        )

        else -> Unit
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(
    onNavigateToScreen: (route: String) -> Unit,
    onLogout: () -> Unit,
    onTakeMedication: (Medication) -> Unit,
    progress: Float,
    taken: Int,
    missed: Int,
    medications: List<Medication>,
    cloudSyncCount: Int
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Home", fontWeight = FontWeight.ExtraBold) },
                actions = {
                    TextButton(onClick = onLogout) {
                        Text("Logout", color = MaterialTheme.colorScheme.primary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateToScreen(Screen.AddMedication.route) },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
        bottomBar = {
            AppBottomBar(
                currentRoute = Screen.Home.route,
                onNavigate = { route -> onNavigateToScreen(route) }
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Greeting()
            Stats(taken = taken, missed = missed)
            AdherenceStat(progress)
            if (cloudSyncCount > 0) {
                Text(
                    text = "☁ $cloudSyncCount medication(s) synced to cloud",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            UpcomingMedication(
                onNavigateToMedicationDetailsScreen = onNavigateToScreen,
                onTakeMedication = onTakeMedication,
                medications = medications
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MedicationReminderAppTheme {
        HomeScreen(viewModel = hiltViewModel(), onNavigateToScreen = {})
    }
}
