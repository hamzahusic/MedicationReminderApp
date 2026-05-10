package com.example.medicationreminderapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.navigation.compose.rememberNavController
import com.example.medicationreminderapp.data.medications
import com.example.medicationreminderapp.presentation.navigation.NavGraph
import com.example.medicationreminderapp.presentation.navigation.Screen
import com.example.medicationreminderapp.presentation.theme.MedicationReminderAppTheme
import com.example.medicationreminderapp.presentation.ui.screens.add_medication.AddMedicationScreen
import com.example.medicationreminderapp.presentation.ui.screens.history.HistoryScreen
import com.example.medicationreminderapp.presentation.ui.screens.home.HomeScreen
import com.example.medicationreminderapp.presentation.ui.screens.login.LoginScreen
import com.example.medicationreminderapp.presentation.ui.screens.medication_details.MedicationDetailsScreen
import com.example.medicationreminderapp.presentation.ui.screens.medications.MedicationsScreen
import com.example.medicationreminderapp.presentation.ui.screens.register.RegisterScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MedicationReminderAppTheme {

                val navController = rememberNavController()

                NavGraph(
                    navController = navController,
                    startDestination = Screen.Login.route
                )

            }
        }
    }
}