package com.example.medicationreminderapp.presentation.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.medicationreminderapp.data.bottomNavItems
import com.example.medicationreminderapp.presentation.navigation.Screen
import com.example.medicationreminderapp.presentation.theme.MedicationReminderAppTheme

@Composable
fun AppBottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick  = { onNavigate(item.route) },
                icon     = { Icon(item.icon, contentDescription = item.label) },
                label    = { Text(item.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}


@Preview(showBackground = true, name = "BottomBar — Home selected")
@Composable
fun AppBottomBarHomePreview() {
    MedicationReminderAppTheme {
        AppBottomBar(
            currentRoute = Screen.Home.route,
            onNavigate = {}
        )
    }
}

@Preview(showBackground = true, name = "BottomBar — Medications selected")
@Composable
fun AppBottomBarProfilePreview() {
    MedicationReminderAppTheme {
        AppBottomBar(
            currentRoute = Screen.Medications.route,
            onNavigate = {}
        )
    }
}
