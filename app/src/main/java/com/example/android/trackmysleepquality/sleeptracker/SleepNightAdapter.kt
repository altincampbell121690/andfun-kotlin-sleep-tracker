package com.example.android.trackmysleepquality.sleeptracker

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.databinding.ListItemSleepNightBinding

//Adapter doesnt need to know the view holder works - so we encapsulated it
// ADAPTER is ONLY responsible for adapting the data to the recyclerView API
class SleepNightAdapter(val clickListener: SleepNightListener): ListAdapter<SleepNight, SleepNightAdapter.ViewHolder>(SleepNightDiffCallback()) {
/*list adapter can be used instead of recylerview adapter to handle keeping track of list items and diff call back?
* takes in type of list int this case SleepNight list and the view holder
* ALSO HANDLES GET ITEM COUNT
* */


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!,clickListener)
    }


    //inflates the view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    //VIEW HOLDER IS RESPONSIBLE FOR EVERYTHING RELATING TO ACTUALLY MANAGING VIEWS
    //constructor accepts binding as param (create from layout tag in listItems xml)
    class ViewHolder private constructor(val binding:ListItemSleepNightBinding): RecyclerView.ViewHolder(binding.root){
        companion object {
            //creates and returns the view holder
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemSleepNightBinding.inflate(layoutInflater,parent,false) // gets reference to view binding obj
                return ViewHolder(binding)
            }
        }

        fun bind(item: SleepNight, clickListener: SleepNightListener) {
            binding.sleep = item
            binding.sleepClickListener = clickListener
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
class SleepNightListener(val clickListener: (sleepId: Long)->Unit ){
    fun onClick(night:SleepNight) = clickListener(night.nightId)
}