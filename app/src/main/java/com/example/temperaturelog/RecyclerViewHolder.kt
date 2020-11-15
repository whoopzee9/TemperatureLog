package com.example.temperaturelog

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class RecyclerViewHolder(itemView: View) : ViewHolder(itemView) {
    var temp: TextView = itemView.findViewById(R.id.tv_temp)
    var time: TextView = itemView.findViewById(R.id.tv_time)

    fun setTempText(str: String){
        temp.text = str
        if (str.toDouble() > 37.3) {
            temp.setTextColor(Color.RED)
        } else if (str.toDouble() > 36.9) {
            temp.setTextColor(Color.YELLOW)
        } else {
            temp.setTextColor(Color.GREEN)
        }
    }

    fun setTimeText(str: String){
        val tmp = str.split(' ')
        val tmp1 = tmp[3] + "     " + tmp[2] + " " + tmp[1] + " " + tmp[5]
        time.text = tmp1
    }
}