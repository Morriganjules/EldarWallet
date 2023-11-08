package com.example.eldarwallet.usecases.addCreditCard

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.eldarwallet.data.model.CreditCard
import com.example.eldarwallet.usecases.addCreditCard.ui.theme.EldarWalletTheme
import com.example.eldarwallet.usecases.createUser.CreateUserViewModel
import com.example.eldarwallet.utils.CryptoManager
import com.example.eldarwallet.views.CreateCardScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.Base64

class CreditCardAdd : ComponentActivity() {
    private lateinit var viewModel: CreateCreditCardViewModel
    private val auth = FirebaseAuth.getInstance()
    private val database = Firebase.database
    val cryptoManager = CryptoManager()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[CreateCreditCardViewModel::class.java]
        viewModel.fetchCardDetails()


        setContent {
            EldarWalletTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CreateCardScreen(
                        onCreateCard = {
                            if(decideCardCompany(viewModel.cardNumber.value).isNullOrEmpty()){
                                Toast.makeText(this,"Ingresa una tarjeta valida", Toast.LENGTH_SHORT)
                            } else if (viewModel.name.value != viewModel.userName.value && viewModel.userLastName.value != viewModel.lastName.value){
                                Toast.makeText(this,"El usuario no es el correcto.", Toast.LENGTH_SHORT)
                            } else {
                                val creditCard = CreditCard(
                                    cardName = decideCardCompany(viewModel.cardNumber.value),
                                    number = viewModel.cardNumber.value,
                                    securityNumber = viewModel.cardSecurityCode.value,
                                    expirationDate = viewModel.cardExpiration.value
                                )
                                writeNewCard(creditCard, viewModel.cardsData.value)
                                viewModel.name.value = ""
                                viewModel.lastName.value = ""
                                viewModel.cardExpiration.value = ""
                                viewModel.cardSecurityCode.value = ""
                                viewModel.cardNumber.value = ""
                            }


                        },
                        viewModel = viewModel
                    )
                }
            }
        }
    }

    private fun decideCardCompany(cardNumber: String): String? {
        return when (cardNumber.first().toString()) {
            "3" -> "American Express"
            "4" -> "Visa"
            "5" -> "Mastercard"
            else -> null
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun writeNewCard(creditCard: CreditCard, previousValue: String){
        val user = auth.currentUser
        val userId = user?.uid

        val database = Firebase.database
        val usersRef = database.getReference("users")
        val base64 = convertToBase64(creditCard)
        val encryptedBase64 = cryptoManager.encrypt(base64.encodeToByteArray())

        if (userId != null) {
            usersRef.child(userId).child("creditCards").setValue(encryptedBase64.toString())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun convertToBase64(creditCard: CreditCard): String {
        val creditCardString =
            "${creditCard.cardName ?: ""},${creditCard.number ?: ""},${creditCard.securityNumber ?: ""},${creditCard.expirationDate ?: ""}"
        val base64CreditCardBytes = Base64.getEncoder().encode(creditCardString.toByteArray())
        return String(base64CreditCardBytes)
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