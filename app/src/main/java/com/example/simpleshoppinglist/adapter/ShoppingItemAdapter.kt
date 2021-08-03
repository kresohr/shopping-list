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
        val shoppingItemTitle: TextView = view.findViewById(R.id.shopping_item_name)
        val shoppingItemQuantity: TextView = view.findViewById(R.id.shopping_item_quantity)
        val chkBoxIsDone: CheckBox = view.findViewById(R.id.chkBoxIsDone)
        val btnIncreaseQuantity: ImageView = view.findViewById(R.id.btnIncreaseQuantity)
        val btnDecreaseQuantity: ImageView = view.findViewById(R.id.btnDecreaseQuantity)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.shopping_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.shoppingItemTitle.text = item.shoppingItemTitle
        holder.shoppingItemQuantity.text = item.shoppingItemQuantity.toString()

        holder.chkBoxIsDone.setOnClickListener {
            if (holder.chkBoxIsDone.isChecked) {
                dataset.removeAt(position)
                notifyDataSetChanged()
                updateSharedPreference(singleLista)
            }
            holder.chkBoxIsDone.isChecked = false
        }
        holder.btnDecreaseQuantity.setOnClickListener {
            var quantity = (holder.shoppingItemQuantity.text as String).toInt()
            quantity--
            if (quantity <= 0) {
                dataset.removeAt(position)
                notifyDataSetChanged()
                updateSharedPreference(singleLista)
            } else {
                holder.shoppingItemQuantity.text = quantity.toString()
                item.shoppingItemQuantity = quantity
                updateSharedPreference(singleLista)
            }

        }
        holder.btnIncreaseQuantity.setOnClickListener {
            var quantity = (holder.shoppingItemQuantity.text as String).toInt()
            quantity++
            holder.shoppingItemQuantity.text = quantity.toString()
            item.shoppingItemQuantity = quantity
            updateSharedPreference(singleLista)

        }

    }

    override fun getItemCount(): Int {
        return dataset.size
    }



    fun updateSharedPreference(singleListObject: SingleLista) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(
            "Liste_u_objektu",
            AppCompatActivity.MODE_PRIVATE
        )
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        sharedPreferences.edit().remove(singleListObject.title)
        val gson = Gson()
        val json: String = gson.toJson(dataset)
        editor.putString(singleListObject.title, json)
        editor.apply()
    }

}