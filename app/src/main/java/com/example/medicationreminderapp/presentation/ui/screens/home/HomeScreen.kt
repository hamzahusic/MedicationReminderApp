package com.example.medicationreminderapp.presentation.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.medicationreminderapp.presentation.navigation.Screen
import com.example.medicationreminderapp.presentation.theme.MedicationReminderAppTheme
import com.example.medicationreminderapp.presentation.ui.screens.home.component.AdherenceStat
import com.example.medicationreminderapp.presentation.ui.screens.home.component.Greeting
import com.example.medicationreminderapp.presentation.ui.screens.home.component.Stats
import com.example.medicationreminderapp.presentation.ui.screens.home.component.UpcomingMedication
import com.example.medicationreminderapp.presentation.ui.components.AppBottomBar
import com.example.medicationreminderapp.presentation.ui.screens.error.ErrorScreen
import com.example.medicationreminderapp.presentation.ui.screens.home.util.Medication
import com.example.medicationreminderapp.presentation.ui.screens.loading.LoadingScreen
import com.example.medicationreminderapp.presentation.view_model.home.HomeUiState
import com.example.medicationreminderapp.presentation.view_model.home.HomeViewModel


@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToScreen: (route:String) -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        is HomeUiState.Loading -> {
            LoadingScreen()
        }

        is HomeUiState.Error -> {
            ErrorScreen(
                message = state.message,
                onRetryClick = { viewModel.resetUiState() }
            )
        }

        is HomeUiState.Success -> {
            HomeScreenContent(
                onNavigateToScreen,
                progress = state.progress,
                medications = state.medications
            )
        }
        else -> {
            //no-op
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(
    onNavigateToScreen: (route:String) -> Unit,
    progress: Float,
    medications: List<Medication>
){
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Home", fontWeight = FontWeight.ExtraBold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateToScreen(Screen.AddMedication.route)},
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
        bottomBar = {
            AppBottomBar(
                currentRoute = Screen.Home.route,
                onNavigate = { route -> onNavigateToScreen(route)}
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
            Stats()
            AdherenceStat(progress)
            UpcomingMedication(
                onNavigateToMedicationDetailsScreen = onNavigateToScreen,
                medications = medications
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MedicationReminderAppTheme {
        HomeScreen(
            viewModel = hiltViewModel(),
            {}
        )
    }
}
