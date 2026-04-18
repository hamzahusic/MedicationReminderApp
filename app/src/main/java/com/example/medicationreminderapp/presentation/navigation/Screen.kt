package com.example.medicationreminderapp.presentation.navigation

sealed class Screen(val route: String){
    data object Home : Screen("home_screen")
    data object Login : Screen("login_screen")
    data object Register : Screen("register_screen")
    data object History : Screen("history_screen")
    data object Medications : Screen("medications_screen")
    data object AddMedication : Screen("add_medication_screen")
    data object MedicationDetails : Screen("medication_details_screen")
}