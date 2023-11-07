package com.example.eldarwallet.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.eldarwallet.usecases.createUser.CreateUserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateUserScreen(
    onSignUpClick: () -> Unit,
    viewModel: CreateUserViewModel
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
                onDone = { /* Maneja el evento de teclado "Done" si es necesario */ }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Campo para repetir la contraseña
        TextField(
            value = viewModel.repeatPassword.value,
            onValueChange = { viewModel.repeatPassword.value = it },
            label = { Text("Repetir Contraseña") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            keyboardActions = KeyboardActions(
                onDone = { /* Maneja el evento de teclado "Done" si es necesario */ }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Campo de nombre de usuario
        TextField(
            value = viewModel.userName.value,
            onValueChange = { viewModel.userName.value = it },
            label = { Text("Nombre") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Campo de nombre de usuario
        TextField(
            value = viewModel.userName.value,
            onValueChange = { viewModel.userName.value = it },
            label = { Text("Apellido") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Botón de registro
        Button(
            onClick = {
                onSignUpClick()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(vertical = 8.dp)
        ) {
            Text("Registrarse")
        }

        // Espacio en blanco en la parte inferior
        Spacer(modifier = Modifier.weight(1f))
    }
}
