package com.example.simpleshoppinglist.data

import com.example.simpleshoppinglist.model.ShoppingItem
import com.example.simpleshoppinglist.model.SingleLista
import com.example.simpleshoppinglist.model.SveListe
import java.io.Serializable

class Datasource : Serializable {
    var sveListe = SveListe()

    fun dodajListu(
        naziv: String?,
        datum: String = ""
    ): List<SingleLista> {
        var lista = SingleLista(naziv, datum)
        sveListe.dodajSingleListu(lista)
        return sveListe.listaSingleLista
    }
}