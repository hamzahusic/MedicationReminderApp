package com.example.medicationreminderapp.presentation.ui.screens.medication_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicationreminderapp.data.medications
import com.example.medicationreminderapp.presentation.theme.GreenContainer
import com.example.medicationreminderapp.presentation.theme.GreenTaken
import com.example.medicationreminderapp.presentation.theme.MedicationReminderAppTheme
import com.example.medicationreminderapp.presentation.ui.screens.home.util.Medication
import com.example.medicationreminderapp.presentation.ui.screens.medication_details.component.DetailsCard
import com.example.medicationreminderapp.presentation.ui.screens.medication_details.component.StausLabel
import com.example.medicationreminderapp.presentation.util.formatTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationDetailsScreen(medication: Medication) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Medication Details", fontWeight = FontWeight.ExtraBold) },
                navigationIcon = {
                    IconButton(onClick = { }) {
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
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxHeight()
        ) {

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

                // Hero card
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(28.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(MaterialTheme.colorScheme.background),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "💊", fontSize = 32.sp)
                        }
                        Text(
                            text = "Paracetamol",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 28.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "Active",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.background)
                                .padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                    }
                }

                // Stat cards row
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    item{
                        DetailsCard("DOSAGE", medication.dosage, Modifier.fillParentMaxWidth(0.5f))
                    }
                    item{
                        DetailsCard("DOSAGE TIME", formatTime(medication.takeAtHour, medication.takeAtMinute), Modifier.fillParentMaxWidth(0.5f))
                    }
                }

                // Today's status section
                Text(
                    text = "TODAY'S STATUS",
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 11.sp,
                    letterSpacing = 0.5.sp
                )

                StausLabel()
            }

            // Bottom action buttons
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Mark as Taken",
                        modifier = Modifier.padding(vertical = 10.dp),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedButton(
                        onClick = {},
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Edit",
                            modifier = Modifier.padding(vertical = 10.dp),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Button(
                        onClick = {},
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text(
                            text = "Delete",
                            modifier = Modifier.padding(vertical = 10.dp),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MedicationDetailsScreenPreview() {
    MedicationReminderAppTheme {
        MedicationDetailsScreen(medications[0])
    }
}
