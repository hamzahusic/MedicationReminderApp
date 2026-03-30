package com.example.medicationreminderapp.presentation.ui.screens.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.medicationreminderapp.presentation.theme.GreenTaken
import com.example.medicationreminderapp.presentation.theme.RedMissed

@Composable
fun Stats() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        StatCard("TAKEN", 2, "doses today", GreenTaken, modifier = Modifier.weight(1f))
        StatCard("MISSED", 1, "needs action", RedMissed, modifier = Modifier.weight(1f))
    }
}
