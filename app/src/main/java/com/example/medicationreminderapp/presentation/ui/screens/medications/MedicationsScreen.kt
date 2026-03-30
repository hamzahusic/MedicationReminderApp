package com.example.medicationreminderapp.presentation.ui.screens.medications

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.medicationreminderapp.presentation.theme.MedicationReminderAppTheme
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicationreminderapp.presentation.ui.screens.home.component.Medication
import com.example.medicationreminderapp.presentation.ui.components.EmptyListLabel
import com.example.medicationreminderapp.presentation.ui.screens.medications.component.MedicationCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationsScreen(){
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("My Medications", fontWeight = FontWeight.ExtraBold) },
                navigationIcon = {
                    IconButton(onClick = { /* Open drawer */ }) {
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
        Column(
            modifier =
                Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "ACTIVE",
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            val medications = listOf<Medication>(
                Medication("Paracetamol", "500mg", "14:00"),
                Medication("Ibuprofen", "450mg", "08:00"),
                Medication("Caffetin", "500mg", "12:00"),
                Medication("Buscopan", "400mg", "11:00"),
            )

            if (medications.isEmpty()){
                EmptyListLabel()
            }

            medications.forEach { medication ->
                MedicationCard(medication)
            }

            Button(
                onClick = {},
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

@Preview
@Composable
fun MedicationScreenPreview(){
    MedicationReminderAppTheme {
        MedicationsScreen()
    }
}
