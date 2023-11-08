package com.example.eldarwallet.usecases.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.eldarwallet.data.model.CreditCard
import com.example.eldarwallet.ui.theme.EldarWalletTheme
import com.example.eldarwallet.usecases.addCreditCard.CreditCardAdd
import com.example.eldarwallet.usecases.qrReader.QrActivity
import com.example.eldarwallet.utils.CryptoManager
import com.example.eldarwallet.views.HomeScreen
import com.google.firebase.auth.FirebaseAuth
import java.util.Base64

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: MainViewModel
    var cryptoManager = CryptoManager()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.userLiveData.observe(this, Observer { user ->
            if (!user?.creditCards.isNullOrBlank()) {
             /*   val base64Cards = user?.creditCards?.split(",")
                val decryptedCards = base64Cards?.map {
                    cryptoManager.decrypt(it.encodeToByteArray())
                }
                val cards = decryptedCards?.map {
                    decodeBase64ToCreditCard(it.toString())
                }*/
            }

            setContent {
                EldarWalletTheme {

                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        HomeScreen(
                            username = user?.name,
                            onAddCard = {
                                goToAddCreditCardScreen()
                            },
                            onReadQr = {
                                goToQrScreen()
                            }
                        )
                    }
                }
            }
        })
        viewModel.fetchUserDetails()

    }

    private fun goToQrScreen() {
        val intent = Intent(this, QrActivity::class.java)
        this.startActivity(intent)
    }

    private fun goToAddCreditCardScreen() {
        val intent = Intent(this, CreditCardAdd::class.java)
        this.startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun decodeBase64ToCreditCard(base64CreditCard: String): CreditCard {
        val creditCardString = String(Base64.getDecoder().decode(base64CreditCard))
        val parts = creditCardString.split(",")
        return CreditCard(
            cardName = parts.getOrNull(2),
            number = parts.getOrNull(3),
            securityNumber = parts.getOrNull(4),
            expirationDate = parts.getOrNull(5)
        )
    }
}
