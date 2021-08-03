package com.example.simpleshoppinglist.model

import java.io.Serializable

data class ShoppingItem(
    val shoppingItemTitle: String,
    var shoppingItemQuantity: Int = 1,
) : Serializable
