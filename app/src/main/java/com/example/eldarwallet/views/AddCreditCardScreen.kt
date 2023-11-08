package com.example.eldarwallet.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.eldarwallet.usecases.addCreditCard.CreateCreditCardViewModel
import com.example.eldarwallet.usecases.createUser.CreateUserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCardScreen(
    onCreateCard: () -> Unit,
    viewModel: CreateCreditCardViewModel
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
            value = viewModel.name.value,
            onValueChange = { viewModel.name.value = it },
            label = { Text("Nombre") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Campo de contraseña
        TextField(
            value = viewModel.lastName.value,
            onValueChange = { viewModel.lastName.value = it },
            label = { Text("Apellido") },
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
            value = viewModel.cardNumber.value,
            onValueChange = { viewModel.cardNumber.value = it },
            label = { Text("Numero de tarjeta") },
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
            value = viewModel.cardExpiration.value,
            onValueChange = { viewModel.cardExpiration.value = it },
            label = { Text("Fecha de vencimiento") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Campo de apellido de usuario
        TextField(
            value = viewModel.cardSecurityCode.value,
            onValueChange = { viewModel.cardSecurityCode.value = it },
            label = { Text("Codigo de seguridad") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Botón de registro
        Button(
            onClick = {
                onCreateCard()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(vertical = 8.dp)
        ) {
            Text("Agregar Tarjeta")
        }

        // Espacio en blanco en la parte inferior
        Spacer(modifier = Modifier.weight(1f))
    }
}
