package com.example.eldarwallet.data.model

data class User(
    val name:String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val creditCards: List<CreditCard>? = null
)
