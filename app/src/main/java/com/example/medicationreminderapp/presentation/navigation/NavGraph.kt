package com.example.medicationreminderapp.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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
                viewModel = hiltViewModel(),
                onNavigateToScreen = { route -> navController.navigate(route) },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Screen.AddMedication.route) {
            AddMedicationScreen(
                viewModel = hiltViewModel(),
                onNavigateBack = { navController.navigateUp() }
            )
        }

        composable(route = Screen.Login.route) {
            LoginScreen(
                viewModel = hiltViewModel(),
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = { navController.navigate(Screen.Register.route) }
            )
        }

        composable(route = Screen.Register.route) {
            RegisterScreen(
                viewModel = hiltViewModel(),
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = { navController.navigate(Screen.Login.route) }
            )
        }

        composable(route = Screen.History.route) {
            HistoryScreen(
                viewModel = hiltViewModel(),
                onNavigateBack = { navController.navigateUp() },
                onNavigateToAddMedication = { navController.navigate(Screen.AddMedication.route) }
            )
        }

        composable(route = Screen.Medications.route) {
            MedicationsScreen(
                viewModel = hiltViewModel(),
                onNavigateBack = { navController.navigateUp() },
                onNavigateToAddMedication = { navController.navigate(Screen.AddMedication.route) },
                onNavigateToMedicationDetailsScreen = { route -> navController.navigate(route) }
            )
        }

        composable(
            route = Screen.MedicationDetails.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            MedicationDetailsScreen(
                viewModel = hiltViewModel(),
                onNavigateBack = { navController.navigateUp() }
            )
        }
    }
}
