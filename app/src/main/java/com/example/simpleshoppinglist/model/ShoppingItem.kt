package com.example.simpleshoppinglist.model

import java.io.Serializable

data class ShoppingItem(
    val imeNamirnice: String,
    var kolicinaNamirnice: Int = 1,
) : Serializable
