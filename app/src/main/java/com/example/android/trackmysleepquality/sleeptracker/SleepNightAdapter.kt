package com.example.android.trackmysleepquality.sleeptracker

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.convertDurationToFormatted
import com.example.android.trackmysleepquality.convertNumericQualityToString
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.databinding.ListItemSleepNightBinding

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
    class ViewHolder private constructor(val binding:ListItemSleepNightBinding): RecyclerView.ViewHolder(binding.root){
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemSleepNightBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(binding)
            }
        }

        fun bind(item: SleepNight) {
            binding.sleep = item
            binding.executePendingBindings() // optimization to execute pending bindings?

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