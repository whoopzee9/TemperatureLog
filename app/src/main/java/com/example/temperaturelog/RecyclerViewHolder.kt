package com.example.temperaturelog

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class RecyclerViewHolder(itemView: View) : ViewHolder(itemView) {
    var temp: TextView = itemView.findViewById(R.id.tv_temp)
    var time: TextView = itemView.findViewById(R.id.tv_time)

}