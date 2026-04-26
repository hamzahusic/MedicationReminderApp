package com.example.medicationreminderapp.presentation.ui.screens.register

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicationreminderapp.presentation.theme.MedicationReminderAppTheme
import com.example.medicationreminderapp.presentation.ui.components.PasswordInput
import com.example.medicationreminderapp.presentation.ui.screens.register.util.isRegistrationFormValid

@Composable
fun RegisterScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit
){

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val isValid by remember { derivedStateOf { isRegistrationFormValid(username, email, password) } }

    RegisterScreenContent(
        onNavigateToHome = onNavigateToHome,
        onNavigateToLogin = onNavigateToLogin,
        username = username,
        onUsernameChange = { updated -> username = updated },
        email = email,
        onEmailChange = { updated -> email = updated },
        password = password,
        onPasswordChange = { updated -> password = updated},
        passwordVisible = passwordVisible,
        setIsPasswordVisible = { passwordVisible = !passwordVisible },
        isValid = isValid
    )

}

@Composable
fun RegisterScreenContent(
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit,
    username: String,
    onUsernameChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    setIsPasswordVisible: () -> Unit,
    isValid: Boolean
){
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ){ innerpadding ->
        Column(
            modifier =
                Modifier
                    .padding(innerpadding)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 20.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(55.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(MaterialTheme.colorScheme.surface),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "💊", fontSize = 20.sp)
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy((-5).dp)
                ) {
                    Text(
                        text = "MedPulse",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 21.sp
                    )
                    Text(
                        text = "Medication Reminder",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Column() {
                Text(
                    text = "Create your account ",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 50.sp,
                    lineHeight = 45.sp
                )
                Text(
                    text = "Start tracking your medications in seconds.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Column(
                modifier = Modifier.padding(top = 30.dp),
                verticalArrangement = Arrangement.spacedBy(7.dp)
            ) {

                Text(
                    text = "USERNAME",
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                TextField(
                    value = username,
                    onValueChange = { onUsernameChange(it) },
                    placeholder = { Text("John Doe") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                )

                Text(
                    text = "EMAIL",
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                TextField(
                    value = email,
                    onValueChange = { onEmailChange(it) },
                    placeholder = { Text("john.doe@gmail.com") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                )

                Text(
                    text = "PASSWORD",
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onBackground
                )


                PasswordInput(
                    password,
                    passwordVisible,
                    { setIsPasswordVisible() },
                    { updated -> onPasswordChange(updated) }
                )


                Button(
                    onClick = { onNavigateToHome() },
                    modifier = Modifier.fillMaxWidth().padding(top = 35.dp),
                    shape = RoundedCornerShape(12.dp),
                    enabled = isValid
                ) {

                    Text(
                        text = "Create Account",
                        fontSize = 17.sp,
                        modifier = Modifier.padding(vertical = 10.dp),
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
                ) {
                    Text(
                        text = "Already have an account?",
                        fontSize = 17.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Sign in",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .clickable{ onNavigateToLogin() }
                        ,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

            }


        }
    }
}

@Preview
@Composable
fun RegisterScreenPreview(){
    MedicationReminderAppTheme() {
        RegisterScreen(
            onNavigateToHome = {},
            onNavigateToLogin = {}
        )
    }
}