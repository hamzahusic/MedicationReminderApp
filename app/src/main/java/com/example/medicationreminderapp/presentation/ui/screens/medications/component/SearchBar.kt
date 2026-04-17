package com.example.medicationreminderapp.presentation.ui.screens.medications.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(
    inputText : String,
    setInputText: (input : String) -> Unit
){
    Row (
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = inputText,
            onValueChange = { setInputText(it)},
            placeholder = {
                Text(
                    text = "Type medication name...",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            modifier = Modifier.weight(1f),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSend = {
                    if (inputText.isNotBlank()) {
                        setInputText("")
                    }
                }
            )
        )

        if(inputText.isNotBlank()){

            IconButton(
                onClick = {
                    setInputText("")
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Clear input",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

        }

    }

}