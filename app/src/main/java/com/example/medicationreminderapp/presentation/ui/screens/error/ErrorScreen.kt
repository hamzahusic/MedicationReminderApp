package com.example.medicationreminderapp.presentation.ui.screens.error

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ErrorScreen(
    message: String,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier) {
    Column(modifier = modifier){
        Text(
            text = message
        )
        Button(
            onClick = onRetryClick
        ) {}
    }
}