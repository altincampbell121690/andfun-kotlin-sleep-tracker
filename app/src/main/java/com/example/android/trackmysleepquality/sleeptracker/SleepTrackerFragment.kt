/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.trackmysleepquality.sleeptracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.database.SleepDatabase
import com.example.android.trackmysleepquality.databinding.FragmentSleepTrackerBinding

/**
 * A fragment with buttons to record start and end times for sleep, which are saved in
 * a database. Cumulative data is displayed in a simple scrollable TextView.
 * (Because we have not learned about RecyclerView yet.)
 */
class SleepTrackerFragment : Fragment() {

    /**
     * Called when the Fragment is ready to display content to the screen.
     *
     * This function uses DataBindingUtil to inflate R.layout.fragment_sleep_quality.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentSleepTrackerBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_sleep_tracker, container, false)


        /* TODO 1) we need a refence to the application context (null check of course)
        * TODO 2) then we need to getting an instance of our data source so we can get data but we
        *  -> dont need the whole table
        *TODO 3a) we need to set up our viewmodel (since its custom we need a viewmodel factory
        *****TODO 3b) create viewmodel factory
        *  TODO 4)
        * */
        //TODO 1)
        val application = requireNotNull(this.activity).application
        //TODO 2) DEFINE DATA SOURCE
        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao
        //TODO 3a)
        val viewModelFactory = SleepTrackerViewModelFactory(dataSource,application)
        //TODO 4) create new instance of viewModel using custom FACTORY
        val sleepTrackerViewModel = ViewModelProvider(this,viewModelFactory).get(SleepTrackerViewModel::class.java)
        // TODO 5) BIND
        binding.lifecycleOwner = this
        binding.sleepTrackerViewModel = sleepTrackerViewModel

        return binding.root
    }
}
