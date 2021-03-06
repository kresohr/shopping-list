package com.example.simpleshoppinglist

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleshoppinglist.adapter.ItemAdapter
import com.example.simpleshoppinglist.data.Datasource
import com.example.simpleshoppinglist.model.SingleLista
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

var datasource: Datasource = Datasource()
var getEverySingleList = datasource.everyList.listOfSingleLista
var emptyInstanceOfSingleList = SingleLista("")
val activityIntentItem: Int = 2

class MainActivity : AppCompatActivity() {
    var itemAdapter = ItemAdapter(this, getEverySingleList, emptyInstanceOfSingleList)

    companion object {
        var everyList = getEverySingleList
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadData()


        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = ItemAdapter(this, getEverySingleList, emptyInstanceOfSingleList)
        itemAdapter = recyclerView.adapter as ItemAdapter
        val btnNewList: Button = findViewById(R.id.btnNovaLista)

        btnNewList.setOnClickListener {
            val intent = Intent(this, AddListActivity::class.java)
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
            getEverySingleList = gson.fromJson(json, turnsType)
            everyList = getEverySingleList
        } else {
            datasource.everyList.listOfSingleLista.clear()
        }
    }

    fun deleteItem(position: Int) {
        getEverySingleList.removeAt(position)
        itemAdapter.notifyItemRemoved(position)
        itemAdapter.notifyItemRangeChanged(position, getEverySingleList.size)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == activityIntentItem) {
            if (resultCode == RESULT_OK) {
                val catchObject = data?.getSerializableExtra("Single_Lista") as SingleLista
                getEverySingleList.add(catchObject)
                saveData(getEverySingleList)
            }
        }

    }
}