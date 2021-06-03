package com.example.android.trackmysleepquality.sleeptracker

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.convertDurationToFormatted
import com.example.android.trackmysleepquality.convertNumericQualityToString
import com.example.android.trackmysleepquality.database.SleepNight

//Adapter doesnt need to know the view holder works - so we encapsulated it
// ADAPTER is ONLY responsible for adapting the data to the recyclerView API
class SleepNightAdapter: ListAdapter<SleepNight, SleepNightAdapter.ViewHolder>(SleepNightDiffCallback()) {
/*list adapter can be used instead of recylerview adapter to handle keeping track of list items and diff call back?
* takes in type of list int this case SleepNight list and the view holder
* ALSO HANDLES GET ITEM COUNT
* */


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = getItem(position)// method from ListAdapter interface
        holder.bind(item)
    }


    //inflates the view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    //VIEW HOLDER IS RESPONSIBLE FOR EVERYTHING RELATING TO ACTUALLY MANAGING VIEWS
    class ViewHolder private constructor(itemView:View): RecyclerView.ViewHolder(itemView){
        val sleepLength:TextView = itemView.findViewById(R.id.sleep_length)
        val qualityImage: ImageView = itemView.findViewById(R.id.quality_image)
        val quality : TextView = itemView.findViewById(R.id.quality_string)

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_sleep_night, parent, false)
                return ViewHolder(view)
            }
        }

        fun bind(item: SleepNight) {
            val resources = itemView.context.resources
            sleepLength.text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, resources)
            quality.text = convertNumericQualityToString(item.sleepQuality, resources)
            qualityImage.setImageResource(
                when (item.sleepQuality) {
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
    }

class SleepNightDiffCallback : DiffUtil.ItemCallback<SleepNight>(){
    override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem.nightId == newItem.nightId
    }

    override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem == newItem
    }

}


}