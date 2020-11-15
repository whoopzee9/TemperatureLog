package com.example.temperaturelog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat

class RecyclerAdapter(private var values: ArrayList<Temperature>): RecyclerView.Adapter<RecyclerViewHolder>() {

    override fun getItemCount(): Int {
        return this.values.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return RecyclerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.setTimeText(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(values[position].time).toString())
        holder.setTempText(values[position].temp.toString())
    }

    fun setValues(value: ArrayList<Temperature>){
        this.values.clear()
        this.values.addAll(value)
    }
}
