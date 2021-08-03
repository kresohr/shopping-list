package com.example.simpleshoppinglist.model

class EveryList {
    var listOfSingleLista = mutableListOf<SingleLista>()

    fun addSingleList(singleLista: SingleLista) {
        listOfSingleLista.add(singleLista)
    }
}