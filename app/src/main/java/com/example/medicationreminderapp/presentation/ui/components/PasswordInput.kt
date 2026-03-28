package com.example.medicationreminderapp.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.medicationreminderapp.R

@Composable
fun PasswordInput(
    password: String,
    passwordVisible: Boolean,
    showPassword : () -> Unit,
    changePassword : (password: String) -> Unit
){

    TextField(
        value = password,
        onValueChange = { changePassword(it) },
        placeholder = { Text("Password") },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val iconId = if (passwordVisible) R.drawable.ic_visibility_24 else R.drawable.ic_visibility_off_24
            IconButton(onClick = showPassword ) {
                Icon(painter = painterResource(iconId), contentDescription = "Toggle visibility")
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),

        )
}