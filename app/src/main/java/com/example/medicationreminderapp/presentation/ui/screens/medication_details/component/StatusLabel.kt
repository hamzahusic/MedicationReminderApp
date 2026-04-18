package com.example.medicationreminderapp.presentation.ui.screens.medication_details.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicationreminderapp.presentation.theme.GreenContainer
import com.example.medicationreminderapp.presentation.theme.GreenTaken
import com.example.medicationreminderapp.presentation.theme.RedDark
import com.example.medicationreminderapp.presentation.theme.RedMissed
import com.example.medicationreminderapp.presentation.util.formatTime

@Composable
fun StausLabel(
    isTaken: Boolean,
    takenAtHour: Int,
    takenAtMinute : Int
){
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if(isTaken) GreenContainer else RedDark.copy(alpha = 0.3f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                if (isTaken) Icons.Default.CheckCircle else Icons.Default.Clear,
                contentDescription = if (isTaken) "Taken" else "Not taken",
                tint = if (isTaken) GreenTaken else RedMissed,
                modifier = Modifier.size(28.dp)
            )
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = if (isTaken) "Taken at ${formatTime(takenAtHour, takenAtMinute)}" else "Not taken",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isTaken) GreenTaken else RedMissed
                )
                Text(
                    text = if (isTaken) "Keep it up!" else "Don't forget to take the medication",
                    fontSize = 13.sp,
                    color = if (isTaken) GreenTaken.copy(alpha = 0.7f) else RedMissed.copy(alpha = 0.7f)
                )
            }
        }
    }
}