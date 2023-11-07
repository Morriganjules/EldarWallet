package com.example.eldarwallet.usecases.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.eldarwallet.ui.theme.EldarWalletTheme
import com.example.eldarwallet.usecases.QrActivity
import com.example.eldarwallet.views.HomeScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.userLiveData.observe(this, Observer { user ->
            setContent {
                EldarWalletTheme {

                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        HomeScreen(
                            username = user?.name,
                            onAddCard = {},
                            onReadQr = {goToQrScreen()}
                        )
                    }
                }
            }
        })
        viewModel.fetchUserDetails()
        viewModel.fetchUserDetailsByUid()
    }

    private fun goToQrScreen(){
        val intent = Intent(this, QrActivity::class.java)
        this.startActivity(intent)
    }
}
