package com.example.medicationreminderapp.presentation.ui.screens.medications

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.medicationreminderapp.presentation.theme.MedicationReminderAppTheme
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicationreminderapp.data.medications
import com.example.medicationreminderapp.presentation.ui.components.EmptyListLabel
import com.example.medicationreminderapp.presentation.ui.components.MedicationCard
import com.example.medicationreminderapp.presentation.ui.screens.home.util.Medication
import com.example.medicationreminderapp.presentation.ui.screens.medications.component.SearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationsScreen(
    onNavigateBack: () -> Unit,
    onNavigateToAddMedication: () -> Unit,
    onNavigateToMedicationDetailsScreen: (route:String) -> Unit
){
    var inputText by remember { mutableStateOf("") }
    val filteredMedication by remember {
        derivedStateOf {
            medications.filter { it.name.lowercase().contains(inputText.lowercase()) }
        }
    }

    MedicationsScreenContent(
        onNavigateBack = onNavigateBack,
        onNavigateToAddMedication = onNavigateToAddMedication,
        onNavigateToMedicationDetailsScreen = onNavigateToMedicationDetailsScreen,
        inputText = inputText,
        onInputTextChange = { updated -> inputText = updated},
        filteredMedication = filteredMedication
    )


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationsScreenContent(
    onNavigateBack: () -> Unit,
    onNavigateToAddMedication: () -> Unit,
    onNavigateToMedicationDetailsScreen: (route:String) -> Unit,
    inputText: String,
    onInputTextChange : (String) -> Unit,
    filteredMedication: List<Medication>
){
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Medications", fontWeight = FontWeight.ExtraBold) },
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Menu")
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
            modifier =
                Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            item{
                SearchBar(inputText, { input -> onInputTextChange(input) })
            }

            item{
                Text(
                    text = "ACTIVE",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }


            if (filteredMedication.isEmpty() && medications.isNotEmpty()){
                item{
                    EmptyListLabel(
                        content = "No medication found"
                    )
                }
            } else if(medications.isEmpty()){
                item{
                    EmptyListLabel()
                }
            }

            items(
                items = filteredMedication
            ) { medication ->
                MedicationCard(
                    medication,
                    onNavigateToMedicationDetailsScreen
                )
            }

            item{
                Button(
                    onClick = { onNavigateToAddMedication() },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = null, // Content description can be null for decorative icons
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Add space between icon and text

                    Text(
                        text = "Add Medication",
                        modifier = Modifier.padding(vertical = 12.dp),
                        fontSize = 17.sp
                    )
                }
            }

        }
    }
}

@Preview
@Composable
fun MedicationScreenPreview(){
    MedicationReminderAppTheme {
        MedicationsScreen(
            onNavigateBack = {},
            onNavigateToAddMedication = {},
            onNavigateToMedicationDetailsScreen = {}
        )
    }
}
