package com.example.simpleshoppinglist

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.Adapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleshoppinglist.adapter.ItemAdapter
import com.example.simpleshoppinglist.data.Datasource
import com.example.simpleshoppinglist.model.SingleLista
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable
import java.lang.reflect.Type

var datasource: Datasource = Datasource()
var dohvatiSveListe = datasource.sveListe.listaSingleLista
var prazniObjektSingleListe = SingleLista("")
val activityIntentItem: Int = 2

class MainActivity : AppCompatActivity() {
    var itemAdapter = ItemAdapter(this, dohvatiSveListe, prazniObjektSingleListe)

    companion object {
        var sveListe = dohvatiSveListe
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadData()


        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = ItemAdapter(this, dohvatiSveListe, prazniObjektSingleListe)
        itemAdapter = recyclerView.adapter as ItemAdapter
        val btnNovaLista: Button = findViewById(R.id.btnNovaLista)

        btnNovaLista.setOnClickListener {
            val intent = Intent(this, DodajListuActivity::class.java)
            this.startActivityForResult(intent, activityIntentItem)
        }
    }

    fun saveData(lista: MutableList<SingleLista>) {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Popis Lista", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        val gson = Gson()
        val json: String = gson.toJson(lista)
        editor.putString("Spremi_Listu", json)
        editor.apply()
    }

    fun loadData() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Popis Lista", MODE_PRIVATE)
        val gson = Gson()
        //sharedPreferences.edit().clear().apply() FOR LIST DELETION
        if (sharedPreferences.getString("Spremi_Listu", null) != null) {
            val json: String = sharedPreferences.getString("Spremi_Listu", null)!!
            val turnsType = object : TypeToken<MutableList<SingleLista>>() {}.type
            dohvatiSveListe = gson.fromJson(json, turnsType)
            sveListe = dohvatiSveListe
        } else {
            datasource.sveListe.listaSingleLista.clear()
        }
    }

    fun deleteItem(position: Int) {
        dohvatiSveListe.removeAt(position)
        itemAdapter.notifyItemRemoved(position)
        itemAdapter.notifyItemRangeChanged(position, dohvatiSveListe.size)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == activityIntentItem) {
            if (resultCode == RESULT_OK) {
                val dohvatiObjekt = data?.getSerializableExtra("Single_Lista") as SingleLista
                dohvatiSveListe.add(dohvatiObjekt)
                saveData(dohvatiSveListe)
            }
        }

    }
}