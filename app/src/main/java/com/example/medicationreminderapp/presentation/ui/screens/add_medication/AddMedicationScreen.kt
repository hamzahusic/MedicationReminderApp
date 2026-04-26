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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.medicationreminderapp.presentation.theme.MedicationReminderAppTheme
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicationreminderapp.presentation.ui.screens.add_medication.component.FirstDoseTimePicker
import com.example.medicationreminderapp.presentation.ui.screens.add_medication.util.isAddMedicationFormValid


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedicationScreen(
    onNavigateBack: () -> Unit
){

    var name by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var selectedHour by remember { mutableIntStateOf(8) }
    var selectedMinute by remember { mutableIntStateOf(0) }

    val isValid by remember { derivedStateOf { isAddMedicationFormValid(name, dosage) } }

    AddMedicationScreenContent(
        onNavigateBack = onNavigateBack,
        name = name,
        onNameChange = { updated -> name = updated },
        dosage = dosage,
        onDosageChange = { updated -> dosage = updated },
        selectedHour = selectedHour,
        selectedMinute = selectedMinute,
        isValid = isValid,
        onSelectedHour = { updated -> selectedHour = updated },
        onSelectedMinute = { updated -> selectedMinute = updated }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedicationScreenContent(
    onNavigateBack: () -> Unit,
    name: String,
    onNameChange: (String) -> Unit,
    dosage : String,
    onDosageChange: (String) -> Unit,
    selectedHour: Int,
    selectedMinute : Int,
    isValid : Boolean,
    onSelectedHour : (Int) -> Unit,
    onSelectedMinute : (Int) -> Unit
){
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Add Medication", fontWeight = FontWeight.ExtraBold)},
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
            modifier =
                Modifier
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
                label = { Text("Paracetamol") },
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
                label = { Text("500mg") },
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

                // Shows selected time as confirmation
                Text(
                    text = "Reminder set for $selectedHour:${selectedMinute.toString().padStart(2, '0')}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Button(
                onClick = {},
                modifier =
                    Modifier
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

@Preview
@Composable
fun AddMedicationScreenPreview(){
    MedicationReminderAppTheme {
        AddMedicationScreen(
            onNavigateBack = {}
        )
    }
}