package com.example.simpleshoppinglist

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleshoppinglist.adapter.ShoppingItemAdapter
import com.example.simpleshoppinglist.model.ShoppingItem
import com.example.simpleshoppinglist.model.SingleLista
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

val activityIntentId: Int = 1


class SingleListActivity : AppCompatActivity() {

    //Empty init
    var singleList = SingleLista("Placeholder")
    var listSource = singleList.listOfShoppingItems
    var shoppingItemAdapter = ShoppingItemAdapter(this, singleList.listOfShoppingItems, singleList)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_list)

        val headerTextView: TextView = findViewById(R.id.textViewHeaderSingleActivity)
        val getObjectFromRecyclerOnClick =
            intent.getSerializableExtra("Extra_object") as SingleLista
        singleList = getObjectFromRecyclerOnClick
        loadData()

        listSource = singleList.listOfShoppingItems
        shoppingItemAdapter =
            ShoppingItemAdapter(this, singleList.listOfShoppingItems, getObjectFromRecyclerOnClick)
        val recyclerViewSingle = findViewById<RecyclerView>(R.id.recycler_view_single)
        val btnAddShoppingItem: Button = findViewById(R.id.btnDodajNamirnicu)
        recyclerViewSingle.adapter = shoppingItemAdapter
        headerTextView.text = getObjectFromRecyclerOnClick.title


        btnAddShoppingItem.setOnClickListener {
            val intent = Intent(this, AddShoppingItemActivity::class.java)
            intent.putExtra("SinglLista", singleList)
            this.startActivityForResult(intent, activityIntentId)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == activityIntentId) {
            if (resultCode == RESULT_OK) {
                val shoppingItemName = data?.getSerializableExtra("NazivNamirnice") as String
                val shoppingItemQuantitySerializabled =
                    data?.getSerializableExtra("KolicinaNamirnice") as String
                var shoppingItemQuantity = shoppingItemQuantitySerializabled.toIntOrNull()
                if (shoppingItemQuantity == null) {
                    shoppingItemQuantity = 1
                }


                addShoppingItem(
                    shoppingItemName,
                    shoppingItemQuantity,
                    shoppingItemAdapter,
                )

                saveData()

            }
        }

    }

    fun saveData() {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("Liste_u_objektu", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        val gson = Gson()
        val json: String = gson.toJson(singleList.listOfShoppingItems)
        editor.putString(singleList.title, json)
        editor.apply()
    }

    fun loadData() {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("Liste_u_objektu", MODE_PRIVATE)
        val gson = Gson()
        if (sharedPreferences.getString(singleList.title, null) != null) {
            val json: String = sharedPreferences.getString(singleList.title, null)!!
            val turnsType = object : TypeToken<MutableList<ShoppingItem>>() {}.type
            singleList.listOfShoppingItems = gson.fromJson(json, turnsType)
            shoppingItemAdapter.notifyDataSetChanged()
        } else {
            listSource.clear()
        }
    }

    fun addShoppingItem(
        shoppingItemName: String,
        shoppingItemQuantity: Int,
        adapter: ShoppingItemAdapter,
    ) {
        singleList.listOfShoppingItems.add(
            ShoppingItem(
                shoppingItemName,
                shoppingItemQuantity
            )
        )
        adapter.notifyDataSetChanged()
    }

}