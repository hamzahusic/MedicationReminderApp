package com.example.medicationreminderapp.data
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import com.example.medicationreminderapp.presentation.navigation.Screen
import com.example.medicationreminderapp.presentation.ui.screens.home.util.BottomNavItem
import com.example.medicationreminderapp.presentation.ui.screens.home.util.Medication

val todayHistory = listOf<Medication>(
    Medication(1, "Paracetamol", "500mg", 8,2, true, 8, 0),
    Medication(2, "Ibuprofen", "400mg", 14, 2),
    Medication(3, "Ibuprofen", "400mg", 18, 0),
)

var medications = listOf<Medication>(
    Medication(1, "Paracetamol", "500mg", 14, 0, true, 14, 0),
    Medication(2, "Ibuprofen", "450mg", 8, 0),
    Medication(3, "Caffetin", "500mg", 12, 0),
    Medication(4, "Ibuprofen", "400mg", 18, 0),
)

val bottomNavItems = listOf(
    BottomNavItem(Screen.Home.route,     "Home",     Icons.Default.Home),
    BottomNavItem(Screen.Medications.route, "Medications", Icons.Default.List),
    BottomNavItem(Screen.History.route, "History", Icons.Default.DateRange)
)