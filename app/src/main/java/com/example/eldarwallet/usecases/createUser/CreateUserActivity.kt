package com.example.eldarwallet.usecases.createUser

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.eldarwallet.data.model.User
import com.example.eldarwallet.ui.theme.EldarWalletTheme
import com.example.eldarwallet.usecases.main.MainActivity
import com.example.eldarwallet.views.CreateUserScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CreateUserActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    private lateinit var viewModel: CreateUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        val database = Firebase.database
        val myRef = database.getReference("user")

        viewModel = ViewModelProvider(this).get(CreateUserViewModel::class.java)

        setContent {
            EldarWalletTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CreateUserScreen(
                        viewModel = viewModel,
                        onSignUpClick = {
                            if (viewModel.emailUser.value.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(
                                    viewModel.emailUser.value
                                ).matches()
                            ) {
                                Toast.makeText(
                                    baseContext,
                                    "Ingrese un correo electronico valido.",
                                    Toast.LENGTH_SHORT,
                                ).show()
                            } else if (viewModel.passwordUser.value != viewModel.repeatPassword.value) {
                                Toast.makeText(
                                    baseContext,
                                    "Las contraseñas no coinciden.",
                                    Toast.LENGTH_SHORT,
                                ).show()
                            } else {
                                createAuthUser(
                                    email = viewModel.emailUser.value,
                                    password = viewModel.passwordUser.value,
                                    nombre = viewModel.userName.value,
                                    apellido = viewModel.userName.value
                                )
                                saveUserInDatabase(
                                    email = viewModel.emailUser.value,
                                    nombre = viewModel.userName.value,
                                    apellido = viewModel.userName.value
                                )
                                navigateToMain()
                            }
                        })
                }
            }
        }
    }

    private fun saveUserInDatabase(email: String, nombre: String, apellido: String){
        val user = auth.currentUser
        val userId = user?.uid

        val database = Firebase.database
        val usersRef = database.getReference("users")
        val newUser = User(nombre, apellido, email, emptyList())
        if (userId != null) {
            usersRef.child(userId).setValue(newUser)
        }
    }

    private fun navigateToMain(){
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        this.startActivity(intent)
    }

    private fun createAuthUser(email: String, password: String, nombre: String, apellido: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    // Resto de tu lógica después de crear el usuario
                    Log.d(TAG, "createUserWithEmail:success")
                } else {
                    // Si falla la creación del usuario, muestra un mensaje al usuario.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

}
