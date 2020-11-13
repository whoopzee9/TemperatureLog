package com.example.temperaturelog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat

class RecyclerAdapter(var values: List<Temperature>): RecyclerView.Adapter<RecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return RecyclerViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return values.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.time.text = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(values[position].time).toString()
        holder.temp.text = values[position].temp.toString()
    }
}
