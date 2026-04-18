package com.example.medicationreminderapp.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.medicationreminderapp.data.medications
import com.example.medicationreminderapp.presentation.ui.screens.add_medication.AddMedicationScreen
import com.example.medicationreminderapp.presentation.ui.screens.history.HistoryScreen
import com.example.medicationreminderapp.presentation.ui.screens.home.HomeScreen
import com.example.medicationreminderapp.presentation.ui.screens.login.LoginScreen
import com.example.medicationreminderapp.presentation.ui.screens.medication_details.MedicationDetailsScreen
import com.example.medicationreminderapp.presentation.ui.screens.medications.MedicationsScreen
import com.example.medicationreminderapp.presentation.ui.screens.register.RegisterScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                onNavigateToScreen = { route ->
                    navController.navigate(route)
                }
            )
        }

        composable(route = Screen.AddMedication.route) {
            AddMedicationScreen(
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }

        composable(route = Screen.Login.route) {
            LoginScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route)
                }
            )
        }

        composable(route = Screen.Register.route) {
            RegisterScreen()
        }

        composable(route = Screen.History.route) {
            HistoryScreen(
                onNavigateBack = {
                    navController.navigateUp()
                },
                onNavigateToAddMedication = {
                    navController.navigate(Screen.AddMedication.route)
                }
            )
        }

        composable(route = Screen.Medications.route) {
            MedicationsScreen(
                onNavigateBack = {
                    navController.navigateUp()
                },
                onNavigateToAddMedication = {
                    navController.navigate(Screen.AddMedication.route)
                }
            )
        }

        composable(route = Screen.MedicationDetails.route) {
            MedicationDetailsScreen(medications[0])
        }

    }
}