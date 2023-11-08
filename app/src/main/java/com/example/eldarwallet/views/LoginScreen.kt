package com.example.eldarwallet.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eldarwallet.R
import com.example.eldarwallet.usecases.login.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    viewModel: LoginViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Espacio en blanco en la parte superior
        Spacer(modifier = Modifier.weight(1f))


        // Campo de correo electrónico
        TextField(
            value = viewModel.emailUser.value,
            onValueChange = { viewModel.emailUser.value = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Campo de contraseña
        TextField(
            value = viewModel.passwordUser.value,
            onValueChange = { viewModel.passwordUser.value = it },
            label = { Text("Contraseña") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            keyboardActions = KeyboardActions(
                onDone = { onLoginClick() }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Botón de inicio de sesión
        Button(
            onClick = { onLoginClick() },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(vertical = 8.dp)
        ) {
            Text("Iniciar sesión")
        }

        // Botón de registro
        TextButton(
            onClick = { onRegisterClick() },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Registrarse", fontSize = 16.sp)
        }

        // Espacio en blanco en la parte inferior
        Spacer(modifier = Modifier.weight(1f))
    }
}
