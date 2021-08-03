package com.example.simpleshoppinglist.adapter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleshoppinglist.R
import com.example.simpleshoppinglist.SingleListActivity
import com.example.simpleshoppinglist.model.SingleLista
import com.google.gson.Gson
import java.io.Serializable

class ItemAdapter(private val context: Context, private val dataset: MutableList<SingleLista>, private val objekt: SingleLista ) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val listName: TextView = view.findViewById(R.id.item_title)
        val listDate: TextView = view.findViewById(R.id.item_date)
        val btnDeleteList: ImageView = view.findViewById(R.id.btnDeleteIcon)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }



    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.listName.text = item.title
        holder.listDate.text = item.date


        holder.listName.setOnClickListener { view ->

            val intent = Intent(context, SingleListActivity::class.java)
            intent.putExtra("Extra_object", item as Serializable)
            context.startActivity(intent)
        }
        holder.listDate.setOnClickListener { view ->
            val intent = Intent(context, SingleListActivity::class.java)
            intent.putExtra("Extra_object", item as Serializable)
            context.startActivity(intent)
        }
        holder.btnDeleteList.setOnClickListener {
            dataset.removeAt(position)
            notifyDataSetChanged()
            updateSharedPreference()
        }


    }

    fun updateSharedPreference(){
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("Popis Lista",
            AppCompatActivity.MODE_PRIVATE
        )
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        sharedPreferences.edit().clear().apply()
        val gson = Gson()
        val json: String = gson.toJson(dataset)
        editor.putString("Spremi_Listu", json)
        editor.apply()
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}
