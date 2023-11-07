package com.example.eldarwallet.usecases.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.eldarwallet.usecases.main.MainActivity
import com.example.eldarwallet.ui.theme.EldarWalletTheme
import com.example.eldarwallet.usecases.createUser.CreateUserActivity
import com.example.eldarwallet.views.LoginScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: LoginViewModel

    val user = "usuario_prueba2@gmail.com"
    val pass = "gabi1234"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Firebase Auth
        auth = Firebase.auth
        viewModel = LoginViewModel()
        setContent {
            EldarWalletTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen(
                        viewModel = viewModel,
                        onLoginClick = {
                            if (viewModel.emailUser.value.isEmpty() || viewModel.passwordUser.value.isEmpty()) {
                                Toast.makeText(
                                    baseContext,
                                    "Authentication failed.",
                                    Toast.LENGTH_SHORT,
                                ).show()
                            } else {

                                signIn(
                                    email = viewModel.emailUser.value,
                                    password = viewModel.passwordUser.value
                                )
                            }
                        }, onRegisterClick = {
                            navigateToCreateUser()
                        })
                }
            }
        }
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    navigateToMain()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        this.startActivity(intent)
    }

    private fun navigateToCreateUser() {
        val intent = Intent(this, CreateUserActivity::class.java)
        this.startActivity(intent)
    }
}