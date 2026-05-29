package com.example.medicationreminderapp.presentation.ui.screens.medications

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.medicationreminderapp.data.model.remote.MedicationDto
import com.example.medicationreminderapp.presentation.theme.MedicationReminderAppTheme
import com.example.medicationreminderapp.presentation.ui.components.EmptyListLabel
import com.example.medicationreminderapp.presentation.ui.components.MedicationCard
import com.example.medicationreminderapp.presentation.ui.screens.error.ErrorScreen
import com.example.medicationreminderapp.presentation.ui.screens.home.util.Medication
import com.example.medicationreminderapp.presentation.ui.screens.loading.LoadingScreen
import com.example.medicationreminderapp.presentation.ui.screens.medications.component.SearchBar
import com.example.medicationreminderapp.presentation.view_model.medications.MedicationsUiState
import com.example.medicationreminderapp.presentation.view_model.medications.MedicationsViewModel
import com.example.medicationreminderapp.presentation.view_model.medications.NetworkSyncState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationsScreen(
    viewModel: MedicationsViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToAddMedication: () -> Unit,
    onNavigateToMedicationDetailsScreen: (route: String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val networkSyncState by viewModel.networkSyncState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        is MedicationsUiState.Loading -> LoadingScreen()

        is MedicationsUiState.Error -> ErrorScreen(
            message = state.message,
            onRetryClick = { viewModel.resetUiState() }
        )

        is MedicationsUiState.Success -> MedicationsScreenContent(
            onNavigateBack = onNavigateBack,
            onNavigateToAddMedication = onNavigateToAddMedication,
            onNavigateToMedicationDetailsScreen = onNavigateToMedicationDetailsScreen,
            inputText = state.inputText,
            onInputTextChange = viewModel::onInputTextChange,
            filteredMedication = state.medications,
            hasAnyMedication = state.medications.isNotEmpty() || state.inputText.isNotEmpty(),
            networkSyncState = networkSyncState,
            onSyncFromNetwork = viewModel::syncFromNetwork,
            onDismissNetworkSync = viewModel::dismissNetworkSync
        )

        else -> Unit
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationsScreenContent(
    onNavigateBack: () -> Unit,
    onNavigateToAddMedication: () -> Unit,
    onNavigateToMedicationDetailsScreen: (route: String) -> Unit,
    inputText: String,
    onInputTextChange: (String) -> Unit,
    filteredMedication: List<Medication>,
    hasAnyMedication: Boolean,
    networkSyncState: NetworkSyncState,
    onSyncFromNetwork: () -> Unit,
    onDismissNetworkSync: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Medications", fontWeight = FontWeight.ExtraBold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            item { SearchBar(inputText) { input -> onInputTextChange(input) } }

            item {
                Text(
                    text = "ACTIVE",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (filteredMedication.isEmpty() && hasAnyMedication) {
                item { EmptyListLabel(content = "No medication found") }
            } else if (!hasAnyMedication) {
                item { EmptyListLabel() }
            }

            items(items = filteredMedication) { medication ->
                MedicationCard(medication, onNavigateToMedicationDetailsScreen)
            }

            item {
                Button(
                    onClick = onNavigateToAddMedication,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Add Medication",
                        modifier = Modifier.padding(vertical = 12.dp),
                        fontSize = 17.sp
                    )
                }
            }

            // ── Network / Remote section ──────────────────────────────────────
            item {
                Text(
                    text = "REMOTE MEDICATIONS",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            when (networkSyncState) {
                is NetworkSyncState.Idle -> item {
                    OutlinedButton(
                        onClick = onSyncFromNetwork,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Fetch from Cloud API")
                    }
                }

                is NetworkSyncState.Loading -> item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(32.dp))
                    }
                }

                is NetworkSyncState.Error -> item {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = networkSyncState.message,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 14.sp
                        )
                        OutlinedButton(
                            onClick = onSyncFromNetwork,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Retry")
                        }
                    }
                }

                is NetworkSyncState.Success -> {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${networkSyncState.medications.size} medications loaded",
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            OutlinedButton(onClick = onDismissNetworkSync) {
                                Text("Clear", fontSize = 12.sp)
                            }
                        }
                    }
                    items(items = networkSyncState.medications) { dto ->
                        RemoteMedicationCard(dto)
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun RemoteMedicationCard(dto: MedicationDto) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = dto.name,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp
            )
            Text(
                text = dto.details,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2
            )
        }
    }
}

@Preview
@Composable
fun MedicationScreenPreview() {
    MedicationReminderAppTheme {
        MedicationsScreen(
            viewModel = hiltViewModel(),
            onNavigateBack = {},
            onNavigateToAddMedication = {},
            onNavigateToMedicationDetailsScreen = {}
        )
    }
}
