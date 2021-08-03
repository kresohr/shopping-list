package com.example.simpleshoppinglist.data

import com.example.simpleshoppinglist.model.SingleLista
import com.example.simpleshoppinglist.model.EveryList
import java.io.Serializable

class Datasource : Serializable {
    var everyList = EveryList()

    fun addList(
        title: String?,
        date: String = ""
    ): List<SingleLista> {
        val lista = SingleLista(title, date)
        everyList.addSingleList(lista)
        return everyList.listOfSingleLista
    }
}