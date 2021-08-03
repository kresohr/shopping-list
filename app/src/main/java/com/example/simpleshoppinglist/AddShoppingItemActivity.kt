package com.example.simpleshoppinglist

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class AddShoppingItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dodaj_namirnicu)
        val shoppingItemName: EditText = findViewById(R.id.nazivNamirniceEditText)
        val shoppingItemQuantity: EditText = findViewById(R.id.kolicinaNamirniceEditText)
        val btnAddShoppingItem: Button = findViewById(R.id.btnSpremiNamirnicu)


        btnAddShoppingItem.setOnClickListener {

            when {
                shoppingItemName.text.toString().length > 15 -> Toast.makeText(
                    this,
                    "Maximum 15 characters allowed!",
                    Toast.LENGTH_SHORT
                ).show()
                shoppingItemName.text.toString().isEmpty() -> Toast.makeText(
                    this,
                    "Please enter item name!",
                    Toast.LENGTH_SHORT
                ).show()
                shoppingItemQuantity.text.toString().length > 3 -> Toast.makeText(
                    this,
                    "Maximum quantity allowed is 999",
                    Toast.LENGTH_SHORT
                ).show()
                shoppingItemQuantity.text.toString() == "0" -> Toast.makeText(
                    this,
                    "Please enter quantity",
                    Toast.LENGTH_SHORT
                ).show()
                else -> {
                    val intent = Intent(this, SingleListActivity::class.java)
                    intent.putExtra("NazivNamirnice", shoppingItemName.text.toString())
                    intent.putExtra("KolicinaNamirnice", shoppingItemQuantity.text.toString())
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }

            }


        }

    }
}