package com.example.simpleshoppinglist.model

class SveListe {
    var listaSingleLista = mutableListOf<SingleLista>()

    fun dodajSingleListu(singleLista: SingleLista) {
        listaSingleLista.add(singleLista)
    }
}