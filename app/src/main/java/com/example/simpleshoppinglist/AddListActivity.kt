package com.example.simpleshoppinglist

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.simpleshoppinglist.model.SingleLista
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*


class AddListActivity : AppCompatActivity() {
    lateinit var listTitle: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_list)
        val date = getCurrentDateTime()
        val dateInString = date.toString("yyyy/MM/dd")
        val btnAddList: Button = findViewById(R.id.btnDodajListu)
        val frmListName: EditText = findViewById(R.id.nazivListeEditText)
        val lblDate: TextView = findViewById(R.id.datumTextField)
        lblDate.text = dateInString


        btnAddList.setOnClickListener {
            /*  */
            listTitle = frmListName.text.toString()
            when {
                checkIfListExist() -> Toast.makeText(
                    this,
                    "There is already a list with that name",
                    Toast.LENGTH_SHORT
                ).show()
                listTitle.isEmpty() -> Toast.makeText(
                    this,
                    "Please enter list name",
                    Toast.LENGTH_SHORT
                ).show()
                listTitle.length > 20 -> Toast.makeText(
                    this,
                    "Maximum 20 characters allowed!",
                    Toast.LENGTH_SHORT
                ).show()
                else -> {
                    val singleList = SingleLista(frmListName.text.toString(), lblDate.text.toString())
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("Single_Lista", singleList as Serializable)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
            }

        }

    }

    fun checkIfListExist(): Boolean {
        for (item in MainActivity.everyList) {
            if (item.title.toString() == listTitle) {
                return true
            }
        }
        return false
    }


    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }


}