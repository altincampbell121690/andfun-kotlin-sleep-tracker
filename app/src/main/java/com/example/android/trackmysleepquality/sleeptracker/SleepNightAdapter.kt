package com.example.android.trackmysleepquality.sleeptracker

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.TextItemViewHolder
import com.example.android.trackmysleepquality.convertDurationToFormatted
import com.example.android.trackmysleepquality.convertNumericQualityToString
import com.example.android.trackmysleepquality.database.SleepNight

class SleepNightAdapter:RecyclerView.Adapter<SleepNightAdapter.ViewHolder>() {
    var dataList = listOf<SleepNight>()
    set(value){//custom setter to data variable alerts recycler view that data has chanced
        field = value
        notifyDataSetChanged()
    } // i dont think this is best practice
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item_sleep_night,parent,false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = dataList[position]
        val resources = holder.itemView.resources
        holder.sleepLength.text = convertDurationToFormatted(item.startTimeMilli,item.endTimeMilli, resources)
        holder.quality.text = convertNumericQualityToString(item.sleepQuality,resources)
        holder.qualityImage.setImageResource(
            when(item.sleepQuality){
                0 -> R.drawable.ic_sleep_0
                1 -> R.drawable.ic_sleep_1
                2 -> R.drawable.ic_sleep_2
                3 -> R.drawable.ic_sleep_3
                4 -> R.drawable.ic_sleep_4
                5 -> R.drawable.ic_sleep_5
                else -> R.drawable.ic_sleep_active
            }
        )
    }
    // lets recycler know how many items there are
    override fun getItemCount() = dataList.size

    class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        val sleepLength:TextView = itemView.findViewById(R.id.sleep_length)
        val qualityImage: ImageView = itemView.findViewById(R.id.quality_image)
        val quality : TextView = itemView.findViewById(R.id.quality_string)
    }



}