package com.example.simpleshoppinglist.model

import java.io.Serializable

data class SingleLista(
    val naziv: String?,
    var datum: String = ""
) : Serializable {
    var listOfShoppingItems = mutableListOf<ShoppingItem>()
}
