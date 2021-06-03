package com.example.android.trackmysleepquality.sleeptracker

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.TextItemViewHolder
import com.example.android.trackmysleepquality.database.SleepNight

class SleepNightAdapter:RecyclerView.Adapter<TextItemViewHolder>() {
    var dataList = listOf<SleepNight>()
    set(value){//custom setter to data variable alerts recycler view that data has chanced
        field = value
        notifyDataSetChanged()
    } // i dont think this is best practice
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.text_item_view,parent,false) as TextView
        return TextItemViewHolder(view)
    }
// lets the recylcer view know how to draw the item
    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        var item = dataList[position]
    if (item.sleepQuality <= 1) {
        holder.textView.setTextColor(Color.RED)
    } else {
        holder.textView.setTextColor(Color.BLACK)
    }
        holder.textView.text = dataList[position].sleepQuality.toString()

    }

    // lets recycler know how many items there are
    override fun getItemCount() = dataList.size

    class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        val sleepQuality: TextView = itemView.findViewById(R.id.sleep_length)
        val quality : TextView = itemView.findViewById(R.id.quality_string)
    }
}