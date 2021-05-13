package com.example.searchplacesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.searchplacesapp.Model.MainPojo
import com.example.searchplacesapp.Model.Prediction
import retrofit2.Callback

class RecyclerAdapter(lista:List<Prediction>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    var listaPlaces:List<Prediction> = lista;
    lateinit var onClickItem : OnClickItem

    fun addOnClickItem(onClickItem: OnClickItem){
        this.onClickItem = onClickItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textview.setText(listaPlaces.get(position).description)
        holder.itemView.setOnClickListener{onClickItem.onAssignItemClick(position)}
    }

    override fun getItemCount(): Int {
        return listaPlaces.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var textview:TextView = itemView.findViewById(R.id.txt1)
    }
}

interface OnClickItem {
    fun onAssignItemClick(position: Int)
}