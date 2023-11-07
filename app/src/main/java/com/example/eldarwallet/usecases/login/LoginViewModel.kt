package com.example.eldarwallet.usecases.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LoginViewModel(): ViewModel() {
    var emailUser = mutableStateOf("")
    var passwordUser = mutableStateOf("")

}