package com.example.eldarwallet.usecases.addCreditCard

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eldarwallet.data.model.CreditCard
import com.example.eldarwallet.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CreateCreditCardViewModel: ViewModel() {
    var name = mutableStateOf("")
    var lastName = mutableStateOf("")
    var cardNumber = mutableStateOf("")
    var cardExpiration = mutableStateOf("")
    var cardSecurityCode = mutableStateOf("")

    private val auth = FirebaseAuth.getInstance()
    private val database = Firebase.database

    val cardsData = mutableStateOf("")
    val userName = mutableStateOf("")
    val userLastName = mutableStateOf("")

    fun fetchCardDetails() {
        val user = auth.currentUser
        if (user != null) {
            val userId = user.uid
            val usersRef = database.getReference("users").child(userId)

            usersRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val user = dataSnapshot.getValue(User::class.java)
                        if (user != null) {
                            cardsData.value = user.creditCards.toString() ?: ""
                            userName.value = user.name.toString()
                            userLastName.value = user.lastName.toString()
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle the error
                }
            })

        }
    }

}