package com.example.simpleshoppinglist.adapter

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleshoppinglist.R
import com.example.simpleshoppinglist.model.ShoppingItem
import com.example.simpleshoppinglist.model.SingleLista
import com.google.gson.Gson

class ShoppingItemAdapter(
    private val context: Context,
    private val dataset: MutableList<ShoppingItem>,
    private val singleLista: SingleLista
) : RecyclerView.Adapter<ShoppingItemAdapter.ItemViewHolder>() {


    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val nazivNamirnice: TextView = view.findViewById(R.id.naziv_namirnice)
        val kolicinaNamirnice: TextView = view.findViewById(R.id.kolicina_namirnice)
        val chkBoxIsDone: CheckBox = view.findViewById(R.id.chkBoxIsDone)
        val btnIncreaseQuantity: ImageView = view.findViewById(R.id.btnIncreaseQuantity)
        val btnDecreaseQuantity: ImageView = view.findViewById(R.id.btnDecreaseQuantity)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.namirnica_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        var chkBoxChecked = holder.chkBoxIsDone.isChecked
        holder.nazivNamirnice.text = item.imeNamirnice
        holder.kolicinaNamirnice.text = item.kolicinaNamirnice.toString()

        holder.chkBoxIsDone.setOnClickListener {
            if (holder.chkBoxIsDone.isChecked) {
                dataset.removeAt(position)
                notifyDataSetChanged()
                updateSharedPreference(singleLista)
            }
            holder.chkBoxIsDone.isChecked = false
        }
        holder.btnDecreaseQuantity.setOnClickListener {
            var kolicina = (holder.kolicinaNamirnice.text as String).toInt()
            kolicina--
            if (kolicina <= 0) {
                dataset.removeAt(position)
                notifyDataSetChanged()
                updateSharedPreference(singleLista)
            } else {
                holder.kolicinaNamirnice.text = kolicina.toString()
                item.kolicinaNamirnice = kolicina
                updateSharedPreference(singleLista)
            }

        }
        holder.btnIncreaseQuantity.setOnClickListener {
            var kolicina = (holder.kolicinaNamirnice.text as String).toInt()
            kolicina++
            holder.kolicinaNamirnice.text = kolicina.toString()
            item.kolicinaNamirnice = kolicina
            updateSharedPreference(singleLista)

        }

    }

    override fun getItemCount(): Int {
        return dataset.size
    }



    fun updateSharedPreference(nazivObjekta: SingleLista) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(
            "Liste_u_objektu",
            AppCompatActivity.MODE_PRIVATE
        )
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        sharedPreferences.edit().remove(nazivObjekta.naziv)
        val gson = Gson()
        val json: String = gson.toJson(dataset)
        editor.putString(nazivObjekta.naziv, json)
        editor.apply()
    }

}