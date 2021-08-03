package com.example.simpleshoppinglist.model

import java.io.Serializable

data class SingleLista(
    val title: String?,
    var date: String = ""
) : Serializable {
    var listOfShoppingItems = mutableListOf<ShoppingItem>()
}
