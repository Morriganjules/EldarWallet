package com.example.eldarwallet.usecases.createUser

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateUserViewModel(app: Application) : AndroidViewModel(app) {

    var emailUser = mutableStateOf("")
    var passwordUser = mutableStateOf("")
    var repeatPassword = mutableStateOf("")
    var userName = mutableStateOf("")

}