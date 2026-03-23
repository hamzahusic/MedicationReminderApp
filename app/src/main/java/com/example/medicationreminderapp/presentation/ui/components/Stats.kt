package com.example.medicationreminderapp.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Stats() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        StatCard("TAKEN", 2, "doses today", 0xFF16A34A, modifier = Modifier.weight(1f))
        StatCard("MISSED", 1, "needs action", 0xFFDC2626, modifier = Modifier.weight(1f))
    }
}
